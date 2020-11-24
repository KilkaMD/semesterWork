package ru.KilkaMD.secondWork;

import java.util.Scanner;

/**
 * MainClass - основной класс программы для решения СЛАУ итерационными методами (простой итерации, Зейделя, верхней релаксации).<br/>
 * Данный класс содержит вызовы следующих подпрограмм:<br/>
 * Подпрограмма для преобразования исходной системы к системе вида x = Hx+g, а также вычисления ||H||<hr/>
 * Подпрограмма для нахождения априорной оценки того k, при котором ||x − x^(k)|| < ε, ε = 0.001<hr/>
 * Подпрограмма для вычисления решения методом простой итерации с точностью ε = 0.001<hr/>
 * Подпрограмма для вычисления решения системы x = Hx + g методом Зейделя с точностью ε = 0.001<hr/>
 * Подпрограмма для вычисления спектрального радиуса матрицы перехода, если рассматривать метод Зейделя как метод простой итерации<hr/>
 * Подпрограмма для вычисления решения системы Ax = b методом верхней релаксации с точностью ε = 0.001<hr/>
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
     * Здесь инициализируется расширенная матрица A условия, её размерность, а также находится вектор решения системы с помощью метода Гаусса
     * @param args параметры командной строки
     */
    public static void main(String[] args) {
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
        System.out.println("1. Использовать матрицу из Варианта 9");
        System.out.println("2. Ввести матрицу самостоятельно");
        System.out.print("Ввод: ");
        int checkTypeInput = input.nextInt();
        while ((checkTypeInput != 1) && (checkTypeInput != 2)) {
            System.out.println("Неверный ввод!");
            System.out.println("1. Использовать матрицу из Варианта 9");
            System.out.println("2. Ввести матрицу самостоятельно");
            System.out.print("Ввод: ");
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
        matrixA.printMatrixFull(rows);

        //vectorX - вектор точного решения СЛАУ, создается как экземпляр класса MatrixClass
        MatrixClass vectorX = new MatrixClass();
        //Инициализация размера вектора x
        vectorX.setSize(rows, 1);
        //Для нахождения точного решения применяется метод calculator.gaussWithMax
        vectorX = calculator.gaussWithMax(matrixA, rows);
        //matrixH - матрица H, создается как экземпляр класса MatrixClass
        MatrixClass matrixH = new MatrixClass();
        //Инициализация размера матрицы H
        matrixH.setSize(rows, rows);
        //gVector - вектор g, создается как экземпляр класса MatrixClass
        MatrixClass gVector = new MatrixClass();
        //Инициализация размера вектора g
        gVector.setSize(rows, 1);

        double eps = 0.001, specRadius;
        int menuItem, k;
        //Меню программы
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Преобразование исходной системы к системе вида x = Hx+g, а также вычисление ||H||");
            System.out.println("2. Нахождение априорной оценки того k, при котором ||x − x^(k)|| < ε, ε = 0.001");
            System.out.println("3. Вычисление решения методом простой итерации с точностью ε = 0.001");
            System.out.println("4. Вычисление решения системы x = Hx + g методом Зейделя с точностью ε = 0.001");
            System.out.println("5. Вычисление спектрального радиуса матрицы перехода, если рассматривать метод Зейделя как метод простой итерации");
            System.out.println("6. Вычисление решения системы Ax = b методом верхней релаксации с точностью ε = 0.001");
            System.out.println("7. Сравнение фактического числа итераций различных методов");
            System.out.println("0. Выход");
            System.out.print("Ввод: ");
            menuItem = input.nextInt();
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    gVector = calculator.findG(matrixA, rows);
                    double normaH = calculator.norma(matrixH, rows);
                    System.out.println("Матрица H: ");
                    matrixH.printMatrix(rows);
                    System.out.println("Вектор g: ");
                    gVector.printVector(rows);
                    System.out.println("Норма матрицы H = " + normaH);
                    break;
                }
                case 2: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    gVector = calculator.findG(matrixA, rows);
                    double normaH = calculator.norma(matrixH, rows);
                    double normaG = calculator.normaVector(gVector, rows);
                    k = calculator.precision(normaH, normaG, eps);
                    System.out.println("Найденное значение k = " + k);
                    break;
                }
                case 3: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    gVector = calculator.findG(matrixA, rows);
                    double normaH = calculator.norma(matrixH, rows);
                    double normaG = calculator.normaVector(gVector, rows);
                    k = calculator.precision(normaH, normaG, eps);
                    calculator.methodIter(matrixH, gVector, vectorX, rows, eps, k);
                    break;
                }
                case 4: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    gVector = calculator.findG(matrixA, rows);
                    calculator.zaidel(matrixH, gVector, vectorX, rows, eps);
                    break;
                }
                case 5: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    specRadius = calculator.findSpecRadius(matrixH, rows);
                    System.out.println("Спектрального радиуса матрицы перехода, если рассматривать метод Зейделя как метод простой итерации = "+ specRadius);
                    double specRadiusH = matrixH.maxSobstv();
                    System.out.println("Спектрального радиуса матрицы перехода метода простой итерации = "+specRadiusH);
                    break;
                }
                case 6: {
                    program.clrscr();
                    matrixH = calculator.findH(matrixA, rows);
                    gVector = calculator.findG(matrixA, rows);
                    specRadius = matrixH.maxSobstv();
                    calculator.relax(matrixH, gVector, vectorX, rows, eps, specRadius);
                    break;
                }
                case 7: {
                    program.clrscr();
                    calculator.watchFactK();
                    break;
                }
            }
        } while (menuItem != 0);
    }
}
