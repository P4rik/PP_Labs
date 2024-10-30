import java.util.Scanner;

public class T3 extends Thread{
    String name;
    final int N;
    Data dataManager;

    public T3(String name, int N, int priority) {
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

        // Оголошення матриць
        int[][] MO, MP, MS;

        // Синхронізований блок для безпечної роботи з введенням
        synchronized (System.in) {
            if (N == 3) {
                // Якщо N = 3, введення вручну
                MO = dataManager.inputMatrix(N, "MO", name);
                MP = dataManager.inputMatrix(N, "MP", name);
                MS = dataManager.inputMatrix(N, "MS", name);
            } else {
                // Для більшого N – вибір методу заповнення
                Scanner in = new Scanner(System.in);
                System.out.print("Choose a Filling Method:\n1 - Random Filling\n2 - Single Element Filling\nYour Choice: ");
                switch (in.nextInt()) {
                    case 1:
                        MO = dataManager.generateRandomMatrix(N);
                        MP = dataManager.generateRandomMatrix(N);
                        MS = dataManager.generateRandomMatrix(N);
                        break;
                    case 2:
                        System.out.print("Enter value to fill elements: ");
                        int value = in.nextInt();
                        MO = dataManager.matrixFixedValue(N, value);
                        MP = dataManager.matrixFixedValue(N, value);
                        MS = dataManager.matrixFixedValue(N, value);
                        break;
                    default:
                        System.out.println("Invalid choice. Exiting...");
                        return; // Виходимо, якщо вибір некоректний
                }
            }
        }
        // Обчислення MO * MP
        int[][] MO_times_MP = dataManager.multiplyMatrices(MO, MP);

        // Обчислення MO * MP + MS
        int[][] MO_MP_plus_MS = dataManager.sumMatrices(MO_times_MP, MS);

        // Обчислення MIN(MO * MP + MS)
        int result = dataManager.getMinFromMatrix(MO_MP_plus_MS);

        // Виведення результату для малих N
        if (N < 10) {
            System.out.printf("Thread %s. Result = %s\n", name, result);
        }
        System.out.printf("%s is finished.\n", name);
    }
}
