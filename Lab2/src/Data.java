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

    // Множення вектора на матрицю
    public static int[] multiplyVectorMatrix(int[] vector, int[][] matrix, int begin, int end) {
        int numCols = end - begin;
        int[] result = new int[numCols];
        int[][] slicedMatrix = new int[matrix.length][numCols];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < numCols; j++) {
                slicedMatrix[i][j] = matrix[i][begin + j];
            }
        }
        for (int i = 0; i < slicedMatrix.length; i++) {
            for (int j = 0; j < slicedMatrix[i].length; j++) {
                result[j] += slicedMatrix[i][j] * vector[i];
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

    // Виділення частини вектора
    public static int[] extractSubVector(int start, int end, int[] vector) {
        int[] subVector = new int[end - start];
        for (int i = 0; i < end - start; i++) {
            subVector[i] = vector[i + start];
        }
        return subVector;
    }

    // Множення вектора на скаляр
    public static int[] multiplyScalarVector(int[] vector, int scalar) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= scalar;
        }
        return vector;
    }

    // Додавання двох векторів
    public static int[] addVectors(int[] vector1, int[] vector2) {
        int[] result = new int[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] + vector2[i];
        }
        return result;
    }

    // Виведення вектора
    public static void displayVector(int[] vector) {
        for (int num : vector) {
            System.out.print(num + " ");
        }
    }

    // Зберігання результату в основний вектор
    public static void updateFinalVector(int[] target, int[] source, int begin, int end) {
        int index = 0;
        for (int i = begin; i < end; i++) {
            target[i] = source[index++];
        }
    }
}
