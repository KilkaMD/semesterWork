package ru.KilkaMD.firstWork;

import java.util.Scanner;


public class MainClass {

    public static void clrscr() {
        for(int clear = 0; clear < 10; clear++) {
            System.out.println("\b") ;
        }
    }


        public static void main(String[] args) {
        MatrixClass reverseMatrix = new MatrixClass();
        MainClass program = new MainClass();
        MatrixCalculateClass calculator = new MatrixCalculateClass();
        Scanner input = new Scanner(System.in);
        System.out.print("Введите размерность матрицы A: ");
        int rows = input.nextInt();
        MatrixClass matrixA = new MatrixClass();
        matrixA.setSize(rows, rows+1);
        double value;
        System.out.print("1. Использовать матрицу из Варианта 9\n2. Ввести матрицу самостоятельно\nВвод: ");
        int checkTypeInput = input.nextInt();
        while ((checkTypeInput != 1) && (checkTypeInput != 2)) {
            System.out.println("Неверный ввод!");
            System.out.print("1. Использовать матрицу из Варианта 9\n2. Ввести матрицу самостоятельно\nВвод: ");
            checkTypeInput = input.nextInt();
        }
        if(checkTypeInput == 1) {
            matrixA.setElem(0,0, 12.785723);
            matrixA.setElem(0,1, 1.534675);
            matrixA.setElem(0,2, -3.947418);
            matrixA.setElem(0,3, 9.60565);
            matrixA.setElem(1,0, 1.534675);
            matrixA.setElem(1,1, 9.709232);
            matrixA.setElem(1,2, 0.918435);
            matrixA.setElem(1,3, 7.30777);
            matrixA.setElem(2,0, -3.947418);
            matrixA.setElem(2,1, 0.918435);
            matrixA.setElem(2,2, 7.703946);
            matrixA.setElem(2,3, 4.21575);
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
        do {
            System.out.println("--------------Меню---------------");
            System.out.println("1. Решение СЛАУ методом Гаусса с выбором главного элемента по столбцу и строке");
            System.out.println("2. Нахождение обратной матрицы к A");
            System.out.println("3. Вычисление числа обусловленности");
            System.out.println("0. Выход");
            menuItem = input.nextInt();
            switch (menuItem) {
                case 1: {
                    program.clrscr();
                    calculator.gaussWithMax(matrixA, rows);
                    break;
                }
                case 2: {
                    program.clrscr();
                    reverseMatrix = calculator.reverseMatrix(matrixA, rows);
                    System.out.println("Обратная матрица к A: ");
                    reverseMatrix.printMatrixLU(rows);
                    break;
                }
                case 3: {
                    program.clrscr();
                    reverseMatrix = calculator.reverseMatrix(matrixA, rows);
                    program.clrscr();
                    double normaA = calculator.norma(matrixA, rows);
                    double normaReverseA = calculator.norma(reverseMatrix, rows);
                    System.out.print("Число обусловленности матрицы A = ");
                    System.out.println(normaA*normaReverseA);
                    break;
                }
            }
        } while(menuItem != 0);
    }
}
