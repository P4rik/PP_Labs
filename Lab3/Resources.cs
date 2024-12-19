using System;

namespace Lab3
{
    public class Resources
    {

        public int p;
        public int n;
        public int h;
        public int[] Z;
        public int[,] MD;
        public int[,] MU;
        public int[,] MR;

        // Спільні ресурси
        public int d;
        public int m;
        public int[,] MC;
        public Resources(int p, int n)
        {
            if (n <= 0) throw new ArgumentException("Розмір векторів і матриць має бути додатнім числом.");
            if (n % p != 0) throw new ArgumentException("Значення 'n' має бути кратним 'p'.");
            this.p = p;
            this.n = n;
            h = n / p;
            Z = new int[n];
            MC = new int[n, n];
            MU = new int[n, n];
            MD = new int[n, n];
            MR = new int[n, n];
        }
    }
}