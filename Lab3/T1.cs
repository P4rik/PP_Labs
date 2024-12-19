using System;
using System.Threading;

namespace Lab3
{
    public class T1
    {
        private Resources resources;

        private int begin;
        private int end;

        public T1(Resources resources)
        {
            this.resources = resources;
            begin = 0;
            end = begin + resources.h;
        }
        public void Run()
        {
            Console.WriteLine("T1 started");
            try
            {
                // Введення даних
                resources.d = 1;

                // Очікування даних з T3, T4
                Program.S1.Release(3);
                Program.S2.WaitOne();
                Program.S3.WaitOne();

                // Розрахунок 1: mi = max(Zh)
                int mi = Data.FindMaxValue(resources.Z, begin, end);
                // Критична ділянка 1 
                // Розрахунок 2: m = max(m, mi)
                Interlocked.Exchange(ref resources.m, Math.Max(resources.m, mi));

                // Сигнал потокам T2, T3, T4, про обчислення максимуму m
                Program.E1.Set();
                // Очікування сигналу від T2
                Program.E2.WaitOne();
                // Очікування сигналу від T3
                Program.E3.WaitOne();
                // Очікування сигналу від T4
                Program.E4.WaitOne();

                // Критична ділянка 2: копіювання d
                int d1;
                lock (Program.Lock1)
                {
                    d1 = resources.d;
                }
                // Критична ділянка 3: копіювання m
                Program.M1.WaitOne();
                int m1 = resources.m;
                Program.M1.ReleaseMutex();

                // Розрахунок 3: MUh = (MDh * MC) * d + m * MRh
                int[,] MU1 = Data.MatrixAddition(Data.MultiplyScalarMatrix(Data.MultiplyMatrix(resources.MD, resources.MC, begin, end), d1), Data.MultiplyScalarMatrix(resources.MR, m1, begin, end));
                Data.FinalMatrix(resources.MU, MU1, begin, end);

                // Очікування сигналу від потоків T2, T3, T4 про обрахунок MUh
                Program.B1.SignalAndWait();

                // Вивід результату
                if (resources.n == 4 || resources.n == 8)
                    Data.DisplayMatrix(resources.MU);
            }
            catch (ThreadInterruptedException)
            {
                Console.WriteLine("T1 interrupted");
                Thread.CurrentThread.Interrupt();
            }
        }
    }
}