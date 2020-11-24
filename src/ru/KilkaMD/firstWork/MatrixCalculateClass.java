package ru.KilkaMD.firstWork;

import static java.lang.Math.abs;

/**
 * MatrixCalculateClass - класс с реализацией основных функций программы для решения СЛАУ схемой Гаусса, нахождения обратной матрицы и вычисления числа обусловленности
 */
public class MatrixCalculateClass {

    /**
     * Метод Гаусса для решения СЛАУ с выбором ведущего элемента по строкам и столбцам
     * @param matrix расширенная матрица A задачи
     * @param rows порядок матрицы A задачи
     */
    public static void gaussWithMax(MatrixClass matrix, int rows) {
        MatrixClass resMatrix = new MatrixClass();
        MatrixClass helpMatrix = new MatrixClass();
        resMatrix.setSize(rows, rows + 1);
        helpMatrix.setSize(rows, rows + 1);
        double vector[] = new double[rows];
        resMatrix.setRowsOrderLength(rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows + 1; j++) {
                resMatrix.setElem(i, j, matrix.getElem(i, j));
                helpMatrix.setElem(i, j, matrix.getElem(i, j));
            }
        }
        for (int i = 0; i < rows; i++) {
            resMatrix.maxElem(rows, rows, i);
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < rows + 1; y++) {
                    helpMatrix.setElem(x, y, resMatrix.getElem(x, y));
                }
            }
            for (int j = i + 1; j < rows; j++) {
                for (int k = i; k < rows + 1; k++) {
                    double tmp = resMatrix.getElem(j, k) - ((resMatrix.getElem(i, k) * helpMatrix.getElem(j, i)) / resMatrix.getElem(i, i));
                    resMatrix.setElem(j, k, tmp);
                }
            }
        }
        for (int i = rows - 1; i >= 0; --i) {
            double tmp = resMatrix.getElem(i, rows);
            if (resMatrix.getElem(i, i) != 0) {
                for (int j = i + 1; j < rows; ++j) {
                    tmp -= resMatrix.getElem(i, j) * vector[resMatrix.getRowsOrder(j)];
                }
                tmp /= resMatrix.getElem(i, i);
                vector[resMatrix.getRowsOrder(i)] = tmp;
            } else {
                vector[resMatrix.getRowsOrder(i)] = 0;
            }
        }
        System.out.println("Матрица системы: ");
        matrix.printMatrix(rows);
        System.out.println("Верхнетреугольная матрица: ");
        resMatrix.printMatrix(rows);
        System.out.println("Вектор решения: ");
        for (int i = 0; i < rows; ++i) {
            System.out.println("x_" + i + " = " + vector[i]);
        }
        System.out.println("Вектор невязки: ");
        for (int i = 0; i < rows; ++i) {
            double sum = 0;
            for (int j = 0; j < rows; ++j) {
                sum += matrix.getElem(i, j) * vector[j];
            }
            System.out.println("R_" + i + " = " + (matrix.getElem(i, rows) - sum));
        }
    }

    /**
     * Метод для нахождения обратной матрицы к A с помощью LU-разложения
     * @param matrix матрица A условия
     * @param rows порядок матрицы A
     * @return обратная матрица A^(-1)
     */
    public static MatrixClass reverseMatrix(MatrixClass matrix, int rows) {
        MatrixClass reverseMatrix = new MatrixClass();
        MatrixClass lMatrix = new MatrixClass();
        MatrixClass uMatrix = new MatrixClass();
        reverseMatrix.setSize(rows, rows);
        lMatrix.setSize(rows, rows);
        uMatrix.setSize(rows, rows);
        double sum;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                lMatrix.setElem(i, j, 0);
                uMatrix.setElem(i, j, 0);
                if (i == j) {
                    uMatrix.setElem(i, j, 1);
                }
            }
        }
        for (int i = 0; i < rows; ++i) {
            lMatrix.setElem(i, 0, matrix.getElem(i, 0));
            uMatrix.setElem(0, i, matrix.getElem(0, i) / lMatrix.getElem(0, 0));
        }
        for (int i = 1; i < rows; ++i) {
            for (int j = 1; j < rows; ++j) {
                if (i >= j) {
                    sum = 0;
                    for (int k = 0; k < j; k++) sum += lMatrix.getElem(i, k) * uMatrix.getElem(k, j);
                    lMatrix.setElem(i, j, matrix.getElem(i, j) - sum);
                } else {
                    sum = 0;
                    for (int k = 0; k < i; k++) sum += lMatrix.getElem(i, k) * uMatrix.getElem(k, j);
                    uMatrix.setElem(i, j, (matrix.getElem(i, j) - sum) / lMatrix.getElem(i, i));
                }
            }
        }
        System.out.println("Матрица U: ");
        uMatrix.printMatrixLU(rows);
        System.out.println("Матрица L: ");
        lMatrix.printMatrixLU(rows);

        for (int iter = 0; iter < rows; ++iter) {
            double lVector[] = new double[rows];
            double uVector[] = new double[rows];
            if (iter == 0) {
                lVector[0] = 1 / lMatrix.getElem(0, 0);
            } else lVector[0] = 0;
            for (int i = 1; i < rows; ++i) {
                if (iter == i) {
                    sum = 1;
                } else sum = 0;
                for (int j = 0; j < i; ++j) {
                    sum -= lVector[j] * lMatrix.getElem(i, j);
                }
                sum /= lMatrix.getElem(i, i);
                lVector[i] = sum;
            }
            uVector[rows - 1] = lVector[rows - 1];
            for (int i = rows - 2; i >= 0; --i) {
                sum = lVector[i];
                for (int j = i + 1; j < rows; ++j) {
                    sum -= uVector[j] * uMatrix.getElem(i, j);
                }
                uVector[i] = sum;
            }
            for (int i = 0; i < rows; ++i) {
                reverseMatrix.setElem(i, iter, uVector[i]);
            }
        }
        return reverseMatrix;
    }

    /**
     * Метод для нахождения нормы матрицы, как максимальной суммы строчных элементов
     * @param matrix матрица, норму которой необходимо найти
     * @param rows порядок матрицы
     * @return норма матрицы
     */
    public static double norma(MatrixClass matrix, int rows) {
        double max = 0, sum;
        for (int i = 0; i < rows; ++i) {
            sum = 0;
            for (int j = 0; j < rows; ++j) {
                sum += abs(matrix.getElem(i, j));
            }
            if (sum > max) max = sum;
        }
        return max;
    }
}
