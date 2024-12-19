using System;

namespace Lab3
{
    public class Data
    {
        public static int[,] MultiplyScalarMatrix(int[,] matrix, int scalar, int startRow, int endRow)
        {
            int[,] result = new int[endRow - startRow, matrix.GetLength(1)];
            for (int i = 0; i < endRow - startRow; i++)
            {
                for (int j = 0; j < matrix.GetLength(1); j++)
                {
                    result[i, j] = matrix[i + startRow, j] * scalar;
                }
            }
            return matrix;
        }
        public static int[,] MultiplyScalarMatrix(int[,] matrix, int scalar)
        {
            for (int i = 0; i < matrix.GetLength(0); i++)
            {
                for (int j = 0; j < matrix.GetLength(1); j++)
                {
                    matrix[i, j] *= scalar;
                }
            }
            return matrix;
        }
        public static int[,] MultiplyMatrix(int[,] matrix1, int[,] matrix2, int startRow, int endRow)
        {
            int n = matrix1.GetLength(0);
            int[,] result = new int[endRow - startRow, n];
            for (int i = startRow; i < endRow; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    for (int k = 0; k < n; k++)
                    {
                        result[i - startRow, j] += matrix1[i, k] * matrix2[k,
                        j];
                    }
                }
            }
            return result;
        }
        public static int[,] MultiplyMatrix(int[,] matrix1, int[,] matrix2)
        {
            int rows1 = matrix1.GetLength(0);
            int cols1 = matrix1.GetLength(1);
            int cols2 = matrix2.GetLength(1);
            int[,] result = new int[rows1, cols2];
            for (int i = 0; i < rows1; i++)
            {
                for (int j = 0; j < cols2; j++)
                {
                    for (int k = 0; k < cols1; k++)
                    {
                        result[i, j] += matrix1[i, k] * matrix2[k, j];
                    }
                }
            }
            return result;
        }
        public static int[,] MatrixAddition(int[,] matrix1, int[,] matrix2)
        {
            for (int i = 0; i < matrix1.GetLength(0); i++)
            {
                for (int j = 0; j < matrix1.GetLength(1); j++)
                {
                    matrix1[i, j] += matrix2[i, j];
                }
            }
            return matrix1;
        }
        public static int FindMaxValue(int[] vector, int start, int end)
        {
            int result = int.MinValue;
            for (int i = start; i < end; i++)
            {
                result = Math.Max(result, vector[i]);
            }
            return result;
        }
        public static void DisplayMatrix(int[,] matrix)
        {
            for (int i = 0; i < matrix.GetLength(0); i++)
            {
                for (int j = 0; j < matrix.GetLength(1); j++)
                {
                    Console.Write(matrix[i, j] + " ");
                }
                Console.WriteLine();
            }
        }
        public static void FinalMatrix(int[,] MO, int[,] MOh, int
        startRow, int endRow)
        {
            for (int i = 0; i < MOh.GetLength(0); i++)
            {
                for (int j = 0; j < MOh.GetLength(1); j++)
                {
                    MO[i + startRow, j] = MOh[i, j];
                }
            }
        }
        public static void InitializeVector(int[] vector, int value)
        {
            for (int i = 0; i < vector.Length; i++)
            {
                vector[i] = value;
            }
        }
        public static void InitializeMatrix(int[,] matrix, int value)
        {
            for (int i = 0; i < matrix.GetLength(0); i++)
            {
                for (int j = 0; j < matrix.GetLength(1); j++)
                {
                    matrix[i, j] = value;
                }
            }
        }
    }
}
