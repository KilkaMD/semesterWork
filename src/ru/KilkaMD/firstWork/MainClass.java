package ru.KilkaMD.firstWork;

import java.util.Scanner;

/**
 * MainClass - основной класс программы для решения СЛАУ схемой Гаусса, нахождения обратной матрицы и вычисления числа обусловленности<br/>
 * Данный класс содержит вызовы следующих подпрограмм:<br/>
 * Подпрограмма для решение СЛАУ методом Гаусса с выбором главного элемента по столбцу и строке<hr/>
 * Подпрограмма для нахождения обратной матрицы к A с помощью LU-разложения<hr/>
 * Подпрограмма для вычисления числа обусловленности<hr/>
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
     * Здесь инициализируется расширенная матрица A условия, её размерность, а также тут находится меню программы
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
        MatrixClass reverseMatrix = new MatrixClass();
        MainClass program = new MainClass();
        //calculator - экзепляр класса MatrixCalculateClass
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        Scanner input = new Scanner(System.in);
        System.out.print("Введите размерность матрицы A: ");
        //rows - размерность матрицы A
        int rows = input.nextInt();
        //matrixA - матрица A, создается как экземпляр класса MatrixClass
        MatrixClass matrixA = new MatrixClass();
        //Инициализация размера матрицы A
        matrixA.setSize(rows, rows + 1);
        double value;
        System.out.print("1. Использовать матрицу из Варианта 9\n2. Ввести матрицу самостоятельно\nВвод: ");
        int checkTypeInput = input.nextInt();
        while ((checkTypeInput != 1) && (checkTypeInput != 2)) {
            System.out.println("Неверный ввод!");
            System.out.print("1. Использовать матрицу из Варианта 9\n2. Ввести матрицу самостоятельно\nВвод: ");
            checkTypeInput = input.nextInt();
        }
        if (checkTypeInput == 1) {
            matrixA.setElem(0, 0, 12.785723);
            matrixA.setElem(0, 1, 1.534675);
            matrixA.setElem(0, 2, -3.947418);
            matrixA.setElem(0, 3, 9.60565);
            matrixA.setElem(1, 0, 1.534675);
            matrixA.setElem(1, 1, 9.709232);
            matrixA.setElem(1, 2, 0.918435);
            matrixA.setElem(1, 3, 7.30777);
            matrixA.setElem(2, 0, -3.947418);
            matrixA.setElem(2, 1, 0.918435);
            matrixA.setElem(2, 2, 7.703946);
            matrixA.setElem(2, 3, 4.21575);
        } else {
            System.out.println("Введите расширенную матрицу A: ");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows + 1; j++) {
                    value = input.nextDouble();
                    matrixA.setElem(i, j, value);
                }
            }
        }
        System.out.println("Матрица A: ");
        matrixA.printMatrix(rows);
        int menuItem;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Решение СЛАУ методом Гаусса с выбором главного элемента по столбцу и строке");
            System.out.println("2. Нахождение обратной матрицы к A");
            System.out.println("3. Вычисление числа обусловленности");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    System.out.println("1. Решение СЛАУ методом Гаусса с выбором главного элемента по столбцу и строке");
                    calculator.gaussWithMax(matrixA, rows);
                    break;
                }
                case 2: {
                    program.clrscr();
                    System.out.println("2. Нахождение обратной матрицы к A");
                    reverseMatrix = calculator.reverseMatrix(matrixA, rows);
                    System.out.println("Обратная матрица к A: ");
                    reverseMatrix.printMatrixLU(rows);
                    break;
                }
                case 3: {
                    program.clrscr();
                    reverseMatrix = calculator.reverseMatrix(matrixA, rows);
                    program.clrscr();
                    System.out.println("3. Вычисление числа обусловленности");
                    double normaA = calculator.norma(matrixA, rows);
                    double normaReverseA = calculator.norma(reverseMatrix, rows);
                    System.out.print("Число обусловленности матрицы A = ");
                    System.out.println(normaA * normaReverseA);
                    break;
                }
            }
        } while (menuItem != 0);
    }
}
