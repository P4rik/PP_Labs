using System;
using System.Threading;

namespace Lab3
{
    public class T2
    {
        private Resources resources;

        private int begin;
        private int end;

        public T2(Resources resources)
        {
            this.resources = resources;
            begin = resources.h;
            end = begin + resources.h;
        }
        public void Run()
        {
            Console.WriteLine("T2 started");
            try
            {
                // Очікування даних з T1, T3 та T4
                Program.S1.WaitOne();
                Program.S2.WaitOne();
                Program.S3.WaitOne();

                // Розрахунок 1: mi = max(Zh)
                int mi = Data.FindMaxValue(resources.Z, begin, end);
                // Критична ділянка 1 
                // Розрахунок 2: m = max(m, mi)
                Interlocked.Exchange(ref resources.m, Math.Max(resources.m, mi));

                // Сигнал потокам T1, T3, T4, про обчислення максимуму m
                Program.E2.Set();
                // Очікування сигналу від T1
                Program.E1.WaitOne();
                // Очікування сигналу від T3
                Program.E3.WaitOne();
                // Очікування сигналу від T4
                Program.E4.WaitOne();

                // Критична ділянка 2: копіювання d
                int d2;
                lock (Program.Lock1)
                {
                    d2 = resources.d;
                }
                // Критична ділянка 3: копіювання m
                Program.M1.WaitOne();
                int m2 = resources.m;
                Program.M1.ReleaseMutex();

                // Розрахунок 3: MUh = (MDh * MC) * d + m * MRh
                int[,] MU2 = Data.MatrixAddition(Data.MultiplyScalarMatrix(Data.MultiplyMatrix(resources.MD, resources.MC, begin, end), d2), Data.MultiplyScalarMatrix(resources.MR, m2, begin, end));
                Data.FinalMatrix(resources.MU, MU2, begin, end);
                // Очікування сигналу від потоків T1, T3, T4 про обрахунок MUh
                Program.B1.SignalAndWait();
            }
            catch (ThreadInterruptedException)
            {
                Console.WriteLine("T2 interrupted");
                Thread.CurrentThread.Interrupt();
            }
        }
    }
}