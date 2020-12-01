package ru.KilkaMD.fifthWork;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * ProblemClass - класс с реализацией основных функций программы для решения задачи Коши линейных дифференциальных систем
 */
public class ProblemClass {

    /**
     * Метод для печати дифференциальной системы уравнений 1-го порядка из условия задачи
     */
    public void printFunc() {
        System.out.println("y'_1 = -125*y_1 + 123.45*y_2");
        System.out.println("y'_2 = 123.45*y_1 - 123*y_2");
    }

    /**
     * Метод представляющий собой правую часть(y_1' = f(y_1,y_2)) дифференциального уравнения 1-го порядка системы из условия задачи
     * @param y вектор Y = (y_1, y_2)
     * @return f(y_1,y_2)
     */
    public double funcComputationFirst(double[] y) {
        return (-125 * y[0] + 123.45 * y[1]);
    }

    /**
     * Метод представляющий собой правую часть(y_2' = f(y_1,y_2)) дифференциального уравнения 1-го порядка системы из условия задачи
     * @param y вектор Y = (y_1, y_2)
     * @return f(y_1,y_2)
     */
    public double funcComputationSecond(double[] y) {
        return (123.45 * y[0] - 123 * y[1]);
    }

    /**
     * Метод вычисляющий точное значение y_1 во время t задачи Коши дифференциальной системы уравнений 1-го порядка из условия задачи
     * @param t шаг метода
     * @return y_1(t)
     */
    public double funcExactSolutionFirst(double t) {
        return (exp(-248.00 * t) * (0.5041 * exp(0.54595 * t) + 0.4959 * exp(247.45 * t) + 0.5000 * exp(247.45 * t) - 0.5000 * exp(0.54595 * t)));
    }

    /**
     * Метод вычисляющий точное значение y_2 во время t задачи Коши дифференциальной системы уравнений 1-го порядка из условия задачи
     * @param t шаг метода
     * @return y_2(t)
     */
    public double funcExactSolutionSecond(double t) {
        return (exp(-248.00 * t) * (0.5041 * exp(247.45 * t) + 0.4959 * exp(0.54595 * t) + 0.5000 * exp(247.45 * t) - 0.5000 * exp(0.54595 * t)));
    }

    /**
     * Метод для проверки устойчивости приближенных вычислений
     * @param eigen вектор собственных чисел
     */
    public void checkEigen(double[] eigen) {
        if ((abs(eigen[0]) >= 1) || (abs(eigen[1]) >= 1)) {
            System.out.println("Метод не устойчив");
        } else {
            System.out.println("Метод устойчив");
        }
    }

    /**
     * Метод для приведения структуры списка к обыкновенному массиву - вектору значений каждого метода решения задачи Коши
     * @param y список значений, полученных одним из методов решения задачи Коши
     * @return их представление в виде массива
     */
    private double[] converListToDouble(List<Double> y) {
        Double[] y_step = new Double[y.size()];
        y_step = y.toArray(y_step);
        double y_ret[] = new double[y_step.length];
        int i = 0;
        for (Double d : y_step) {
            y_ret[i] = (double) d;
            i++;
        }
        return y_ret;
    }

    /**
     * Метод для нахождения точного решения задачи Коши для дифференциальной системы уравнений 1-го порядка
     * @param step шаг задачи
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @param numEq индекс при y_(i+1) (i = 0, 1)
     * @return массив значений точного решения для y_(i+1)
     */
    public double[] firstOrderDifferentialEquationsPackage(double step, double maxTime, int numEq) {
        List<Double> yList = new ArrayList<Double>();
        FirstOrderDifferentialEquations odes = new PackageOde();
        double[] y = ((PackageOde) odes).getInitialConditions();
        double t = 0.0;
        while (t <= maxTime) {
            yList.add(y[numEq]);
            if (numEq == 0) {
                y[numEq] = funcExactSolutionFirst(t + step);
            } else {
                y[numEq] = funcExactSolutionSecond(t + step);
            }
            t += step;
        }
        double yRet[] = converListToDouble(yList);
        return yRet;
    }

    /**
     * Метод для вывода на печать таблицы точного решения задачи Коши для дифференциальной системы уравнений 1-го порядка
     * @param y1 вектор значений y_1
     * @param y2 вектор значений y_2
     * @param step шаг задачи
     * @param maxTime конец промежутка, где задача имеет единственное решение
     */
    public void printTableExactSolution(double[] y1, double[] y2, double step, double maxTime) {
        System.out.println(String.format("%4s %5s %13s %10s", "i", "x_i", "y_1(x_i)", "y_2(x_i)"));
        double t = 0.0;
        int i = 0;
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f %10.6f", i, t, y1[i], y2[i]));
            t += step;
            ++i;
        }
    }

    /**
     * Метод для вывода на печать таблицы приближенного решения задачи Коши для дифференциальной системы уравнений 1-го порядка в сравнении с точным решением
     * @param y матрица значений, полученных при решении задачи Коши приближенными методами
     * @param step шаг задачи
     * @param y1 вектор значений y_1 точного решения задачи Коши
     * @param y2 вектор значений y_2 точного решения задачи Коши
     */
    public void printTable(double[][] y, double step, double[] y1, double[] y2) {
        System.out.println(String.format("%4s %5s %27s %32s", "i", "x_i", "|y_1_exact - y_1(x_i)|", "|y_2_exact - y_2(x_i)|"));
        double t = 0.0;
        int i = 0, j = 0;
        while (i < y.length) {
            if (i % ((y.length - 1) / 5) == 0) {
                System.out.println(String.format("%4d %8.4f %18.6f %32.6f", j, t, abs(y[i][0] - y1[j]), abs(y[i][1] - y2[j])));
                ++j;
            }
            t += step;
            ++i;
        }
    }

    /**
     * Метод нахождения решения задачи Коши для дифференциальной системы уравнений 1-го порядка метод Эйлера и обратный метод Эйлера
     * @param step шаг метода
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @param matrixW матрица W метода
     * @return матрица значений, полученных при решении задачи Коши приближенными методами
     */
    public double[][] eulerMethod(double step, double maxTime, MatrixClass matrixW) {
        List<Double> yList1 = new ArrayList<Double>();
        List<Double> yList2 = new ArrayList<Double>();
        yList1.add(1.0);
        yList2.add(1.0);
        double t = 0.0;
        int i = 0;
        while (t <= maxTime) {
            if (t == maxTime) break;
            yList1.add(matrixW.getElem(0, 0) * yList1.get(i) + matrixW.getElem(0, 1) * yList2.get(i));
            yList2.add(matrixW.getElem(1, 0) * yList1.get(i) + matrixW.getElem(1, 1) * yList2.get(i));
            t += step;
            i++;
        }
        double yRet1[] = converListToDouble(yList1);
        double yRet2[] = converListToDouble(yList2);
        double[][] yRet = new double[yRet1.length][yRet2.length];
        for (int j = 0; j < yRet1.length; ++j) {
            yRet[j][0] = yRet1[j];
            yRet[j][1] = yRet2[j];
        }
        return yRet;
    }

    /**
     * Метод нахождения решения задачи Коши для дифференциальной системы уравнений 1-го порядка интерполяционным методом Адамса третьего порядка
     * @param step шаг метода
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @param matrixW матрица W обратного метода Эйлера
     * @param matrixW1 матрица W1 метода Адамса
     * @param matrixW2 матрица W2 метода Адамса
     * @return матрица значений, полученных при решении задачи Коши приближенными методами
     */
    public double[][] adamsMethod(double step, double maxTime, MatrixClass matrixW, MatrixClass matrixW1, MatrixClass matrixW2) {
        List<Double> yList1 = new ArrayList<Double>();
        List<Double> yList2 = new ArrayList<Double>();
        yList1.add(1.0);
        yList2.add(1.0);
        double[][] yEulerBack = eulerMethod(step, maxTime, matrixW);
        yList1.add(yEulerBack[1][0]);
        yList2.add(yEulerBack[1][1]);
        double t = step;
        int i = 1;
        while (t <= maxTime) {
            if (t == maxTime) break;
            yList1.add(matrixW1.getElem(0, 0) * yList1.get(i) + matrixW1.getElem(0, 1) * yList2.get(i) - (
                    matrixW2.getElem(0, 0) * yList1.get(i - 1) + matrixW2.getElem(0, 1) * yList2.get(i - 1)
            ));
            yList2.add(matrixW1.getElem(1, 0) * yList1.get(i) + matrixW1.getElem(1, 1) * yList2.get(i) - (
                    matrixW2.getElem(1, 0) * yList1.get(i - 1) + matrixW2.getElem(1, 1) * yList2.get(i - 1)
            ));
            t += step;
            i++;
        }
        double yRet1[] = converListToDouble(yList1);
        double yRet2[] = converListToDouble(yList2);
        double[][] yRet = new double[yRet1.length][yRet2.length];
        for (int j = 0; j < yRet1.length; ++j) {
            yRet[j][0] = yRet1[j];
            yRet[j][1] = yRet2[j];
        }
        return yRet;
    }

    /**
     * Метод для вычисления корней характеристических уравнений метода Адамса третьего порядка и определения его устойчивости
     * @param eigenW1 вектор собственных чисел матрицы W1
     * @param eigenW2 вектор собственных чисел матрицы W2
     */
    public void characteristicEquations(double[] eigenW1, double[] eigenW2) {
        double x1, x2, y1, y2;
        System.out.println("Корни первого характеристического уравнения:");
        x1 = (eigenW1[0] + sqrt(pow(eigenW1[0], 2) - 4 * eigenW2[0])) / 2.0;
        x2 = (eigenW1[0] - sqrt(pow(eigenW1[0], 2) - 4 * eigenW2[0])) / 2.0;
        System.out.println("x1 = "+x1);
        System.out.println("x2 = "+x2);
        System.out.println("Корни второго характеристического уравнения:");
        y1 = (eigenW1[1] + sqrt(pow(eigenW1[1], 2) - 4 * eigenW2[1])) / 2.0;
        y2 = (eigenW1[1] - sqrt(pow(eigenW1[1], 2) - 4 * eigenW2[1])) / 2.0;
        System.out.println("x1 = "+y1);
        System.out.println("x2 = "+y2);
        if ((abs(x1) >= 1) || (abs(x2) >= 1) || (abs(y1) >= 1) || (abs(y2) >= 1)) {
            System.out.println("Метод не устойчив");
        } else {
            System.out.println("Метод устойчив");
        }
    }
}
