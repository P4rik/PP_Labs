import java.util.Scanner;
import java.util.Arrays;

public class T2 extends Thread{
    String name;
    final int N;
    Data dataManager;

    public T2(String name, int N, int priority) {
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
        int[][] MG, MH, MK, ML;

        // Синхронізований блок для безпечної роботи з введенням
        synchronized (System.in) {
            if (N == 3) {
                // Якщо N = 3, введення вручну
                MG = dataManager.inputMatrix(N, "MG", name);
                MH = dataManager.inputMatrix(N, "MH", name);
                MK = dataManager.inputMatrix(N, "MK", name);
                ML = dataManager.inputMatrix(N, "ML", name);
            } else {
                // Для більшого N – вибір методу заповнення
                Scanner in = new Scanner(System.in);
                System.out.print("Choose a Filling Method:\n1 - Random Filling\n2 - Single Element Filling\nYour Choice: ");
                switch (in.nextInt()) {
                    case 1:
                        MG = dataManager.generateRandomMatrix(N);
                        MH = dataManager.generateRandomMatrix(N);
                        MK = dataManager.generateRandomMatrix(N);
                        ML = dataManager.generateRandomMatrix(N);
                        break;
                    case 2:
                        System.out.print("Enter value to fill elements: ");
                        int value = in.nextInt();
                        MG = dataManager.matrixFixedValue(N, value);
                        MH = dataManager.matrixFixedValue(N, value);
                        MK = dataManager.matrixFixedValue(N, value);
                        ML = dataManager.matrixFixedValue(N, value);
                        break;
                    default:
                        System.out.println("Invalid choice. Exiting...");
                        return; // Виходимо, якщо вибір некоректний
                }
            }
        }
        // Обчислення MG * MH
        int[][] MG_times_MH = dataManager.multiplyMatrices(MG, MH);

        // Обчислення MK + ML
        int[][] MK_plus_ML = dataManager.sumMatrices(MK, ML);

        // Обчислення (MG * MH) * (MK + ML)
        int[][] result = dataManager.multiplyMatrices(MG_times_MH, MK_plus_ML);

        // Виведення результату для малих N
        if (N < 10) {
            System.out.printf("Thread %s. Result = %s\n", name, Arrays.deepToString(result));
        }
        System.out.printf("%s is finished.\n", name);
    }
}
