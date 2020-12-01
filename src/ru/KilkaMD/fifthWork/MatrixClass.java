package ru.KilkaMD.fifthWork;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Formatter;

import static org.apache.commons.math3.linear.MatrixUtils.inverse;

/**
 * MatrixClass - класс для реализаций операций над матрицами
 */
public class MatrixClass {

    /**
     * Переменная(двумерный массив) для хранения матрицы
     */
    private double[][] matrix;

    /**
     * Метод для нахождения собственных чисел матрицы и печати их на экран
     * @return вектор собственных чисел
     */
    public double[] eigen() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        EigenDecomposition decomposition = new EigenDecomposition(testMatrix);
        double eigenValues[] = decomposition.getRealEigenvalues();
        System.out.println("Характеристические числа: ");
        System.out.println("λ1 = " + eigenValues[0]);
        System.out.println("λ2 = " + eigenValues[1]);
        return eigenValues;
    }

    /**
     * Метод для нахождения собственных чисел матриц W1 и W2 метода Адамса третьего порядка
     * @return вектор собственных чисел соответствующей матрицы
     */
    public double[] eigenWi() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        EigenDecomposition decomposition = new EigenDecomposition(testMatrix);
        double eigenValues[] = decomposition.getRealEigenvalues();
        return eigenValues;
    }

    /**
     * Метод инициализации размера матрицы
     * @param rows кол-во строк
     * @param columns кол-во столбцов
     */
    public void setSize(int rows, int columns) {
        matrix = new double[rows][columns];
    }

    /**
     * Метод для вставки элемента в матрицу
     * @param rowNum номер строки для вставки
     * @param colNum номер стобца для вставки
     * @param value  значение для вставки
     */
    public void setElem(int rowNum, int colNum, double value) {
        matrix[rowNum][colNum] = value;
    }

    /**
     * Метод для взятия элемента матрицы
     * @param rowNum номер строки, где находится элемент
     * @param colNum номер стобца, где находится элемент
     * @return matrix[rowNum][colNum]
     */
    public double getElem(int rowNum, int colNum) {
        return matrix[rowNum][colNum];
    }

    /**
     * Метод для печати в консоль квадратной матрицы
     * @param rows порядок матрицы
     */
    public void printMatrix(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                Formatter formatter = new Formatter();
                formatter.format("%-10.6f ", matrix[i][j]);
                System.out.print(formatter);
            }
            System.out.println("");
        }
    }

    /**
     * Метод для поиска матрицы W метода Эйлера
     * @param matrixA матрица A условия задачи
     * @param step шаг метода
     */
    public void foundWEuler(MatrixClass matrixA, double step) {
        matrix[0][0] = 1 + matrixA.getElem(0, 0) * step;
        matrix[0][1] = 0 + matrixA.getElem(0, 1) * step;
        matrix[1][0] = 0 + matrixA.getElem(1, 0) * step;
        matrix[1][1] = 1 + matrixA.getElem(1, 1) * step;
    }

    /**
     * Метод для поиска матрицы W обратного метода Эйлера
     * @param matrixA матрица A условия задачи
     * @param step шаг метода
     */
    public void foundWEulerBack(MatrixClass matrixA, double step) {
        matrix[0][0] = 1 - matrixA.getElem(0, 0) * step;
        matrix[0][1] = 0 - matrixA.getElem(0, 1) * step;
        matrix[1][0] = 0 - matrixA.getElem(1, 0) * step;
        matrix[1][1] = 1 - matrixA.getElem(1, 1) * step;
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        RealMatrix reverseTestMatrix = inverse(testMatrix);
        matrix = reverseTestMatrix.getData();
    }

    /**
     * Метод для поиска матрицы W1 интерполяционного метода Адамса третьего порядка
     * @param matrixA матрица A условия задачи
     * @param step шаг метода
     */
    public void foundW1(MatrixClass matrixA, double step) {
        matrix[0][0] = 1 - (matrixA.getElem(0, 0) * step * 5) / 12;
        matrix[0][1] = 0 - (matrixA.getElem(0, 1) * step * 5) / 12;
        matrix[1][0] = 0 - (matrixA.getElem(1, 0) * step * 5) / 12;
        matrix[1][1] = 1 - (matrixA.getElem(1, 1) * step * 5) / 12;
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        RealMatrix reverseTestMatrix = inverse(testMatrix);
        double[][] rightMatrix = new double[2][2];
        rightMatrix[0][0] = 1 + (matrixA.getElem(0, 0) * step * 2) / 3;
        rightMatrix[0][1] = 0 + (matrixA.getElem(0, 1) * step * 2) / 3;
        rightMatrix[1][0] = 0 + (matrixA.getElem(1, 0) * step * 2) / 3;
        rightMatrix[1][1] = 1 + (matrixA.getElem(1, 1) * step * 2) / 3;
        RealMatrix testRightMatrix = MatrixUtils.createRealMatrix(rightMatrix);
        RealMatrix res = reverseTestMatrix.multiply(testRightMatrix);
        matrix = res.getData();
    }

    /**
     * Метод для поиска матрицы W2 интерполяционного метода Адамса третьего порядка
     * @param matrixA матрица A условия задачи
     * @param step шаг метода
     */
    public void foundW2(MatrixClass matrixA, double step) {
        matrix[0][0] = 1 - (matrixA.getElem(0, 0) * step * 5) / 12;
        matrix[0][1] = 0 - (matrixA.getElem(0, 1) * step * 5) / 12;
        matrix[1][0] = 0 - (matrixA.getElem(1, 0) * step * 5) / 12;
        matrix[1][1] = 1 - (matrixA.getElem(1, 1) * step * 5) / 12;
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        RealMatrix reverseTestMatrix = inverse(testMatrix);
        double[][] rightMatrix = new double[2][2];
        rightMatrix[0][0] = (matrixA.getElem(0, 0) * step) / 12;
        rightMatrix[0][1] = (matrixA.getElem(0, 1) * step) / 12;
        rightMatrix[1][0] = (matrixA.getElem(1, 0) * step) / 12;
        rightMatrix[1][1] = (matrixA.getElem(1, 1) * step) / 12;
        RealMatrix testRightMatrix = MatrixUtils.createRealMatrix(rightMatrix);
        RealMatrix res = reverseTestMatrix.multiply(testRightMatrix);
        matrix = res.getData();
    }
}
