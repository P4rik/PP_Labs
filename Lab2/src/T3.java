import java.util.concurrent.BrokenBarrierException;

public class T3 extends Thread {
    private final Resources resources;
    private final int begin;
    private final int end;

    public T3(Resources resources) {
        setName("T3");
        this.resources = resources;
        begin = 2 * resources.h;
        end = begin + resources.h;
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " started");
        try {
            // Ініціалізація вектору Z
            resources.Z = Data.initializeVector(resources.n, 1);
            // Синхронізація всіх потоків на бар'єрі B1
            Main.B1.await();

            // Критична ділянка: зчитування загального ресурсу B
            Main.CS1.lock();
            int[] B3 = resources.B;
            Main.CS1.unlock();

            // Розрахунок 1: Ch = B * MVh
            int[] C3 = Data.multiplyVectorMatrix(B3, resources.MV, begin, end);

            // Критична ділянка: зчитування загального ресурсу MM
            Main.S1.acquire();
            int[][] MM3 = resources.MM;
            Main.S1.release();

            // Розрахунок 2: MAh = MM * MCh
            int[][] MA3 = Data.multiplyMatrix(MM3, resources.MC, begin, end);

            // Розрахунок 3: mi = min(Zh)
            int Mi3 = Data.findMinValue(Data.extractSubVector(begin, end, resources.Z));

            // Розрахунок 4: m = min(m, mi)
            if (Mi3 < resources.m.get()) {
                resources.m.set(Mi3);
            }
            // Дозволяємо наступним потокам продовжити
            Main.S4.release(3);

            // Очікування синхронізації на семафорах S3, S4, S5
            Main.S2.acquire();
            Main.S3.acquire();
            Main.S5.acquire();

            // Критична ділянка: зчитування загального ресурсу X
            Main.CS2.lock();
            int[] X1 = resources.X;
            Main.CS2.unlock();

            // Критична ділянка: зчитування загального ресурсу e
            Main.S6.acquire();
            int eh = resources.e;
            Main.S6.release();

            // Критична ділянка: зчитування загального ресурсу m
            Main.S7.acquire();
            int mh = resources.m.get();
            Main.S7.release();

            // Розрахунок 5: Ah = m * Ch + e * X * MAh
            int[] Ah = Data.addVectors(Data.multiplyScalarVector(C3, mh),
                    Data.multiplyScalarVector(Data.multiplyVectorMatrix(X1, MA3, begin, end), eh));
            Data.updateFinalVector(resources.A, Ah, begin, end);

            // Синхронізація всіх потоків на бар'єрі B2
            Main.B2.await();

            // Виведення вектору результату, якщо його довжина не перевищує 20 елементів
            if (resources.A.length <=20) {
                System.out.print(getName() + ". Vector A: ");
                Data.displayVector(resources.A);
            }

        } catch (InterruptedException | BrokenBarrierException ex) {
            System.out.printf("%s is interrupted\n", getName());
            Thread.currentThread().interrupt();
        }
    }
}
