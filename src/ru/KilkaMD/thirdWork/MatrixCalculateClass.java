package ru.KilkaMD.thirdWork;

import static java.lang.Math.*;

import java.util.Scanner;

public class MatrixCalculateClass {
    private int factK[] = {-1, -1};

    public void watchFactK() {
        System.out.println("Note: Если значение равно \"-1\", то это означает, что вычисление этого метода ещё не происходило. Произведите его через меню и вернитесь обратно к результатам");
        System.out.println("Фактическое число итераций, полученное степенным методом c точностью ε = 0.001 k = " + factK[0]);
        System.out.println("Фактическое число итераций, полученное методом скалярных произведений с точностью ε = 0.000001 k = " + factK[1]);
    }

    public void yakobiMethod(MatrixClass matrixA, int rows, double eps) {
        MatrixClass matrixX = new MatrixClass();
        matrixX.setSize(rows, rows);
        MatrixClass matrixV = new MatrixClass();
        matrixV.setSize(rows, rows);
        MatrixClass matrixHelp = new MatrixClass();
        matrixHelp.setSize(rows, rows);
        MatrixClass matrixIterBefore = new MatrixClass();
        matrixIterBefore.setSize(rows, rows);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                matrixHelp.setElem(i, j, matrixA.getElem(i, j));
                matrixIterBefore.setElem(i, j, matrixA.getElem(i, j));
                if (i == j) {
                    matrixX.setElem(i, j, 1);
                } else {
                    matrixX.setElem(i, j, 0);
                }
            }
        }
        double c, s;
        while (true) {
            int maxIndex[] = matrixHelp.getMaxElemIndex(rows);
            if (abs(matrixHelp.getElem(maxIndex[0], maxIndex[1])) < eps) {
                System.out.println("Собственные числа матрицы A: ");
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < rows; ++j) {
                        if (i == j) {
                            System.out.println("\u03bb_" + i + " = " + matrixHelp.getElem(i, i));
                        }
                    }
                }
                System.out.println("");
                double sum;
                System.out.println("Собственные векторы матрицы A: ");
                for (int i = 0; i < rows; ++i) {
                    sum = 0;
                    System.out.println("Собственный вектор соответствующий \u03bb_" + i + " :");
                    for (int j = 0; j < rows; ++j) {
                        System.out.println(matrixX.getElem(j, i));
                        sum += pow(matrixX.getElem(j, i), 2);
                    }
                    sum = sqrt(sum);
                    System.out.println("Длина вектора = " + sum);
                    System.out.println("");
                }
                break;
            } else {
                c = matrixHelp.findC(maxIndex);
                s = matrixHelp.findS(maxIndex);
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < rows; ++j) {
                        if ((i != maxIndex[0]) && (j != maxIndex[0]) && (i != maxIndex[1]) && (j != maxIndex[1])) {
                            matrixHelp.setElem(i, j, matrixIterBefore.getElem(i, j));
                        } else {
                            if ((i != maxIndex[0]) && (i != maxIndex[1])) {
                                if (j == maxIndex[0]) {
                                    matrixHelp.setElem(i, maxIndex[0], c * matrixIterBefore.getElem(i, maxIndex[0]) + s * matrixIterBefore.getElem(i, maxIndex[1]));
                                    matrixHelp.setElem(maxIndex[0], i, matrixHelp.getElem(i, maxIndex[0]));
                                } else {
                                    if (j == maxIndex[1]) {
                                        matrixHelp.setElem(i, maxIndex[1], -s * matrixIterBefore.getElem(i, maxIndex[0]) + c * matrixIterBefore.getElem(i, maxIndex[1]));
                                        matrixHelp.setElem(maxIndex[1], i, matrixHelp.getElem(i, maxIndex[1]));
                                    }
                                }
                            } else {
                                if ((i == maxIndex[0]) && (j == maxIndex[0])) {
                                    matrixHelp.setElem(maxIndex[0], maxIndex[0], pow(c, 2) * matrixIterBefore.getElem(maxIndex[0], maxIndex[0]) + 2 * c * s * matrixIterBefore.getElem(maxIndex[0], maxIndex[1]) + pow(s, 2) * matrixIterBefore.getElem(maxIndex[1], maxIndex[1]));
                                } else {
                                    if ((i == maxIndex[1]) && (j == maxIndex[1])) {
                                        matrixHelp.setElem(maxIndex[1], maxIndex[1], pow(s, 2) * matrixIterBefore.getElem(maxIndex[0], maxIndex[0]) - 2 * c * s * matrixIterBefore.getElem(maxIndex[0], maxIndex[1]) + pow(c, 2) * matrixIterBefore.getElem(maxIndex[1], maxIndex[1]));
                                    } else {
                                        if ((i == maxIndex[0]) && (j == maxIndex[1])) {
                                            matrixHelp.setElem(maxIndex[0], maxIndex[1], 0);
                                            matrixHelp.setElem(maxIndex[1], maxIndex[0], 0);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < rows; ++j) {
                        matrixV.setElem(i, j, 0);
                        if ((i != maxIndex[0]) && (i != maxIndex[1]) && (i == j)) {
                            matrixV.setElem(i, i, 1);
                        }
                    }
                }
                matrixV.setElem(maxIndex[0], maxIndex[0], c);
                matrixV.setElem(maxIndex[1], maxIndex[1], c);
                matrixV.setElem(maxIndex[0], maxIndex[1], -s);
                matrixV.setElem(maxIndex[1], maxIndex[0], s);

                matrixX.iterX(matrixV);

                for (int i = 0; i < rows; ++i) {
                    for (int j = 0; j < rows; ++j) {
                        matrixIterBefore.setElem(i, j, matrixHelp.getElem(i, j));
                    }
                }
            }
        }
    }

    public void stepMethod(MatrixClass matrixA, int rows, double eps) {
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        MatrixClass vectorY = new MatrixClass();
        vectorY.setSize(rows, 1);
        MatrixClass vectorYBeforeIter = new MatrixClass();
        vectorYBeforeIter.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            vectorY.setElem(i, 0, 1);
            vectorYBeforeIter.setElem(i, 0, 1);
        }
        int k = 0;
        double lyambda = 1, lymbdaBefore;
        while (true) {
            vectorY.iterY(matrixA);
            lymbdaBefore = lyambda;
            for (int i = 0; i < rows; ++i) {
                if (vectorYBeforeIter.getElem(i, 0) != 0) {
                    lyambda = vectorY.getElem(i, 0) / vectorYBeforeIter.getElem(i, 0);
                    break;
                }
            }
            if (k >= 2) {
                if (abs(lyambda - lymbdaBefore) < eps) {
                    break;
                } else {
                    ++k;
                    for (int i = 0; i < rows; ++i) {
                        vectorYBeforeIter.setElem(i, 0, vectorY.getElem(i, 0));
                    }
                }
            } else {
                ++k;
                for (int i = 0; i < rows; ++i) {
                    vectorYBeforeIter.setElem(i, 0, vectorY.getElem(i, 0));
                }
            }
        }
        double sum = 0;
        for (int i = 0; i < rows; ++i) {
            sum += pow(vectorY.getElem(i, 0), 2);
        }
        sum = sqrt(sum);
        for (int i = 0; i < rows; ++i) {
            vectorY.setElem(i, 0, vectorY.getElem(i, 0) / sum);
        }
        System.out.println("Максимальное по модулю собственное число λ матрицы A = " + lyambda);
        System.out.println("Соответствующий ему собственный вектор: ");
        for (int i = 0; i < rows; ++i) {
            System.out.println(vectorY.getElem(i, 0));
        }
        sum = 0;
        for (int i = 0; i < rows; ++i) {
            sum += pow(vectorY.getElem(i, 0), 2);
        }
        sum = sqrt(sum);
        System.out.println("Длина вектора = " + sum);
        System.out.println("");
        System.out.println("Число итераций k = " + k);
        factK[0] = k;
    }

    public void scalProiz(MatrixClass matrixA, int rows, double eps) {
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        MatrixClass vectorY = new MatrixClass();
        vectorY.setSize(rows, 1);
        MatrixClass vectorYBeforeIter = new MatrixClass();
        vectorYBeforeIter.setSize(rows, 1);
        for (int i = 0; i < rows; ++i) {
            vectorY.setElem(i, 0, 1);
        }
        double normaY;
        normaY = calculator.normaVector(vectorY, rows);
        for (int i = 0; i < rows; ++i) {
            vectorY.setElem(i, 0, vectorY.getElem(i, 0) / normaY);
            vectorYBeforeIter.setElem(i, 0, vectorY.getElem(i, 0));
        }
        int k = 0;
        double lyambda = 1, lymbdaBefore, denominator, numerator;
        while (true) {
            lymbdaBefore = lyambda;
            vectorY.iterY(matrixA);
            denominator = calculator.scal(vectorYBeforeIter, vectorYBeforeIter, rows);
            numerator = calculator.scal(vectorY, vectorYBeforeIter, rows);
            lyambda = numerator / denominator;
            if (k > 1) {
                if (abs(lyambda - lymbdaBefore) < eps) {
                    break;
                } else {
                    ++k;
                    normaY = calculator.normaVector(vectorY, rows);
                    for (int i = 0; i < rows; ++i) {
                        vectorY.setElem(i, 0, vectorY.getElem(i, 0) / normaY);
                    }
                    for (int i = 0; i < rows; ++i) {
                        vectorYBeforeIter.setElem(i, 0, vectorY.getElem(i, 0));
                    }
                }
            } else {
                ++k;
                normaY = calculator.normaVector(vectorY, rows);
                for (int i = 0; i < rows; ++i) {
                    vectorY.setElem(i, 0, vectorY.getElem(i, 0) / normaY);
                }
                for (int i = 0; i < rows; ++i) {
                    vectorYBeforeIter.setElem(i, 0, vectorY.getElem(i, 0));
                }
            }
        }
        double sum = 0;
        for (int i = 0; i < rows; ++i) {
            sum += pow(vectorY.getElem(i, 0), 2);
        }
        sum = sqrt(sum);
        for (int i = 0; i < rows; ++i) {
            vectorY.setElem(i, 0, vectorY.getElem(i, 0) / sum);
        }
        System.out.println("Максимальное по модулю собственное число λ матрицы A = " + lyambda);
        System.out.println("Соответствующий ему собственный вектор: ");
        for (int i = 0; i < rows; ++i) {
            System.out.println(vectorY.getElem(i, 0));
        }
        sum = 0;
        for (int i = 0; i < rows; ++i) {
            sum += pow(vectorY.getElem(i, 0), 2);
        }
        sum = sqrt(sum);
        System.out.println("Длина вектора = " + sum);
        System.out.println("");
        System.out.println("Число итераций k = " + k);
        factK[1] = k;
    }

    public double scal(MatrixClass matrixA, MatrixClass matrixB, int rows) {
        double sum = 0;
        for (int i = 0; i < rows; ++i) {
            sum += matrixA.getElem(i, 0) * matrixB.getElem(i, 0);
        }
        return sum;
    }


    public static double normaVector(MatrixClass matrix, int rows) {
        double max = 0;
        for (int i = 0; i < rows; ++i) {
            if (abs(matrix.getElem(i, 0)) > abs(max)) {
                max = matrix.getElem(i, 0);
            }
        }
        return max;
    }
}

