public class Monitor {
    private final Resources resources;

    private int inputCompletedCounter;
    private int calculationMCompletedCounter;
    private int calculationKCompletedCounter;
    private int finalCalculationCompletedCounter;

    public Monitor(Resources resources) {
        this.resources = resources;

        inputCompletedCounter = 0;
        calculationMCompletedCounter = 0;
        calculationKCompletedCounter = 0;
        finalCalculationCompletedCounter = 0;
    }

    public synchronized void signalOthersAboutSuccessfulInput() {
        inputCompletedCounter++;
        if (inputCompletedCounter == resources.p - 1) {
            System.out.println("All data has been inputted successfully");
            notifyAll();
        }
    }

    public synchronized void waitOthersToSuccessfulInput() throws InterruptedException {
        if (inputCompletedCounter != resources.p - 1) {
            wait();
        }
    }

    public synchronized void calculationMin(int valueM){
        if(resources.m > valueM){
            resources.m = valueM;
        }
    }

    public synchronized void signalCalculationMin(){
        calculationMCompletedCounter += 1;
        if(calculationMCompletedCounter == resources.p){
            notifyAll();
        }
    }


    public synchronized void waitCalculationMin() throws InterruptedException {
        if(calculationMCompletedCounter != resources.p){
            wait();
        }
    }

    public synchronized void calculationMax(int valueK){
        if(resources.k < valueK){
            resources.k = valueK;
        }
    }

    public synchronized void signalCalculationMax(){
        calculationKCompletedCounter += 1;
        if(calculationKCompletedCounter == resources.p){
            notifyAll();
        }
    }

    public synchronized void waitCalculationMax() throws InterruptedException {
        if(calculationKCompletedCounter != resources.p){
            wait();
        }
    }

    public synchronized int copyScalarM(){
        return resources.m;
    }

    public synchronized int copyScalarK(){
        return resources.k;
    }

    public synchronized int copyScalarD(){
        return resources.d;
    }


    public synchronized void signalFinalCalculation(){
        finalCalculationCompletedCounter += 1;
        if(finalCalculationCompletedCounter == resources.p - 1){
            notifyAll();
        }
    }

    public synchronized void waitFinalCalculation() throws InterruptedException {
        if(finalCalculationCompletedCounter != resources.p - 1){
            wait();
        }
    }
}