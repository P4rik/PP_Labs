using System;
using System.Threading;

namespace Lab3
{
    public class T3
    {
        private Resources resources;

        private int begin;
        private int end;

        public T3(Resources resources)
        {
            this.resources = resources;
            begin = 2 * resources.h;
            end = begin + resources.h;
        }
        public void Run()
        {
            Console.WriteLine("T3 started");
            try
            {
                // Введення даних
                Data.InitializeMatrix(resources.MC, 1);
                Data.InitializeMatrix(resources.MR, 1);

                // Очікування даних з T1 та T4
                Program.S2.Release(3);
                Program.S1.WaitOne();
                Program.S3.WaitOne();

                // Розрахунок 1: mi = max(Zh)
                int mi = Data.FindMaxValue(resources.Z, begin, end);
                // Критична ділянка 1 
                // Розрахунок 2: m = max(m, mi)
                Interlocked.Exchange(ref resources.m, Math.Max(resources.m, mi));

                // Сигнал потокам T1, T2, T4, про обчислення максимуму m
                Program.E3.Set();
                // Очікування сигналу від T1
                Program.E1.WaitOne();
                // Очікування сигналу від T2
                Program.E2.WaitOne();
                // Очікування сигналу від T4
                Program.E4.WaitOne();

                // Критична ділянка 2: копіювання d
                int d3;
                lock (Program.Lock1)
                {
                    d3 = resources.d;
                }
                // Критична ділянка 3: копіювання m
                Program.M1.WaitOne();
                int m3 = resources.m;
                Program.M1.ReleaseMutex();

                // Розрахунок 3: MUh = (MDh * MC) * d + m * MRh
                int[,] MU3 = Data.MatrixAddition(Data.MultiplyScalarMatrix(Data.MultiplyMatrix(resources.MD, resources.MC, begin, end), d3), Data.MultiplyScalarMatrix(resources.MR, m3, begin, end));
                Data.FinalMatrix(resources.MU, MU3, begin, end);

                // Очікування сигналу від потоків T1, T2, T4 про обрахунок MUh
                Program.B1.SignalAndWait();
            }
            catch (ThreadInterruptedException)
            {
                Console.WriteLine("T3 interrupted");
                Thread.CurrentThread.Interrupt();
            }
        }
    }
}
