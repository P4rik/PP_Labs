public class Resources {
    public final int p;
    public final int n;
    public final int h;

    public int[] Z;
    public int[][] MX;
    public int[][] MC;
    public int[][] MA1;
    public int[][] MA2;
    public int[][] MA4;

    // Спільні ресурси
    public int[][] MR;
    public int m;
    public int k;
    public int d;


    public Resources(int p, int n) {
        this.p = p;
        this.n = n;
        this.h = n / p;

        // Ініціалізація масивів і змінних
        Z = new int[n];

        MX = new int[n][n];
        MC = new int[n][n];
        MA1 = new int[n][n];
        MA2 = new int[n][n];
        MA4 = new int[n][n];
        MR = new int[n][n];

        m = Integer.MAX_VALUE;
        k = Integer.MIN_VALUE;
    }
}
