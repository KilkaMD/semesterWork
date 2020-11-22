package ru.KilkaMD.thirdWork;

import java.util.Formatter;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;

import static java.lang.Math.*;

public class MatrixClass {
    private double[][] matrix;

    public RealMatrix toRealMatrix() {
        RealMatrix testMatrix = MatrixUtils.createRealMatrix(matrix);
        return testMatrix;
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

    public void iterX(MatrixClass matrixV) {
        RealMatrix matrixX = MatrixUtils.createRealMatrix(matrix);
        RealMatrix realMatrixV = matrixV.toRealMatrix();
        RealMatrix res = matrixX.multiply(realMatrixV);
        matrix = res.getData();
    }

    public void iterY(MatrixClass matrixA) {
        RealMatrix matrixY = MatrixUtils.createRealMatrix(matrix);
        RealMatrix realMatrixA = matrixA.toRealMatrix();
        RealMatrix res = matrixY.preMultiply(realMatrixA);
        matrix = res.getData();
    }

    public double findC(int maxIndex[]) {
        if(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]] == 0) {
            return cos(PI/4);
        }
        double value = (2*matrix[maxIndex[0]][maxIndex[1]])/(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]]);
        return cos(atan(value)/2);
    }

    public double findS(int maxIndex[]) {
        if(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]] == 0) {
            return sin(PI/4);
        }
        double value = (2*matrix[maxIndex[0]][maxIndex[1]])/(matrix[maxIndex[0]][maxIndex[0]] - matrix[maxIndex[1]][maxIndex[1]]);
        return sin(atan(value)/2);
    }

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

