using System;
using System.Threading;

namespace Lab3
{
    public class T4
    {
        private Resources resources;

        private int begin;
        private int end;

        public T4(Resources resources)
        {
            this.resources = resources;
            begin = 3 * resources.h;
            end = begin + resources.h;
        }
        public void Run()
        {
            Console.WriteLine("T4 started");
            try
            {
                // Введення даних
                Data.InitializeVector(resources.Z, 1);
                Data.InitializeMatrix(resources.MD, 1);

                // Очікування даних з T1 та T3
                Program.S3.Release(3);
                Program.S2.WaitOne();
                Program.S1.WaitOne();

                // Розрахунок 1: mi = max(Zh)
                int mi = Data.FindMaxValue(resources.Z, begin, end);
                // Критична ділянка 1 
                // Розрахунок 2: m = max(m, mi)
                Interlocked.Exchange(ref resources.m, Math.Max(resources.m, mi));

                // Сигнал потокам T1, T2, T3, про обчислення максимуму m
                Program.E4.Set();
                // Очікування сигналу від T1
                Program.E1.WaitOne();
                // Очікування сигналу від T2
                Program.E2.WaitOne();
                // Очікування сигналу від T3
                Program.E3.WaitOne();

                // Критична ділянка 2: копіювання d
                int d4;
                lock (Program.Lock1)
                {
                    d4 = resources.d;
                }
                // Критична ділянка 3: копіювання m
                Program.M1.WaitOne();
                int m4 = resources.m;
                Program.M1.ReleaseMutex();

                // Розрахунок 3: MUh = (MDh * MC) * d + m * MRh
                int[,] MU4 = Data.MatrixAddition(Data.MultiplyScalarMatrix(Data.MultiplyMatrix(resources.MD, resources.MC, begin, end), d4), Data.MultiplyScalarMatrix(resources.MR, m4, begin, end));
                Data.FinalMatrix(resources.MU, MU4, begin, end);

                // Очікування сигналу від потоків T1, T2, T3 про обрахунок MUh
                Program.B1.SignalAndWait();
            }
            catch (ThreadInterruptedException)
            {
                Console.WriteLine("T4 interrupted");
                Thread.CurrentThread.Interrupt();
            }
        }
    }
}
