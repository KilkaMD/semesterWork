package ru.KilkaMD.thirdWork;

import java.util.Formatter;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import static java.lang.Math.*;

/**
 * MatrixClass - класс для реализаций операций над матрицами
 */
public class MatrixClass {
    /**
     * Переменная(двумерный массив) для хранения матрицы
     */
    private double[][] matrix;

    /**
     * Метод для приведения матрицы типа double[][] к типу RealMatrix, чтобы использовать возможности мат. пакета
     * @return переменную типа RealMatrix
     */
    public RealMatrix toRealMatrix() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        return testMatrix;
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
     * Метод для нахождения наибольшего по абсолютной величине элемента матрицы выше диагонали
     * @param rows порядок матрицы
     * @return массив размерностью 2 элемента, где на 0 позиции находится номер строки, а на 1 - номер столбца наибольшего элемента
     */
    public int[] getMaxElemIndex(int rows) {
        int maxIndex[] = new int[2];
        double max = 0;
        for(int i = 0; i < rows; ++i) {
            for(int j = i; j < rows; ++j) {
                if(j != i) {
                    if(abs(matrix[i][j]) > abs(max)) {
                        maxIndex[0] = i;
                        maxIndex[1] = j;
                        max = matrix[i][j];
                    }
                }
            }
        }
        return maxIndex;
    }

    /**
     * Метод для подсчета матрицы X(метода Якоби) при очередной итерации
     * @param matrixV матрица V - ортогональная матрица вращения
     */
    public void iterX(MatrixClass matrixV) {
        RealMatrix matrixX = MatrixUtils.createRealMatrix(matrix);
        RealMatrix realMatrixV = matrixV.toRealMatrix();
        RealMatrix res = matrixX.multiply(realMatrixV);
        matrix = res.getData();
    }

    /**
     * Метод для подсчета вектора Y(следующего вектора последовательности при выполнении степенного метода) при очередной итерации
     * @param matrixA матрица A условия
     */
    public void iterY(MatrixClass matrixA) {
        RealMatrix matrixY = MatrixUtils.createRealMatrix(matrix);
        RealMatrix realMatrixA = matrixA.toRealMatrix();
        RealMatrix res = matrixY.preMultiply(realMatrixA);
        matrix = res.getData();
    }

    /**
     * Метод для подсчета c = cos(φ_k) из метода Якоби
     * @param maxIndex массив, который содержит координаты максимального наддиагонального элемента
     * @return cos(φ_k)
     */
    public double findC(int maxIndex[]) {
        if(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]] == 0) {
            return cos(PI/4);
        }
        double value = (2*matrix[maxIndex[0]][maxIndex[1]])/(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]]);
        return cos(atan(value)/2);
    }

    /**
     * Метод для подсчета s = sin(φ_k) из метода Якоби
     * @param maxIndex массив, который содержит координаты максимального наддиагонального элемента
     * @return sin(φ_k)
     */
    public double findS(int maxIndex[]) {
        if(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]] == 0) {
            return sin(PI/4);
        }
        double value = (2*matrix[maxIndex[0]][maxIndex[1]])/(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]]);
        return sin(atan(value)/2);
    }

    /**
     * Метод для печати в консоль квадратной матрицы
     * @param rows порядок матрицы
     */
    public void printMatrix(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                Formatter formatter = new Formatter();
                formatter.format("%-10.5f ", matrix[i][j]);
                System.out.print(formatter);
            }
            System.out.println("");
        }
    }

}

