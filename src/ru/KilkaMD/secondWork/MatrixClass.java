package ru.KilkaMD.secondWork;

import java.util.Formatter;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import static java.lang.Math.abs;
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
     * Переменная(массив) для хранения порядка неизвестных в матрице при решении СЛАУ методом Гаусса с выбором ведущего элемента по строкам и столбцам
     */
    private int rowsOrder[] = {0, 1, 2};

    /**
     * Метод для взятия номера переменной после перестановки при решении СЛАУ методом Гаусса
     * @param index индекс элемента, который необходимо сопоставить его реальному индексу до перестановки
     * @return индекс элемента на позиции параметра метода
     */
    public int getRowsOrder(int index) {
        return rowsOrder[index];
    }

    /**
     * Метод для нахождения спектрального радиуса
     * @return спектральный радиус экземпляра класса MatrixClass, то есть матрицы
     */
    public double maxSobstv() {
        double max = 0;
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        EigenDecomposition decomposition = new EigenDecomposition(testMatrix);
        double eigenValues[] = decomposition.getRealEigenvalues();
        for (double eigenValue : eigenValues) {
            if (abs(eigenValue) > max) max = abs(eigenValue);
        }
        return max;
    }

    /**
     * Метод для приведения матрицы типа double[][] к типу RealMatrix, чтобы использовать возможности мат. пакета
     * @return переменную типа RealMatrix
     */
    public RealMatrix toRealMatrix() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        return testMatrix;
    }

    /**
     * Метод для вычисления спектрального радиуса матрицы перехода, если рассматривать метод Зейделя как метод простой итерации
     * @param matrixHR матрица H_r
     * @param rows порядок матрицы
     * @return спектральный радиус
     */
    public double hZaid(MatrixClass matrixHR, int rows) {
        double max = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < rows; ++j) {
                if(i == j) {
                    matrix[i][j] = 1;
                } else {
                    if(i > j) {
                        matrix[i][j] = -matrix[i][j];
                    }
                }
            }
        }
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        RealMatrix testMatrixHR = matrixHR.toRealMatrix();
        EigenDecomposition decomposition = new EigenDecomposition(inverse(testMatrix).multiply(testMatrixHR));
        double eigenValues[] = decomposition.getRealEigenvalues();
        for (double eigenValue : eigenValues) {
            if (abs(eigenValue) > max) max = abs(eigenValue);
        }
        return max;
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
     * @param value значение для вставки
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
     * Метод для поиска ведущего элемента по строкам и столбцам, а также их перестановки для прямого хода метода Гаусса
     * @param rowNum число строк матрицы
     * @param colNum число столбцов матрицы
     * @param iter номер шага метода Гаусса
     */
    public void maxElem(int rowNum, int colNum, int iter) {
        double maxValue = 0, value = 0;
        boolean maxExist = false;
        int maxI = iter, maxJ = iter, rowNumSwap;
        for (int i = iter; i < rowNum; i++) {
            for (int j = iter; j < colNum; j++) {
                value = matrix[i][j];
                if (((abs(value) > abs(maxValue)) || (!maxExist)) && (value != 0)) {
                    maxValue = value;
                    maxI = i;
                    maxJ = j;
                    maxExist = true;
                }
            }
        }
        if ((maxI != iter) || (maxJ != iter)) {
            if (maxJ != iter) {
                for (int i = 0; i < rowNum; ++i) {
                    value = matrix[i][iter];
                    matrix[i][iter] = matrix[i][maxJ];
                    matrix[i][maxJ] = value;
                }
                rowNumSwap = rowsOrder[iter];
                rowsOrder[iter] = rowsOrder[maxJ];
                rowsOrder[maxJ] = rowNumSwap;
            }
            if (maxI != iter) {
                for (int j = iter; j < colNum + 1; ++j) {
                    value = matrix[iter][j];
                    matrix[iter][j] = matrix[maxI][j];
                    matrix[maxI][j] = value;
                }
            }
        }
    }

    /**
     * Метод для печати в консоль расширенной квадратной матрицы
     * @param rows порядок матрицы
     */
    public void printMatrixFull(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows + 1; j++) {
                Formatter formatter = new Formatter();
                formatter.format("%-10.6f ", matrix[i][j]);
                System.out.print(formatter);
            }
            System.out.println("");
        }
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
     * Метод для печати в консоль вектора
     * @param rows высота вектора
     */
    public void printVector(int rows) {
        for (int i = 0; i < rows; i++) {
            Formatter formatter = new Formatter();
            formatter.format("%-10.6f ", matrix[i][0]);
            System.out.print(formatter);
            System.out.println("");
        }
    }

}
