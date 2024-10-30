/* -----------------------------------------------------
-- Паралельне програмування                           --
--                                                    --
-- Лабораторна робота №2                              --
--                                                    --
-- Варіант: A = min(Z)*(B*MV) + e*X*(MM*MC)           --
--                                                    --
-- Виконав: Паровенко Данило                          --
-- Група: ІО-24                                       --
-- Дата: 30.10.2024                                   --
----------------------------------------------------- */

import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final int NUMBER_OF_THREADS = 4;

    // Бар'єри для синхронізації потоків після введення даних та проміжних обчислень
    public static final CyclicBarrier B1 = new CyclicBarrier(NUMBER_OF_THREADS, () -> {
        System.out.println("All data have been inputted successfully");
    });
    public static final CyclicBarrier B2 = new CyclicBarrier(NUMBER_OF_THREADS);

    // Лок для захисту критичних секцій
    public static final Lock CS1 = new ReentrantLock();
    public static final Lock CS2 = new ReentrantLock();

    // Семафор для керування доступом до матриці MM у першому етапі
    public static final Semaphore S1 = new Semaphore(1);
    // Семафор для синхронізації потоку після обчислення мінімального значення m
    public static final Semaphore S2 = new Semaphore(0);
    // Семафор для сигналізації завершення критичної секції з X для інших потоків
    public static final Semaphore S3 = new Semaphore(0);
    // Семафор для синхронізації доступу до змінної e після завершення T1
    public static final Semaphore S4 = new Semaphore(0);
    // Семафор для передачі управління до T1 після обробки змінної m
    public static final Semaphore S5 = new Semaphore(0);
    // Семафор для керування доступом до змінної e в потоці T1
    public static final Semaphore S6 = new Semaphore(1);
    // Семафор для синхронізації завершення роботи з m і його оновлення
    public static final Semaphore S7 = new Semaphore(1);


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the size of vectors and matrices: ");
        final int n = in.nextInt();

        Resources resources = new Resources(NUMBER_OF_THREADS, n);

        T1 t1 = new T1(resources);
        T2 t2 = new T2(resources);
        T3 t3 = new T3(resources);
        T4 t4 = new T4(resources);

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

