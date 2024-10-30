import java.util.Scanner;

public class T1 extends Thread {
    String name;
    final int N;
    Data dataManager;

    public T1(String name, int N, int priority) {
        this.name = name;
        this.N = N;
        setPriority(priority);
        dataManager = new Data();
    }

    @Override
    public void run() {
        System.out.printf("%s is running\n", name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.printf("%s is interrupted.\n", name);
        }

        // Оголошення матриць та векторів
        int[][] MA, ME;
        int[] A, B, C;

        // Синхронізований блок для безпечної роботи з введенням
        synchronized (System.in) {
            if (N == 3) {
                // Якщо N = 3, введення вручну
                MA = dataManager.inputMatrix(N, "MA", name);
                ME = dataManager.inputMatrix(N, "ME", name);
                A = dataManager.inputVector(N, "A", name);
                B = dataManager.inputVector(N, "B", name);
                C = dataManager.inputVector(N, "C", name);
            } else {
                // Для більшого N – вибір методу заповнення
                Scanner in = new Scanner(System.in);
                System.out.print("Choose a Filling Method:\n1 - Random Filling\n2 - Single Element Filling\nYour Choice: ");
                switch (in.nextInt()) {
                    case 1:
                        MA = dataManager.generateRandomMatrix(N);
                        ME = dataManager.generateRandomMatrix(N);
                        A = dataManager.generateRandomVector(N);
                        B = dataManager.generateRandomVector(N);
                        C = dataManager.generateRandomVector(N);
                        break;
                    case 2:
                        System.out.print("Enter value to fill elements: ");
                        int value = in.nextInt();
                        MA = dataManager.matrixFixedValue(N, value);
                        ME = dataManager.matrixFixedValue(N, value);
                        A = dataManager.vectorFixedValue(N, value);
                        B = dataManager.vectorFixedValue(N, value);
                        C = dataManager.vectorFixedValue(N, value);
                        break;
                    default:
                        System.out.println("Invalid choice. Exiting...");
                        return; // Виходимо, якщо вибір некоректний
                }
            }
        }

        // Обчислення B + C
        int[] B_plus_C = dataManager.sumVectors(B, C);

        // Обчислення MA * ME
        int[][] MA_times_ME = dataManager.multiplyMatrices(MA, ME);

        // Обчислення B * (MA * ME)
        int[] B_times_MA_ME = dataManager.multiplyMatrixByVector(MA_times_ME, B);

        // Обчислення A + B * (MA * ME)
        int[] A_plus_B_MA_ME = dataManager.sumVectors(A, B_times_MA_ME);

        // Обчислення MAX(B + C) та MIN(A + B * (MA * ME))
        int max_B_plus_C = dataManager.getMaxFromMatrix(new int[][]{B_plus_C});
        int min_A_plus_B_MA_ME = dataManager.getMinFromMatrix(new int[][]{A_plus_B_MA_ME});

        // Обчислення результату
        int result = max_B_plus_C + min_A_plus_B_MA_ME;

        // Виведення результату для малих N
        if (N < 10) {
            System.out.printf("Thread %s. Result = %d\n", name, result);
        }
        System.out.printf("%s is finished.\n", name);
    }
}
