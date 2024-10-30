import java.util.concurrent.atomic.AtomicInteger;

public class Resources {
    public final int p;
    public final int n;
    public final int h;

    public int[] Z;
    public int[] A;
    public int[][] MV;
    public int[][] MC;

    // Спільні ресурси
    public int[][] MM;
    public int[] X;
    public int[] B;
    public int e;
    public AtomicInteger m;


    public Resources(int p, int n) {
        this.p = p;
        this.n = n;
        this.h = n / p;

        // Ініціалізація масивів і змінних
        Z = new int[n];
        A = new int[n];
        X = new int[n];
        B = new int[n];

        MV = new int[n][n];
        MC = new int[n][n];
        MM = new int[n][n];

        m = new AtomicInteger(Integer.MAX_VALUE);
    }
}
