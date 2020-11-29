package ru.KilkaMD.fourthWork;

import java.util.Scanner;

import static java.lang.Math.pow;

public class MainClass {

    public static void clrscr() {
        for (int clear = 0; clear < 10; clear++) {
            System.out.println("\b");
        }
    }

    public static void main(String[] args) {
        MainClass program = new MainClass();
        Scanner input = new Scanner(System.in);
        ProblemClass problem = new ProblemClass();
        System.out.print("Задача: ");
        problem.printFunc();
        int menuItem, k;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Получить таблицу значений решения задачи c шагом h = 0.1 на [0, 1], используя функции математического пакета");
            System.out.println("2. Методом Эйлера получить таблицы решения на [0, 0.5]");
            System.out.println("3. Вычислить решение методом Рунге-Кутты 4-ого порядка с точностью ε = 0.00001 на [0, 1]");
            System.out.println("4. Вычислить решение экстраполяционным методом Адамса 5-ого порядка с шагом из метода Рунге-Кутты на промежутке [5h, 1]");
            System.out.println("5. Вычислить решение интерполяционным методом Адамса 5-ого порядка с шагом h на промежутке [5h, 1]");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            double step;
            double maxTime;
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    System.out.println("1. Получить таблицу значений решения задачи c шагом h = 0.1 на [0, 1], используя функции математического пакета");
                    step = 0.1;
                    maxTime = 1;
                    double[] yMath = problem.firstOrderDifferentialEquationsPackage(step, maxTime);
                    break;
                }
                case 2: {
                    program.clrscr();
                    System.out.println("2. Методом Эйлера получить таблицы решения на [0, 0.5]");
                    step = 0.1;
                    maxTime = 0.5;
                    System.out.println("Таблица метода Эйлера с шагом h = 0.1");
                    double[] yH = problem.eilerMethod(step, maxTime);
                    System.out.println("Таблица метода Эйлера с шагом h = 0.05");
                    double[] yHalfH = problem.eilerMethod(step / 2, maxTime);
                    System.out.println("Таблица метода Эйлера с уточнением решения по Ричардсону");
                    double[] yRich = problem.richardsonMethod(yH, yHalfH, step, maxTime);
                    System.out.println("Таблица разности значений метода Эйлера с уточнением решения по Ричардсону и метода мат пакета");
                    problem.richardsonAndMath(yRich, step, maxTime);
                    break;
                }
                case 3: {
                    program.clrscr();
                    System.out.println("3. Вычислить решение методом Рунге-Кутты 4-ого порядка с точностью ε = 0.00001 на [0, 1]");
                    double eps = 0.00001;
                    maxTime = 1;
                    double[] yRK = problem.rungaKutta(eps, maxTime);
                    break;
                }
                case 4: {
                    program.clrscr();
                    System.out.println("4. Вычислить решение экстраполяционным методом Адамса 5-ого порядка с шагом из метода Рунге-Кутты на промежутке [5h, 1]");
                    double eps = 0.00001;
                    double stepRK = pow(eps, 1.0 / 5.0);
                    maxTime = 1;
                    double[] yAdamsEx = problem.adamsEx(stepRK, maxTime);
                    break;
                }
                case 5: {
                    program.clrscr();
                    double eps = 0.00001;
                    double stepRK = pow(eps, 1.0 / 5.0);
                    maxTime = 1;
                    double[] yAdamsEx = problem.adamsEx(stepRK, maxTime);
                    program.clrscr();
                    System.out.println("5. Вычислить решение интерполяционным методом Адамса 5-ого порядка с шагом h на промежутке [5h, 1]");
                    step = 0.1;
                    double[] yAdamsIn = problem.adamsInt(step, maxTime, yAdamsEx);
                    break;
                }
            }
        } while (menuItem != 0);

    }
}
