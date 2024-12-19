import java.util.Arrays;

public class Data {
    // Ініціалізація матриці значенням
    public static int[][] initializeMatrix(int size, int value) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(matrix[i], value);
        }
        return matrix;
    }

    // Ініціалізація вектора значенням
    public static int[] initializeVector(int size, int value) {
        int[] vector = new int[size];
        Arrays.fill(vector, value);
        return vector;
    }

    // Перемноження матриць
    public static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2, int startRow, int endRow) {
        int size = matrix1.length;
        int[][] result = new int[endRow - startRow][size];
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i - startRow][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    // Пошук мінімального елемента вектора
    public static int findMinValue(int[] vector) {
        int minValue = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < minValue) {
                minValue = vector[i];
            }
        }
        return minValue;
    }

    // Пошук максимального елемента вектора
    public static int findMaxValue(int[] vector) {
        int maxValue = vector[0];
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] > maxValue) {
                maxValue = vector[i];
            }
        }
        return maxValue;
    }

    // Виділення частини вектора
    public static int[] extractSubVector(int start, int end, int[] vector) {
        int[] subVector = new int[end - start];
        for (int i = 0; i < end - start; i++) {
            subVector[i] = vector[i + start];
        }
        return subVector;
    }

    // Додавання двох матриць
    public static int[][] addMatrix(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    // Множення матриці на скаляр
    public static int[][] multiplyScalarMatrix(int[][] matrix, int scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return result;
    }

    // Виведення матриці
    public static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    // Вибір частини матриці
    public static int[][] extractSubMatrix(int begin, int end, int[][] matrix) {
        int[][] subMatrix = new int[end - begin][matrix[0].length];
        for (int i = begin; i < end; i++) {
            System.arraycopy(matrix[i], 0, subMatrix[i - begin], 0, matrix[0].length);
        }
        return subMatrix;
    }
}