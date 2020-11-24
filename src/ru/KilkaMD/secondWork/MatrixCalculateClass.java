package ru.KilkaMD.secondWork;

import static java.lang.Math.*;

/**
 * MatrixCalculateClass - класс с реализацией основных функций программы для решения СЛАУ итерационными методами
 */
public class MatrixCalculateClass {
    /**
     * Переменная(массив) для хранения фактического числа итераций приведенных методов решения СЛАУ
     */
    private int factK[] = {-1, -1, -1};

    /**
     * Метод для отображения фактического числа итераций приведенных методов решения СЛАУ
     */
    public void watchFactK() {
        System.out.println("Note: Если значение равно \"-1\", то это означает, что вычисление этого метода ещё не происходило. Произведите его через меню и вернитесь обратно к результатам");
        System.out.println("Фактическое число итераций метода простой итерации = " + factK[0]);
        System.out.println("Фактическое число итераций метода Зейделя = " + factK[1]);
        System.out.println("Фактическое число итераций метода верхней релаксации = " + factK[2]);
    }

    /**
     * Метод Гаусса для решения СЛАУ с выбором ведущего элемента по строкам и столбцам
     * @param matrix расширенная матрица A задачи
     * @param rows порядок матрицы A задачи
     * @return вектор точного решения СЛАУ
     */
    public static MatrixClass gaussWithMax(MatrixClass matrix, int rows) {
        MatrixClass resMatrix = new MatrixClass();
        MatrixClass helpMatrix = new MatrixClass();
        resMatrix.setSize(rows, rows + 1);
        helpMatrix.setSize(rows, rows + 1);
        MatrixClass vector = new MatrixClass();
        vector.setSize(rows, 1);
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
                    tmp -= resMatrix.getElem(i, j) * vector.getElem(resMatrix.getRowsOrder(j), 0);
                }
                tmp /= resMatrix.getElem(i, i);
                vector.setElem(resMatrix.getRowsOrder(i), 0, tmp);
            } else {
                vector.setElem(resMatrix.getRowsOrder(i), 0, 0);
            }
        }
        System.out.println("Точное решение системы: ");
        for (int i = 0; i < rows; ++i) {
            System.out.println("x_" + i + " = " + vector.getElem(i, 0));
        }
        return vector;
    }

    /**
     * Метод для поиска матрицы H из разложения x = Hx+g
     * @param matrix матрица A условия
     * @param rows порядок матрицы A
     * @return матрица H
     */
    public static MatrixClass findH(MatrixClass matrix, int rows) {
        MatrixClass matrixX = new MatrixClass();
        matrixX.setSize(rows, rows);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                if (i == j) {
                    matrixX.setElem(i, j, 0);
                } else {
                    matrixX.setElem(i, j, -(matrix.getElem(i, j) / matrix.getElem(i, i)));
                }
            }
        }
        return matrixX;
    }

    /**
     * Метод для поиска вектора g из разложения x = Hx+g
     * @param matrix матрица A условия
     * @param rows порядок матрицы A
     * @return вектор g
     */
    public static MatrixClass findG(MatrixClass matrix, int rows) {
        MatrixClass gVector = new MatrixClass();
        gVector.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            gVector.setElem(i, 0, (matrix.getElem(i, rows) / matrix.getElem(i, i)));
        }
        return gVector;
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

    /**
     * Метод для нахождения нормы вектора(максимального по абсолютной величине его компонента)
     * @param matrix вектор
     * @param rows высота вектора
     * @return норма вектора
     */
    public static double normaVector(MatrixClass matrix, int rows) {
        double max = 0;
        for (int i = 0; i < rows; ++i) {
            if (abs(matrix.getElem(i, 0)) > max) {
                max = abs(matrix.getElem(i, 0));
            }
        }
        return max;
    }

    /**
     * Метод для нахождения априорной оценки того k, при котором ||x − x^(k)|| < ε
     * @param normaH норма матрицы H из разложения x = Hx+g
     * @param normaG норма вектора g из разложения x = Hx+g
     * @param eps ε из условия задачи
     * @return априорная оценка
     */
    public int precision(double normaH, double normaG, double eps) {
        int k = 0;
        double accuracy;
        while (true) {
            accuracy = (pow(normaH, k) * normaG) / (1 - normaH);
            if (accuracy <= eps) {
                break;
            } else {
                if (k == Integer.MAX_VALUE) {
                    System.out.println("Такого k не удалось найти. Будет взято максимально возможное значение");
                    break;
                }
                ++k;
            }
        }
        return k;
    }

    /**
     * Метод для вычисления решения методом простой итерации с точностью ε
     * @param matrixH матрица H из разложения x = Hx+g
     * @param vectorG вектор g из разложения x = Hx+g
     * @param vectorX вектор точного решения СЛАУ методом Гаусса
     * @param rows порядок матрицы H
     * @param eps ε из условия задачи
     * @param apriorK априорной оценки того k, при котором ||x − x^(k)|| < ε
     */
    public void methodIter(MatrixClass matrixH, MatrixClass vectorG, MatrixClass vectorX, int rows, double eps, int apriorK) {
        MatrixCalculateClass calculater = new MatrixCalculateClass();
        MatrixClass xIterVector = new MatrixClass();
        MatrixClass xIterBeforeVector = new MatrixClass();
        xIterVector.setSize(rows, 1);
        xIterBeforeVector.setSize(rows, 1);
        MatrixClass helpVector = new MatrixClass();
        helpVector.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            xIterVector.setElem(i, 0, 0);
        }
        int k = 0;
        double tmp;
        while (true) {
            for (int i = 0; i < rows; ++i) {
                xIterBeforeVector.setElem(i, 0, xIterVector.getElem(i, 0));
            }
            for (int i = 0; i < rows; ++i) {
                tmp = vectorG.getElem(i, 0);
                for (int j = 0; j < rows; ++j) {
                    tmp += matrixH.getElem(i, j) * xIterVector.getElem(j, 0);
                }
                helpVector.setElem(i, 0, tmp);
            }
            for (int i = 0; i < rows; ++i) {
                xIterVector.setElem(i, 0, helpVector.getElem(i, 0));
            }
            for (int i = 0; i < rows; ++i) {
                helpVector.setElem(i, 0, helpVector.getElem(i, 0) - vectorX.getElem(i, 0));
            }
            if (calculater.normaVector(helpVector, rows) < eps) {
                ++k;
                System.out.println("Вектор решения, полученный методом простой итерации: ");
                for (int i = 0; i < rows; ++i) {
                    System.out.println(xIterVector.getElem(i, 0));
                }
                System.out.println("Фактическая погрешность = " + calculater.normaVector(helpVector, rows));
                factK[0] = k;
                System.out.println("Фактическое число итераций = " + k + "\nАприорное значением k = " + apriorK + "\nk/apriorK = " + (double) k / (double) apriorK);
                for (int i = 0; i < rows; ++i) {
                    helpVector.setElem(i, 0, xIterVector.getElem(i, 0) - xIterBeforeVector.getElem(i, 0));
                }
                double normaH = calculater.norma(matrixH, rows);
                double normaG = calculater.normaVector(vectorG, rows);
                double aprior = (pow(normaH, k) * normaG) / (1 - normaH);
                double apostAprior = (normaH * calculater.normaVector(helpVector, rows)) / (1 - normaH);
                System.out.println("Априорная оценка погрешности = " + aprior);
                System.out.println("Апостериорная оценка погрешности = " + apostAprior);
                double maxSob = matrixH.maxSobstv();
                MatrixClass xLusternikVector = new MatrixClass();
                xLusternikVector.setSize(rows, 1);
                for (int i = 0; i < rows; ++i) {
                    xLusternikVector.setElem(i, 0, xIterBeforeVector.getElem(i, 0) + (helpVector.getElem(i, 0)) / (1 - maxSob));
                }
                System.out.println("Уточнение приближённого решение по построенным ранее приближениям: ");
                xLusternikVector.printVector(rows);
                for (int i = 0; i < rows; ++i) {
                    helpVector.setElem(i, 0, xLusternikVector.getElem(i, 0) - vectorX.getElem(i, 0));
                }
                System.out.println("Фактическая погрешность при уточнении последнего приближения = " + calculater.normaVector(helpVector, rows));
                break;
            } else {
                if (k == Integer.MAX_VALUE) {
                    System.out.println("Такого k не удалось найти. Ответ не может быть найден");
                    break;
                }
                ++k;
            }
        }
    }

    /**
     * Метод для вычисления решения системы x = Hx + g методом Зейделя с точностью ε
     * @param matrixH матрица H из разложения x = Hx+g
     * @param gVector вектор g из разложения x = Hx+g
     * @param vectorX вектор точного решения СЛАУ методом Гаусса
     * @param rows порядок матрицы H
     * @param eps ε из условия задачи
     */
    public void zaidel(MatrixClass matrixH, MatrixClass gVector, MatrixClass vectorX, int rows, double eps) {
        MatrixCalculateClass calculater = new MatrixCalculateClass();
        MatrixClass xIterVector = new MatrixClass();
        MatrixClass xIterBeforeVector = new MatrixClass();
        xIterVector.setSize(rows, 1);
        xIterBeforeVector.setSize(rows, 1);
        MatrixClass helpVector = new MatrixClass();
        helpVector.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            xIterVector.setElem(i, 0, 0);
        }
        int k = 0;
        double tmp;
        while (true) {
            for (int i = 0; i < rows; ++i) {
                xIterBeforeVector.setElem(i, 0, xIterVector.getElem(i, 0));
            }
            for (int i = 0; i < rows; ++i) {
                tmp = gVector.getElem(i, 0);
                for (int j = 0; j <= i - 1; ++j) {
                    tmp += matrixH.getElem(i, j) * xIterVector.getElem(j, 0);
                }
                for (int j = i; j < rows; ++j) {
                    tmp += matrixH.getElem(i, j) * xIterBeforeVector.getElem(j, 0);
                }
                xIterVector.setElem(i, 0, tmp);
            }
            for (int i = 0; i < rows; ++i) {
                helpVector.setElem(i, 0, xIterVector.getElem(i, 0) - vectorX.getElem(i, 0));
            }
            if (calculater.normaVector(helpVector, rows) < eps) {
                ++k;
                factK[1] = k;
                System.out.println("Вектор решения, полученный методом Зайделя: ");
                for (int i = 0; i < rows; ++i) {
                    System.out.println(xIterVector.getElem(i, 0));
                }
                System.out.println("Фактическое число итераций = " + k);
                break;
            } else {
                if (k == Integer.MAX_VALUE) {
                    System.out.println("Такого k не удалось найти. Ответ не может быть найден");
                    break;
                }
                ++k;
            }
        }
    }

    /**
     * Метод для вычисления спектрального радиуса матрицы перехода, если рассматривать метод Зейделя как метод простой итерации
     * @param matrixH матрица H для представления её в виде H = H_l + H_r
     * @param rows порядок матрицы H
     * @return спектральный радиус
     */
    public double findSpecRadius(MatrixClass matrixH, int rows) {
        double radius;
        MatrixClass matrixHL = new MatrixClass();
        matrixHL.setSize(rows, rows);
        MatrixClass matrixHR = new MatrixClass();
        matrixHR.setSize(rows, rows);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                matrixHL.setElem(i, j, 0);
                matrixHR.setElem(i, j, 0);
            }
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                if (i == j) {
                    continue;
                }
                if (i > j) {
                    matrixHL.setElem(i, j, matrixH.getElem(i, j));
                } else {
                    matrixHR.setElem(i, j, matrixH.getElem(i, j));
                }
            }
        }
        System.out.println("Матрица H_L: ");
        matrixHL.printMatrix(rows);
        System.out.println("Матрица H_R: ");
        matrixHR.printMatrix(rows);
        radius = matrixHL.hZaid(matrixHR, rows);
        return radius;
    }

    /**
     * Метод для вычисления решения системы Ax = b методом верхней релаксации с точностью ε
     * @param matrixH матрица H из разложения x = Hx+g
     * @param gVector вектор g из разложения x = Hx+g
     * @param vectorX вектор точного решения СЛАУ методом Гаусса
     * @param rows порядок матрицы H
     * @param eps ε из условия задачи
     * @param specRadius спектральный радиус, вычисленный при помощи метода maxSobstv()
     * @see MatrixClass#maxSobstv()
     */
    public void relax(MatrixClass matrixH, MatrixClass gVector, MatrixClass vectorX, int rows, double eps, double specRadius) {
        double qOpt = 2 / (1 + sqrt(1 - pow(specRadius, 2)));
        MatrixCalculateClass calculater = new MatrixCalculateClass();
        MatrixClass xIterVector = new MatrixClass();
        MatrixClass xIterBeforeVector = new MatrixClass();
        xIterVector.setSize(rows, 1);
        xIterBeforeVector.setSize(rows, 1);
        MatrixClass helpVector = new MatrixClass();
        helpVector.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            xIterVector.setElem(i, 0, 0);
        }
        int k = 0;
        double tmp;
        while (true) {
            for (int i = 0; i < rows; ++i) {
                xIterBeforeVector.setElem(i, 0, xIterVector.getElem(i, 0));
            }
            for (int i = 0; i < rows; ++i) {
                tmp = xIterBeforeVector.getElem(i, 0) + qOpt * gVector.getElem(i, 0) - qOpt * xIterBeforeVector.getElem(i, 0);
                for (int j = 0; j <= i - 1; ++j) {
                    tmp += qOpt * matrixH.getElem(i, j) * xIterVector.getElem(j, 0);
                }
                for (int j = i + 1; j < rows; ++j) {
                    tmp += qOpt * matrixH.getElem(i, j) * xIterBeforeVector.getElem(j, 0);
                }
                xIterVector.setElem(i, 0, tmp);
            }
            for (int i = 0; i < rows; ++i) {
                helpVector.setElem(i, 0, xIterVector.getElem(i, 0) - vectorX.getElem(i, 0));
            }
            if (calculater.normaVector(helpVector, rows) < eps) {
                ++k;
                factK[2] = k;
                System.out.println("Вектор решения, полученный методом верхней релаксации: ");
                for (int i = 0; i < rows; ++i) {
                    System.out.println(xIterVector.getElem(i, 0));
                }
                System.out.println("Фактическое число итераций = " + k);
                break;
            } else {
                if (k == Integer.MAX_VALUE) {
                    System.out.println("Такого k не удалось найти. Ответ не может быть найден");
                    break;
                }
                ++k;
            }
        }
    }
}
