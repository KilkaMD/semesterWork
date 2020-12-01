package ru.KilkaMD.sixthWork;

import static java.lang.Math.*;

/**
 * ProblemClass - класс с реализацией основных функций программы для решения краевой задачи для обыкновенного дифференциального уравнения второго порядка
 */
public class ProblemClass {

    /**
     * Метод для печати обыкновенного дифференциального уравнения второго порядка из условия задачи
     */
    public void printFunc() {
        System.out.println("-((4-x)/(5-2x))*u\" + ((1-x)/2)*u' + (1/2)*ln(3+x)*u = 1 + x/3");
    }

    /**
     * Метод для вычисления значения функции p_i = p(x_i)
     * @param x значение x_i
     * @return p(x_i)
     */
    public double pFunc(double x) {
        return ((4 - x) / (5 - 2 * x));
    }

    /**
     * Метод для вычисления значения функции q_i = q(x_i)
     * @param x значение x_i
     * @return q(x_i)
     */
    public double qFunc(double x) {
        return ((1 - x) / 2.0);
    }

    /**
     * Метод для вычисления значения функции r_i = r(x_i)
     * @param x значение x_i
     * @return r(x_i)
     */
    public double rFunc(double x) {
        return ((1.0 / 2.0) * log(3 + x));
    }

    /**
     * Метод для вычисления значения функции f_i = f(x_i)
     * @param x значение x_i
     * @return f(x_i)
     */
    public double fFunc(double x) {
        return (1.0 + x / 3.0);
    }

    /**
     * Метод для инициализации "точного" решения решения краевой задачи, полученного с помощью встроенных функций MATLAB
     * @return массив "точного" решения Yex
     */
    public double[] initExactSolution() {
        double[] yRet = new double[21];
        yRet[0] = 0.0;
        yRet[1] = 0.0596;
        yRet[2] = 0.1176;
        yRet[3] = 0.1731;
        yRet[4] = 0.2255;
        yRet[5] = 0.2738;
        yRet[6] = 0.3173;
        yRet[7] = 0.3552;
        yRet[8] = 0.3866;
        yRet[9] = 0.4107;
        yRet[10] = 0.4267;
        yRet[11] = 0.4339;
        yRet[12] = 0.4315;
        yRet[13] = 0.4189;
        yRet[14] = 0.3954;
        yRet[15] = 0.3605;
        yRet[16] = 0.3138;
        yRet[17] = 0.2546;
        yRet[18] = 0.1828;
        yRet[19] = 0.0980;
        yRet[20] = 0.0;
        return yRet;
    }

    /**
     * Метод для печати на экран таблицы "точного" решения решения краевой задачи
     * @param yRet массив "точного" решения
     * @param step шаг задачи
     * @param startT начало промежутка решения задачи
     * @param endT конец промежутка решения задачи
     */
    public void printTableExactSolution(double[] yRet, double step, double startT, double endT) {
        System.out.println(String.format("%4s %5s %13s", "i", "x_i", "y(x_i)_ex"));
        double t = startT;
        int i = 0;
        while (t <= endT) {
            System.out.println(String.format("%4d %8.4f %10.6f", i, t, yRet[i]));
            t += step;
            i++;
            if (i == 10) t = abs(t);
        }
    }

    /**
     * Метод для нахождения массивов коэффициентов A, B, C, G
     * @param step величина шага h
     * @param size количество разбиений промежутка на равные части
     * @param startT начало промежутка решения задачи
     * @return матрица, где каждый столбец это массив коэффициентов A, B, C, G соответственно
     */
    public double[][] vectorABCG(double step, int size, double startT) {
        double[][] ABCG = new double[size + 1][4];
        double t = startT + step;
        ABCG[0][0] = 0.0;
        ABCG[0][1] = -1.0;
        ABCG[0][2] = 0.0;
        ABCG[0][3] = 0.0;
        ABCG[size][0] = 0.0;
        ABCG[size][1] = -1.0;
        ABCG[size][2] = 0.0;
        ABCG[size][3] = 0.0;
        for (int i = 1; i < size; ++i) {
            ABCG[i][0] = -(pFunc(t) / pow(step, 2)) - (qFunc(t) / (2 * step));
            ABCG[i][1] = -2 * (pFunc(t) / pow(step, 2)) - rFunc(t);
            ABCG[i][2] = -(pFunc(t) / pow(step, 2)) + (qFunc(t) / (2 * step));
            ABCG[i][3] = fFunc(t);
            t += step;
        }
        return ABCG;
    }

    /**
     * Метод для нахождения массивов прогоночных коэффициентов s_i, t_i
     * @param size количество разбиений промежутка на равные части
     * @param ABCG матрица, где каждый столбец это массив коэффициентов A, B, C, G соответственно
     * @return матрица, где каждый столбец это массив прогоночных коэффициентов s_i и t_i соответственно
     */
    public double[][] vectorSV(int size, double[][] ABCG) {
        double[][] SV = new double[size + 1][2];
        SV[0][0] = 0;
        SV[0][1] = 0;
        for (int i = 1; i < size + 1; ++i) {
            SV[i][0] = (ABCG[i][2]) / (ABCG[i][1] - ABCG[i][0] * SV[i - 1][0]);
            SV[i][1] = (ABCG[i][0] * SV[i - 1][1] - ABCG[i][3]) / (ABCG[i][1] - ABCG[i][0] * SV[i - 1][0]);
        }
        SV[size][0] = abs(SV[size][0]);SV[size][1] = abs(SV[size][1]);
        return SV;
    }

    /**
     * Метод для нахождения массива Y решения в точках сетки
     * @param size количество разбиений промежутка на равные части
     * @param SV матрица, где каждый столбец это массив прогоночных коэффициентов s_i и t_i соответственно
     * @return массив Y решения задачи в точках сетки
     */
    public double[] vectorY(int size, double[][] SV) {
        double [] Y = new double[size+1];
        Y[0] = 0;
        Y[size] = abs(SV[size][1]);
        for(int i = size-1; i > 0; --i) {
            Y[i] = SV[i][0]*Y[i+1] + SV[i][1];
        }
        return Y;
    }

    /**
     * Метод для печати на экран Таблицы 1
     * @param step величина шага h
     * @param size количество разбиений промежутка на равные части
     * @param startT начало промежутка решения задачи
     * @param ABCG матрица, где каждый столбец это массив коэффициентов A, B, C, G соответственно
     * @param SV матрица, где каждый столбец это массив прогоночных коэффициентов s_i и t_i соответственно
     * @param Y массив решения задачи в точках сетки
     */
    public void printTable1(double step, int size, double startT, double[][] ABCG, double[][] SV, double[] Y) {
        System.out.println(String.format("%4s %5s %9s %12s %12s %12s %13s %12s %12s", "i", "x_i", "Ai", "Bi", "Ci", "Gi", "s_i", "t_i", "y_i"));
        double t = startT;
        for(int i = 0; i < size + 1; ++i) {
            System.out.println(String.format("%4d %8.4f %12.6f %12.6f %12.6f %12.6f %12.6f %12.6f %12.6f", i, t, ABCG[i][0], ABCG[i][1], ABCG[i][2], ABCG[i][3], SV[i][0], SV[i][1], Y[i]));
            t += step;
            if(i == 4) t = abs(t);
        }
    }

    /**
     * Метод для нахождения массива Yut решения в точках основной сетки с уточнением по Ричардсону
     * @param size количество разбиений промежутка на равные части
     * @param Yn массив решения задачи с n = 20
     * @param YnHalf массив решения задачи с n = 10
     * @return массив решения в точках основной сетки с уточнением по Ричардсону
     */
    public double[] vectorYut(int size, double[] Yn, double[] YnHalf) {
        double[] Yut = new double[size+1];
        double R;
        for(int i = 0; i < size+1; ++i) {
            R = (Yn[2*i]-YnHalf[i])/3;
            Yut[i] = Yn[2*i] + R;
        }
        return Yut;
    }

    /**
     * Метод для печати на экран Таблицы 2
     * @param step величина шага h
     * @param size количество разбиений промежутка на равные части
     * @param startT начало промежутка решения задачи
     * @param Yut массив решения в точках основной сетки с уточнением по Ричардсону
     * @param Yex массив "точного" решения задачи
     */
    public void printTable2(double step, int size, double startT, double[] Yut, double[] Yex) {
        System.out.println(String.format("%4s %5s %10s %12s %20s", "i", "x_i", "Yex", "Yut", "|Yut - Yex|"));
        double t = startT;
        for(int i = 0; i < size + 1; ++i) {
            System.out.println(String.format("%4d %8.4f %12.6f %12.6f %12.6f", i, t, Yex[2*i], Yut[i], abs(Yex[2*i]-Yut[i])));
            t += step;
            if(i == 4) t = abs(t);
        }
    }
}
