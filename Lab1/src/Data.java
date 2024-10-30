import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Data {
    // Заповнення матриці вручну з клавіатури
    public int[][] inputMatrix(int N, String matrixLabel, String threadLabel) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter matrix %s in Thread %s:\n", matrixLabel, threadLabel);
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    // Заповнення матриці випадковими значеннями
    public int[][] generateRandomMatrix(int N) {
        int[][] matrix = new int[N][N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = rand.nextInt(100);
            }
        }
        return matrix;
    }

    // Заповнення матриці фіксованим значенням
    public int[][] matrixFixedValue(int N, int constant) {
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(matrix[i], constant);
        }
        return matrix;
    }

    // Заповнення вектора вручну з клавіатури
    public int[] inputVector(int N, String vectorLabel, String threadLabel) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter vector %s in Thread %s:\n", vectorLabel, threadLabel);
        int[] vector = new int[N];
        for (int i = 0; i < N; i++) {
            vector[i] = scanner.nextInt();
        }
        return vector;
    }

    // Заповнення вектора випадковими значеннями
    public int[] generateRandomVector(int N) {
        int[] vector = new int[N];
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            vector[i] = rand.nextInt(100);
        }
        return vector;
    }

    // Заповнення вектора фіксованим значенням
    public int[] vectorFixedValue(int N, int constant) {
        int[] vector = new int[N];
        Arrays.fill(vector, constant);
        return vector;
    }

    // Додавання двох векторів
    public int[] sumVectors(int[] vector1, int[] vector2) {
        int[] result = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    // Додавання двох матриць
    public int[][] sumMatrices(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix1.length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    // Множення матриць
    public int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    // Пошук максимального значення в матриці
    public int getMaxFromMatrix(int[][] matrix) {
        int maxValue = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int value : row) {
                maxValue = Math.max(maxValue, value);
            }
        }
        return maxValue;
    }

    // Пошук мінімального значення в матриці
    public int getMinFromMatrix(int[][] matrix) {
        int minValue = Integer.MAX_VALUE;
        for (int[] row : matrix) {
            for (int value : row) {
                minValue = Math.min(minValue, value);
            }
        }
        return minValue;
    }

    // Множення матриці на вектор
    public int[] multiplyMatrixByVector(int[][] matrix, int[] vector) {
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }
}
