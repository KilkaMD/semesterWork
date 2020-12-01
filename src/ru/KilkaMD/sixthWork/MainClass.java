package ru.KilkaMD.sixthWork;

import java.util.Scanner;

/**
 * MainClass - основной класс программы для решения краевой задачи для обыкновенного дифференциального уравнения второго порядка. Метод прогонки<br/>
 * Данный класс содержит вызовы следующих подпрограмм:<br/>
 * Подпрограмма для получения “точного” решения задачи в узлах основной сетки с помощью встроенных функций MATLAB<hr/>
 * Подпрограмма для получения решения с O(h^2) при n = 10 и оформления в виде таблицы<hr/>
 * Подпрограмма для получения решения O(h^2) при n = 20, уточнения его по Ричардсону и сравнения с “точным” решением<hr/>
 * @author Цацорин Лев, Вариант 8
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
     * Здесь инициализируется дифференциальное уравнение второго порядка из условия, краевые условия, а также тут находится меню программы
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        MainClass program = new MainClass();
        Scanner input = new Scanner(System.in);
        //Экземпляр класса с подпрограммами
        ProblemClass problem = new ProblemClass();
        System.out.println("Вариант №8");
        System.out.println("B обоих граничных условиях нет слагаемых с производными, т. е. |α2| + |β2| = 0, поэтому граничные уравнения при использовании основной\n" +
                "сетки имеют вид y0 = α, yn = β. Соответственно, в этом случае п. 3) и п. 4) задания опускаются, а п. 5) и п. 6) рациональнее реализовать\n" +
                "на основной сетке.");
        System.out.print("Задача: ");
        //Функция вывода на печать уравнения условия
        problem.printFunc();
        double[] yEx = problem.initExactSolution();
        //Начало промежутка решения задачи
        double startT = -1.0;
        //Конец промежутка решения задачи
        double endT = 1.0;
        double step;
        int menuItem;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Получение “точного” решения задачи в узлах основной сетки с помощью встроенных функций MATLAB");
            System.out.println("2. Получить решение с O(h^2) при n = 10");
            System.out.println("3. Получить решение O(h^2) при n = 20, уточнить его по Ричардсону и сравнить с “точным” решением");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    System.out.println("1. Получение “точного” решения задачи в узлах основной сетки с помощью встроенных функций MATLAB");
                    step = (endT - startT) / 20;
                    problem.printTableExactSolution(yEx, step, startT, endT);
                    break;
                }
                case 2: {
                    program.clrscr();
                    System.out.println("2. Получить решение с O(h^2) при n = 10");
                    step = (endT - startT) / 10;
                    double[][] ABCG = problem.vectorABCG(step, 10, startT);
                    double[][] SV = problem.vectorSV(10, ABCG);
                    double[] Y = problem.vectorY(10, SV);
                    problem.printTable1(step, 10, startT, ABCG, SV, Y);
                    break;
                }
                case 3: {
                    program.clrscr();
                    System.out.println("3. Получить решение O(h^2) при n = 20, уточнить его по Ричардсону и сравнить с “точным” решением");
                    step = (endT - startT) / 20;
                    double[][] ABCGn = problem.vectorABCG(step, 20, startT);
                    double[][] ABCGnHalf = problem.vectorABCG(step*2, 10, startT);
                    double[][] SVn = problem.vectorSV(20, ABCGn);
                    double[][] SVnHalf = problem.vectorSV(10, ABCGnHalf);
                    double[] Yn = problem.vectorY(20, SVn);
                    double[] YnHalf = problem.vectorY(10, SVnHalf);
                    double[] Yut = problem.vectorYut(10, Yn, YnHalf);
                    problem.printTable2(step*2, 10, startT, Yut, yEx);
                    break;
                }
            }
        } while (menuItem != 0);
    }
}
