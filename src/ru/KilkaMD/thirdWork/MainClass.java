package ru.KilkaMD.thirdWork;

import java.util.Scanner;

public class MainClass {

    public static void clrscr() {
        for (int clear = 0; clear < 10; clear++) {
            System.out.println("\b");
        }
    }

    public static void main(String[] args) {
        MainClass program = new MainClass();
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        Scanner input = new Scanner(System.in);
        System.out.println("Вариант №9");
        int rows = 3;
        System.out.println("Размерность матрицы A = " + rows);
        MatrixClass matrixA = new MatrixClass();
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


        double eps = 0.000001, stepEps = 0.001;
        int menuItem, k;
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
                    calculator.yakobiMethod(matrixA, rows, eps);
                    break;
                }
                case 2: {
                    program.clrscr();
                    calculator.stepMethod(matrixA, rows, stepEps);
                    break;
                }
                case 3: {
                    program.clrscr();
                    calculator.scalProiz(matrixA, rows, eps);
                    break;
                }
                case 4: {
                    program.clrscr();
                    calculator.watchFactK();
                    break;
                }
            }
            } while (menuItem != 0);
    }
}
