package ru.KilkaMD.secondWork;

import java.util.Formatter;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import static java.lang.Math.abs;
import static org.apache.commons.math3.linear.MatrixUtils.inverse;

public class MatrixClass {
    private double[][] matrix;
    private int rowsOrder[] = {0, 1, 2};

    public int getRowsOrder(int index) {
        return rowsOrder[index];
    }

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

    public RealMatrix toRealMatrix() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        return testMatrix;
    }

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

    public void setSize(int rows, int columns) {
        matrix = new double[rows][columns];
    }

    public void setElem(int rowNum, int colNum, double value) {
        matrix[rowNum][colNum] = value;
    }

    public double getElem(int rowNum, int colNum) {
        return matrix[rowNum][colNum];
    }

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

    public void printVector(int rows) {
        for (int i = 0; i < rows; i++) {
            Formatter formatter = new Formatter();
            formatter.format("%-10.6f ", matrix[i][0]);
            System.out.print(formatter);
            System.out.println("");
        }
    }

}
