#include "Data.h"
#include <algorithm>

using namespace std;

int Data::N = 4;
int* Data::Z;
int Data::d;
int Data::m = INT_MAX;
int** Data::MR;
int** Data::MB;
int** Data::MC;
int** Data::MD;
int** Data::MM;
int** Data::MA;

void Data::initializeMatrix(int** matrix) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            matrix[i][j] = 1;
        }
    }
}

void Data::initializeVector(int* vector) {
    for (int i = 0; i < N; i++) {
        vector[i] = 1;
    }
}


void Data::multiplyMatrices(int startIndex, int endIndex, int** result, int** matrix1, int** matrix2) {
    for (int i = startIndex; i < endIndex; i++) {
        for (int j = 0; j < N; j++) {
            result[i][j] = 0;
            for (int k = 0; k < N; k++) {
                result[i][j] += matrix1[i][k] * matrix2[k][j];
            }
        }
    }
}

void Data::scaleMatrix(int startIndex, int endIndex, int** matrix, int scalar) {
    for (int i = startIndex; i < endIndex; ++i) {
        for (int j = 0; j < Data::N; ++j) {
            matrix[i][j] *= scalar;
        }
    }
}

int Data::vectorMin(int start, int end, int* vector) {
    int min = vector[start];
    for (int i = start; i < end; i++) {
        if (vector[i] < min) {
            min = vector[i];
        }
    }
    return min;
}


void Data::addMatrices(int startIndex, int endIndex, int** result, int** matrixA, int** matrixB) {
    for (int i = startIndex; i < endIndex; ++i) {
        for (int j = 0; j < N; ++j) {
            result[i][j] = matrixA[i][j] + matrixB[i][j];
        }
    }
}