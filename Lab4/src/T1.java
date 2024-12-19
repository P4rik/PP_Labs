public class T1 extends Thread {
    private final Resources resources;
    private final Monitor monitor;
    private final int begin;
    private final int end;

    public T1(Resources resources, Monitor monitor) {
        setName("T1");
        this.resources = resources;
        this.monitor = monitor;
        begin = 0;
        end = resources.h;
    }

    @Override
    public void run() {
        System.out.println("Thread " + getName() + " started");
        try {
            // Ініціалізація матриці MC та вектору Z
            resources.MC = Data.initializeMatrix(resources.n, 1);
            resources.Z = Data.initializeVector(resources.n, 1);

            // Очікування сигналу завершення заповнення в інших потоках
            monitor.signalOthersAboutSuccessfulInput();
            monitor.waitOthersToSuccessfulInput();

            // Розрахунок 1: mi = min(Zh)
            int Mi1 = Data.findMinValue(Data.extractSubVector(begin, end, resources.Z));

            // КД1:
            // Розрахунок 2: m = min(m, mi)
            monitor.calculationMin(Mi1);

            // Очікуємо на завершення обрахунку mi в іних потоках
            monitor.signalCalculationMin();
            monitor.waitCalculationMin();

            // Розрахунок 3: ki = max(Zh)
            int Ki1 = Data.findMaxValue(Data.extractSubVector(begin, end, resources.Z));

            // КД2:
            // Розрахунок 4: k = max(k, ki)
            monitor.calculationMax(Ki1);

            // Очікуємо на завершення обрахунку ki в іних потоках
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
            resources.MA1 = Data.addMatrix(
                    Data.multiplyScalarMatrix(Data.extractSubMatrix(begin, end, resources.MX), m),
                    Data.multiplyScalarMatrix(Data.multiplyMatrix(resources.MR, resources.MC, begin, end), k * d)
            );

            // Сигнал для потоку T3
            monitor.signalFinalCalculation();

        } catch (InterruptedException e) {
            System.out.printf("%s is interrupted\n", getName());
            Thread.currentThread().interrupt();
        }
    }
}
