import java.util.Scanner;
/* -----------------------------------------------------
-- Паралельне програмування --
-- --
-- Лабораторна робота No1 --
-- --
-- Варіант: --
-- F1 -> d = MAX(B + C) + MIN(A + B*(MA*ME)) (1.19) --
-- F2 -> MF = (MG *MH)*(MK + ML) (2.22) --
-- F3 -> s = MIN(MO*MP+MS) (3.24) --
-- --
-- Виконав: Паровенко Данило Едуардович --
-- Група: ІО-24 --
-- Дата: 01.10.2024 --
----------------------------------------------------- */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter N: ");
        final int N = in.nextInt();

        T1 t1 = new T1("T1", N, Thread.MAX_PRIORITY);
        T2 t2 = new T2("T2", N, Thread.MIN_PRIORITY);
        T3 t3 = new T3("T3", N, Thread.NORM_PRIORITY);

        t1.start();
        t2.start();
        t3.start();

        long start = System.currentTimeMillis();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms.");
        in.close();
    }
}
