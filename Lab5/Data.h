#pragma once

#include <iostream>

class Data {
public:
	static int N;
	static int* Z;
	static int d;
	static int m;
	static int** MR;
	static int** MB;
	static int** MC;
	static int** MD;
	static int** MM;
	static int** MA;


	static void initializeMatrix(int** matrix);
	static void initializeVector(int* vector);
	static void multiplyMatrices(int startRow, int endRow, int** result, int** matrixA, int** matrixB);
	static void scaleMatrix(int startIndex, int endIndex, int** matrix, int scalar);
	static int vectorMin(int start, int end, int* vector);
	static void addMatrices(int startIndex, int endIndex, int** result, int** matrixA, int** matrixB);
};