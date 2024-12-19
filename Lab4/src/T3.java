public class T3 extends Thread {
    private final Resources resources;
    private final Monitor monitor;
    private final int begin;
    private final int end;

    public T3(Resources resources, Monitor monitor) {
        setName("T3");
        this.resources = resources;
        this.monitor = monitor;
        begin = 2 * resources.h;
        end = begin + resources.h;
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " started");
        try {
            // Очікування сигналу завершення заповнення в інших потоках
            monitor.waitOthersToSuccessfulInput();

            // Розрахунок 1: mi = min(Zh)
            int Mi3 = Data.findMinValue(Data.extractSubVector(begin, end, resources.Z));

            // КД1:
            // Розрахунок 2: m = min(m, mi)
            monitor.calculationMin(Mi3);

            // Очікуємо на завершення обрахунку mi в інших потоках
            monitor.signalCalculationMin();
            monitor.waitCalculationMin();

            // Розрахунок 3: ki = max(Zh)
            int Ki3 = Data.findMaxValue(Data.extractSubVector(begin, end, resources.Z));

            // КД2:
            // Розрахунок 4: k = max(k, ki)
            monitor.calculationMax(Ki3);

            // Очікуємо на завершення обрахунку ki в інших потоках
            monitor.signalCalculationMax();
            monitor.waitCalculationMax();

            // КД3:
            // Копіювання m
            int m = monitor.copyScalarM();

            // КД4:
            // Копіювання k
            int k = monitor.copyScalarK();

            // КД5:
            // Копіювання d
            int d = monitor.copyScalarD();

            // Розрахунок 5: MAh = m*MXh + k*(MR*MCh) * d
            int[][] MA3 = Data.addMatrix(
                    Data.multiplyScalarMatrix(Data.extractSubMatrix(begin, end, resources.MX), m),
                    Data.multiplyScalarMatrix(Data.multiplyMatrix(resources.MR, resources.MC, begin, end), k * d)
            );

            // Очікування сигналу про обчислення MA
            monitor.waitFinalCalculation();

            // Виведення результату
            if (MA3.length <= 20 && MA3[0].length <= 20) {
                Data.displayMatrix(MA3);
            }

        } catch (InterruptedException e) {
            System.out.printf("%s is interrupted\n", getName());
            Thread.currentThread().interrupt();
        }
    }
}
