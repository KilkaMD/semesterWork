package ru.KilkaMD.fifthWork;

import java.util.Scanner;

/**
 * MainClass - основной класс программы для решения задачи Коши линейных дифференциальных систем. Жесткая задача<br/>
 * Данный класс содержит вызовы следующих подпрограмм:<br/>
 * Подпрограмма для построения на промежутке [0, 0.5] точного решения в точках t_i = i*h, i = 1, 2, . . . , 5, h = 0.1<hr/>
 * Подпрограмма для построения на промежутке [0, 0.5] приближенного решения в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h явным методом Эйлера<hr/>
 * Подпрограмма для построения на промежутке [0, 0.5] приближенного решения в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h неявным методом Эйлера<hr/>
 * Подпрограмма для построения на промежутке [0, 0.5] приближенного решения в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h интерполяционным методом Адамса третьего порядка<hr/>
 * @author Цацорин Лев, Вариант 9
 * @version 1
 */
public class MainClass {

    /**
     * метод clrscr() создает отступы в консоли при выводе, чтобы отделять результаты разных подпрограмм
     */
    public static void clrscr() {
        for (int clear = 0; clear < 10; clear++) {
            System.out.println("\b");
        }
    }

    /**
     * метод main стартовая точка программы<br/>
     * Здесь инициализируется система дифференциальных уравнений 1-го порядка из условия, начальные данные, а также тут находится меню программы
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        MainClass program = new MainClass();
        //rows - размерность матрицы A
        int rows = 2;
        //matrixA - матрица A, создается как экземпляр класса MatrixClass
        MatrixClass matrixA = new MatrixClass();
        //Инициализация размера матрицы A
        matrixA.setSize(rows, rows);

        matrixA.setElem(0, 0, -125.0);
        matrixA.setElem(0, 1, 123.45);
        matrixA.setElem(1, 0, 123.45);
        matrixA.setElem(1, 1, -123.0);

        Scanner input = new Scanner(System.in);
        //Экземпляр класса с подпрограммами
        ProblemClass problem = new ProblemClass();
        System.out.println("Вариант №9");
        System.out.print("Задача: ");
        //Функция вывода на печать уравнения условия
        problem.printFunc();
        System.out.println("Матрица A: ");
        matrixA.printMatrix(rows);
        int menuItem;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Построить на промежутке [0, 0.5] точное решение в точках t_i = i*h, i = 1, 2, . . . , 5, h = 0.1.");
            System.out.println("2. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h явным методом Эйлера");
            System.out.println("3. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h неявным методом Эйлера");
            System.out.println("4. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h интерполяционным методом Адамса третьего порядка");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            double step;
            double maxTime;
            step = 0.1;
            maxTime = 0.5;
            double[] y1 = problem.firstOrderDifferentialEquationsPackage(step, maxTime, 0);
            double[] y2 = problem.firstOrderDifferentialEquationsPackage(step, maxTime, 1);
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    System.out.println("1. Построить на промежутке [0, 0.5] точное решение в точках t_i = i*h, i = 1, 2, . . . , 5, h = 0.1.");
                    problem.printTableExactSolution(y1, y2, step, maxTime);
                    double[] eigen = matrixA.eigen();
                    break;
                }
                case 2: {
                    program.clrscr();
                    System.out.println("2. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h явным методом Эйлера");
                    System.out.println("Выберите величину шага h");
                    System.out.println("1) h = 0.05");
                    System.out.println("2) h = 0.001");
                    System.out.print("Ввод: ");
                    int choice = input.nextInt();
                    if (choice == 1) step = 0.05;
                    else step = 0.001;
                    maxTime = 0.5;
                    MatrixClass matrixW = new MatrixClass();
                    matrixW.setSize(rows, rows);
                    matrixW.foundWEuler(matrixA, step);
                    double[][] yEuler = problem.eulerMethod(step, maxTime, matrixW);
                    problem.printTable(yEuler, step, y1, y2);
                    double[] eigen = matrixW.eigen();
                    problem.checkEigen(eigen);
                    break;
                }
                case 3: {
                    program.clrscr();
                    System.out.println("3. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h неявным методом Эйлера");
                    System.out.println("Выберите величину шага h");
                    System.out.println("1) h = 0.05");
                    System.out.println("2) h = 0.001");
                    System.out.print("Ввод: ");
                    int choice = input.nextInt();
                    if (choice == 1) {
                        step = 0.05;
                    } else step = 0.001;
                    maxTime = 0.5;
                    MatrixClass matrixW = new MatrixClass();
                    matrixW.setSize(rows, rows);
                    matrixW.foundWEulerBack(matrixA, step);
                    double[][] yEulerBack = problem.eulerMethod(step, maxTime, matrixW);
                    problem.printTable(yEulerBack, step, y1, y2);
                    double[] eigen = matrixW.eigen();
                    problem.checkEigen(eigen);
                    break;
                }
                case 4: {
                    program.clrscr();
                    System.out.println("4. Построить на промежутке [0, 0.5] приближенное решение в точках t_i = i*h, i = 1, 2, . . . , 5 с шагом h интерполяционным методом Адамса третьего порядка");
                    System.out.println("Выберите величину шага h");
                    System.out.println("1) h = 0.05");
                    System.out.println("2) h = 0.001");
                    System.out.print("Ввод: ");
                    int choice = input.nextInt();
                    if (choice == 1) {
                        step = 0.05;
                    } else step = 0.001;
                    maxTime = 0.5;
                    MatrixClass matrixW1 = new MatrixClass();
                    MatrixClass matrixW2 = new MatrixClass();
                    MatrixClass matrixW = new MatrixClass();
                    matrixW.setSize(rows, rows);
                    matrixW1.setSize(rows, rows);
                    matrixW2.setSize(rows, rows);
                    matrixW.foundWEulerBack(matrixA, step);
                    matrixW1.foundW1(matrixA, step);
                    matrixW2.foundW2(matrixA, step);
                    double[][] yAdams = problem.adamsMethod(step, maxTime, matrixW, matrixW1, matrixW2);
                    problem.printTable(yAdams, step, y1, y2);
                    double[] eigenW1 = matrixW1.eigenWi();
                    double[] eigenW2 = matrixW2.eigenWi();
                    problem.characteristicEquations(eigenW1, eigenW2);
                    break;
                }
            }
        } while (menuItem != 0);
    }
}
