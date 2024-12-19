#include <iostream>
#include <algorithm>
#include <omp.h>
#include "Data.h"

/* ------------------------------------------------------------
-- ���������� �������������                                  --
--                                                           --
-- ����������� ������ No5                                    --
--                                                           --
-- ������: MR = MB*(MC*MM)*d + min(Z)*MC                    --
--                                                           --
-- �������: ��������� ������                                 --
-- �����: ��-24                                              --
-- ����: 11.12.2024                                          --
------------------------------------------------------------ */

using namespace std;

int main() {
	cout << "Enter N = ";
	cin >> Data::N;
	int P = 4;
	int H = Data::N / P;

	Data::Z = new int[Data::N];
	Data::MR = new int* [Data::N];
	Data::MB = new int* [Data::N];
	Data::MC = new int* [Data::N];
	Data::MD = new int* [Data::N];
	Data::MM = new int* [Data::N];
	Data::MA = new int* [Data::N];

	for (int i = 0; i < Data::N; i++) {
		Data::MR[i] = new int[Data::N];
		Data::MB[i] = new int[Data::N];
		Data::MC[i] = new int[Data::N];
		Data::MD[i] = new int[Data::N];
		Data::MM[i] = new int[Data::N];
		Data::MA[i] = new int[Data::N];
	}


	int tid, di, mi;
	double startTime = omp_get_wtime();
#pragma omp parallel num_threads(P) private(tid, di, mi)
	{
		tid = omp_get_thread_num() + 1;
#pragma omp critical
		{
			cout << "Thread T" << tid << " started" << endl;
		}
		int start = (tid - 1) * H;
		int end = start + H;

		// �������� �����.
		switch (tid) {
		case 1: {
			Data::initializeMatrix(Data::MB);
			break;
		}
		case 3: {
			Data::initializeMatrix(Data::MC);
			break;
		}
		case 4: {
			Data::initializeVector(Data::Z);
			Data::initializeMatrix(Data::MM);
			Data::d = 1;
			break;
		}
		}
		// ���������� ������� ���� ���������� �������� ����� � ��� �������.
#pragma omp barrier


		// ���������� 1: MAH = MC * MMH.
		Data::multiplyMatrices(start, end, Data::MA, Data::MC, Data::MM);
#pragma omp barrier

		// ���������� 2: mi = min(Zh).
		mi = Data::vectorMin(start, end, Data::Z);

		// ��1: ���������� 3: m = min(m, mi).
#pragma omp critical
		{
			Data::m = min(Data::m, mi);
		}
		// ������ ������ ��� ���������� m � ��� �������.
#pragma omp barrier

		// ��2: ��������� di = d.
#pragma omp critical
		{
			di = Data::d;
		}

		// ��3: ��������� mi = m.
#pragma omp critical
		{
			mi = Data::m;
		}

		// ���������� 4: MRh = MBh * MAh * d + m * MC
		Data::multiplyMatrices(start, end, Data::MD, Data::MB, Data::MA);
		Data::scaleMatrix(start, end, Data::MD, di);
		Data::scaleMatrix(start, end, Data::MC, mi);
		Data::addMatrices(start, end, Data::MR, Data::MD, Data::MC);

		// ���������� ������� ���� ���������� ���������� MRh � ��� �������.
#pragma omp barrier

		if ((tid == 2) && (Data::N < 12)) {
			cout << "Result Matrix:" << endl;
			for (int i = 0; i < Data::N; i++) {
				for (int j = 0; j < Data::N; j++) {
					cout << Data::MR[i][j] << " ";
				}
				cout << endl;
			}
			cout << endl;
		}
	}
	double endTime = omp_get_wtime();
	cout << "Execution time: " << (endTime - startTime) * 1000 << " ms" << endl;
	return 0;
}