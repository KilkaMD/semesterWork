package ru.KilkaMD.fourthWork;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * ProblemClass - класс с реализацией основных функций программы для решения задачи Коши для обыкновенного дифференциального уравнения 1-го порядка
 */
public class ProblemClass {

    /**
     * Метод для печати дифференциального уравнения 1-го порядка из условия задачи
     */
    public void printFunc() {
        System.out.println("y' = 1 + (1.25 - x) * sin(y) - (1.75 + x) * y");
    }

    /**
     * Метод представляющий собой правую часть(y' = f(x,y)) дифференциального уравнения 1-го порядка из условия задачи
     * @param y значение y компоненты функции
     * @param step значение x компоненты функции
     * @return f(x,y)
     */
    public double funcComputation(double y, double step) {
        return (1 + (1.25 - step) * sin(y) - (1.75 + step) * y);
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
     * Метод для решения задачи Коши с помощью функций математического пакета, а также печати таблицы значений решения
     * @param step шаг задачи
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага метода
     */
    public double[] firstOrderDifferentialEquationsPackage(double step, double maxTime) {
        List<Double> yList = new ArrayList<Double>();
        FirstOrderIntegrator firstOrderIntegrator = new LutherIntegrator(step);
        FirstOrderDifferentialEquations odes = new PackageOde();
        double[] y = ((PackageOde) odes).getInitialConditions();
        double t = 0.0;
        int i = 0;
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, y[0]));
            yList.add(y[0]);
            firstOrderIntegrator.integrate(odes, t, y, t + step, y);
            t += step;
            ++i;
        }
        double yRet[] = converListToDouble(yList);
        return yRet;
    }

    /**
     * Метод Эйлера для решения задачи Коши, а также печати таблицы значений решения
     * @param step шаг задачи
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага метода
     */
    public double[] eilerMethod(double step, double maxTime) {
        List<Double> y = new ArrayList<Double>();
        y.add(0.0);
        double t = 0.0;
        int k = 0;
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f", k, t, y.get(k)));
            if (t == maxTime) {
                break;
            }
            y.add(y.get(k) + step * funcComputation(y.get(k), t));
            t += step;
            ++k;
        }
        double yRet[] = converListToDouble(y);
        return yRet;
    }

    /**
     * Метод для уточнения решения по Ричардсону, а также печати таблицы значений решения
     * @param yH массив значений метода Эйлера с шагом h
     * @param yHalfH массив значений метода Эйлера с шагом h/2
     * @param step шаг задачи
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага метода
     */
    public double[] richardsonMethod(double[] yH, double[] yHalfH, double step, double maxTime) {
        List<Double> y = new ArrayList<Double>();
        double t = 0.0;
        int k = 0;
        double R;
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        while (t <= maxTime) {
            R = (yHalfH[2 * k] - yH[k]) / 3;
            y.add(yHalfH[2 * k] + R);
            System.out.println(String.format("%4d %8.4f %10.6f", k, t, y.get(k)));
            t += step;
            ++k;
        }
        double yRet[] = converListToDouble(y);
        return yRet;
    }

    /**
     * Метод для получений вектора решения задачи Коши с помощью функций математического пакета, без печати его на экран
     * @param zeroStep начало промежутка, где задача имеет единственное решение
     * @param step шаг метода
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага
     */
    public double[] mathY(double zeroStep, double step, double maxTime) {
        List<Double> yList = new ArrayList<Double>();
        FirstOrderIntegrator firstOrderIntegrator = new LutherIntegrator(step);
        FirstOrderDifferentialEquations odes = new PackageOde();
        double[] y = ((PackageOde) odes).getInitialConditions();
        double t = zeroStep;
        while (t <= maxTime) {
            yList.add(y[0]);
            firstOrderIntegrator.integrate(odes, t, y, t + step, y);
            t += step;
        }
        double yMath[] = converListToDouble(yList);
        return yMath;
    }

    /**
     * Метод для печати таблицы y_rev − y_math
     * @param yRich массив значений каждого шага, полученный уточненением решения по Ричардсону
     * @param step шаг метода
     * @param maxTime конец промежутка, где задача имеет единственное решение
     */
    public void richardsonAndMath(double[] yRich, double step, double maxTime) {
        double t = 0.0;
        double yMath[] = mathY(t, step, maxTime);

        System.out.println(String.format("%4s %5s %32s", "i", "x_i", "|y(x_i)_rich - y(x_i)_math|"));
        for (int i = 0; i < yRich.length; ++i) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, abs(yMath[i] - yRich[i])));
            t += step;
        }
    }

    /**
     * Метод Рунге-Кутты 4-ого порядка для решения задачи Коши, а также печати таблицы значений решения и таблицы y_math − y_RK
     * @param eps точность решения
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага метода
     */
    public double[] rungaKutta(double eps, double maxTime) {
        List<Double> yList = new ArrayList<Double>();
        yList.add(0.0);
        double t = 0.0;
        double k1, k2, k3, k4;
        int i = 0;
        double step = pow(eps, 1.0 / 5.0);
        double yMath[] = mathY(t, step, maxTime);
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, yList.get(i)));
            if (t + step >= maxTime) {
                break;
            }
            k1 = step * funcComputation(yList.get(i), t);
            k2 = step * funcComputation(yList.get(i) + k1 / 2, t + step / 2);
            k3 = step * funcComputation(yList.get(i) + k2 / 2, t + step / 2);
            k4 = step * funcComputation(yList.get(i) + k3, t + step);
            yList.add(yList.get(i) + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4));
            t += step;
            i++;
        }
        double yRet[] = converListToDouble(yList);
        System.out.println("");
        System.out.println(String.format("%4s %5s %30s", "i", "x_i", "|y(x_i)_RK - y(x_i)_math|"));
        t = 0.0;
        for (int k = 0; k < yRet.length; ++k) {
            System.out.println(String.format("%4d %8.4f %10.6f", k, t, abs(yMath[k] - yRet[k])));
            t += step;
        }
        return yRet;
    }

    /**
     * Экстраполяционный метод Адамса 5-ого порядка для решения задачи Коши, а также печати таблицы значений решения и таблицы y_math − y_Ad_ex
     * @param stepRK шаг метода Рунге-Кутты 4-ого порядка
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @return массив значений каждого шага метода
     */
    public double[] adamsEx(double stepRK, double maxTime) {
        List<Double> yList = new ArrayList<Double>();
        yList.add(0.0);
        double t = 0.0;
        double k1, k2, k3, k4;
        double yMath[] = mathY(t, stepRK, maxTime);
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        int k = 0;
        while (t < 4 * stepRK) {
            System.out.println(String.format("%4d %8.4f %10.6f", k, t, yList.get(k)));
            k1 = stepRK * funcComputation(yList.get(k), t);
            k2 = stepRK * funcComputation(yList.get(k) + k1 / 2, t + stepRK / 2);
            k3 = stepRK * funcComputation(yList.get(k) + k2 / 2, t + stepRK / 2);
            k4 = stepRK * funcComputation(yList.get(k) + k3, t + stepRK);
            yList.add(yList.get(k) + (1.0 / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4));
            t += stepRK;
            ++k;
        }
        int i = 4;
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, yList.get(i)));
            if (t + stepRK >= maxTime) {
                break;
            }
            yList.add(yList.get(i) + (1.0 / 720.0) * (1901 * stepRK * funcComputation(yList.get(i), t) - 2774 * stepRK * funcComputation(yList.get(i - 1), t - stepRK)
                    + 2616 * stepRK * funcComputation(yList.get(i - 2), t - 2 * stepRK) - 1274 * stepRK * funcComputation(yList.get(i - 3), t - 3 * stepRK) + 251 * stepRK * funcComputation(yList.get(i - 4), t - 4 * stepRK)));
            t += stepRK;
            i++;
        }
        double yRet[] = converListToDouble(yList);
        System.out.println("");
        System.out.println(String.format("%4s %5s %35s", "i", "x_i", "|y(x_i)_adamsEx - y(x_i)_math|"));
        t = 0.0;
        for (int j = 0; j < yRet.length; ++j) {
            System.out.println(String.format("%4d %8.4f %10.6f", j, t, abs(yMath[j] - yRet[j])));
            t += stepRK;
        }
        return yRet;
    }

    /**
     * Метод для итерационного нахождения (y_m+1)^(k+1) с заданной точностью
     * @param step шаг метода
     * @param adamsExIter (y_m+1)^(0) - начальное приближение
     * @param t m - порядковый номер шага метода
     * @param yM y_m
     * @param yM1 y_m-1
     * @param yM2 y_m-2
     * @param yM3 y_m-3
     * @return (y_m+1)^(k+1) с заданной точностью
     */
    public double foundIterY(double step, double adamsExIter, double t, double yM, double yM1, double yM2, double yM3) {
        double iter = yM + (1.0 / 720.0) * (251 * step * funcComputation(adamsExIter, t + step) + 646 * step * funcComputation(yM, t)
                - 264 * step * funcComputation(yM1, t - step) + 106 * step * funcComputation(yM2, t - 2 * step) - 19 * step * funcComputation(yM3, t - 3 * step));
        while (abs(iter - adamsExIter) > 0.00001) {
            adamsExIter = iter;
            iter = yM + (1.0 / 720.0) * (251 * step * funcComputation(adamsExIter, t + step) + 646 * step * funcComputation(yM, t)
                    - 264 * step * funcComputation(yM1, t - step) + 106 * step * funcComputation(yM2, t - 2 * step) - 19 * step * funcComputation(yM3, t - 3 * step));
        }
        return iter;
    }

    /**
     * Интерполяционный метод Адамса 5-ого порядка для решения задачи Коши, а также печати таблицы значений решения и таблицы y_math − y_Ad_in
     * @param step шаг метода
     * @param maxTime конец промежутка, где задача имеет единственное решение
     * @param yAdamsEx массив значений каждого шага экстраполяционного метода Адамса 5-ого порядка
     * @return массив значений каждого шага метода
     */
    public double[] adamsInt(double step, double maxTime, double[] yAdamsEx) {
        List<Double> yList = new ArrayList<Double>();
        System.out.println(String.format("%4s %5s %11s", "i", "x_i", "y(x_i)"));
        yList.add(yAdamsEx[0]);
        yList.add(yAdamsEx[1]);
        yList.add(yAdamsEx[2]);
        yList.add(yAdamsEx[3]);
        yList.add(yAdamsEx[4]);
        int i = 0;
        double t = 0.0, iter, adamsExIter;
        double yMath[] = mathY(t, step, maxTime);
        while (t <= maxTime) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, yList.get(i)));
            if (i > 3) {
                if (t + step >= maxTime) {
                    break;
                }
                adamsExIter = yAdamsEx[i + 1];
                iter = foundIterY(step, adamsExIter, t, yList.get(i), yList.get(i - 1), yList.get(i - 2), yList.get(i - 3));
                yList.add(iter);
            }
            t += step;
            i++;
        }
        double yRet[] = converListToDouble(yList);
        System.out.println("");
        System.out.println(String.format("%4s %5s %35s", "i", "x_i", "|y(x_i)_adamsIn - y(x_i)_math|"));
        t = 0.0;
        for (int j = 0; j < yRet.length; ++j) {
            System.out.println(String.format("%4d %8.4f %10.6f", j, t, abs(yMath[j] - yRet[j])));
            t += step;
        }
        return yRet;
    }

}
