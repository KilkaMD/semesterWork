package ru.KilkaMD.thirdWork;

import java.util.Scanner;

/**
 * MainClass - основной класс программы для решения полной проблемы собственных значений методом Якоби, а также частичной проблемы с. значений (степенной, скалярных пр-ий)<br/>
 * Данный класс содержит вызовы следующих подпрограмм:<br/>
 * Подпрограмма для нахождения методом Якоби всех собственных чисел и собственных векторов матрицы с заданной точностью ε = 0.000001<hr/>
 * Подпрограмма для нахождения степенным методом c точностью ε = 0.001 максимального по модулю собственного числа λ матрицы A и соответствующего ему собственного вектора x<hr/>
 * Подпрограмма для нахождения методом скалярных произведений с точностью ε = 0.000001 максимального по модулю собственного числа λ матрицы A<hr/>
 * Подпрограмма для сравнения фактического числа итераций различных методов<hr/>
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
     * Здесь инициализируется матрица A условия, её размерность, а также тут находится меню программы
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        MainClass program = new MainClass();
        //calculator - экзепляр класса MatrixCalculateClass
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        Scanner input = new Scanner(System.in);
        System.out.println("Вариант №9");
        //rows - размерность матрицы A
        int rows = 3;
        System.out.println("Размерность матрицы A = " + rows);
        //matrixA - матрица A, создается как экземпляр класса MatrixClass
        MatrixClass matrixA = new MatrixClass();
        //Инициализация размера матрицы A
        matrixA.setSize(rows, rows);

        System.out.println("Матрица A: ");
        matrixA.setElem(0, 0, -0.90701);
        matrixA.setElem(0, 1, -0.27716);
        matrixA.setElem(0, 2, 0.44570);
        matrixA.setElem(1, 0, -0.27716);
        matrixA.setElem(1, 1, 0.63372);
        matrixA.setElem(1, 2, 0.07774);
        matrixA.setElem(2, 0, 0.44570);
        matrixA.setElem(2, 1, 0.07774);
        matrixA.setElem(2, 2, -0.95535);
        matrixA.printMatrix(rows);

        //eps - точность ε = 0.000001
        //stepEps - точность для степенного метода
        double eps = 0.000001, stepEps = 0.001;
        int menuItem, k;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Нахождение методом Якоби всех собственных чисел и собственных векторов матрицы с заданной точностью ε = 0.000001.");
            System.out.println("2. Нахождение степенным методом c точностью ε = 0.001 максимальное по модулю собственное число λ матрицы A и соответствующий ему собственный вектор x");
            System.out.println("3. Нахождение методом скалярных произведений с точностью ε = 0.000001 максимальное по модулю собственное число λ матрицы A.");
            System.out.println("4. Сравнение фактического числа итераций различных методов");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    System.out.println("1. Нахождение методом Якоби всех собственных чисел и собственных векторов матрицы с заданной точностью ε = 0.000001.");
                    calculator.yakobiMethod(matrixA, rows, eps);
                    break;
                }
                case 2: {
                    program.clrscr();
                    System.out.println("2. Нахождение степенным методом c точностью ε = 0.001 максимальное по модулю собственное число λ матрицы A и соответствующий ему собственный вектор x");
                    calculator.stepMethod(matrixA, rows, stepEps);
                    break;
                }
                case 3: {
                    program.clrscr();
                    System.out.println("3. Нахождение методом скалярных произведений с точностью ε = 0.000001 максимальное по модулю собственное число λ матрицы A.");
                    calculator.scalProiz(matrixA, rows, eps);
                    break;
                }
                case 4: {
                    program.clrscr();
                    System.out.println("4. Сравнение фактического числа итераций различных методов");
                    calculator.watchFactK();
                    break;
                }
            }
            } while (menuItem != 0);
    }
}
