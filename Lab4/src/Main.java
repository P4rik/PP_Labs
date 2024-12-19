/* -----------------------------------------------------
-- Паралельне програмування                           --
--                                                    --
-- Лабораторна робота №4                              --
--                                                    --
-- Варіант: MA = min(Z)*MX+max(Z)*(MR*MC)*d           --
--                                                    --
-- Виконав: Паровенко Данило                          --
-- Група: ІО-24                                       --
-- Дата: 27.11.2024                                   --
----------------------------------------------------- */

import java.util.Scanner;


public class Main {
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the size of vectors and matrices: ");
        final int n = in.nextInt();

        Resources resources = new Resources(NUMBER_OF_THREADS, n);
        Monitor monitor = new Monitor(resources);

        T1 t1 = new T1(resources, monitor);
        T2 t2 = new T2(resources, monitor);
        T3 t3 = new T3(resources, monitor);
        T4 t4 = new T4(resources, monitor);

        long start = System.currentTimeMillis();

        // Старт потоків
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException ex) {
            System.out.printf("%nAn error occurred: %s%n", ex.getMessage());
            Thread.currentThread().interrupt();
        }

        long end = System.currentTimeMillis();
        System.out.println("\nTime taken: " + (end - start) + " ms.");
    }
}

