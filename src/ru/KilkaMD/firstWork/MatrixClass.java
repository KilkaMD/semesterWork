package ru.KilkaMD.firstWork;

import java.util.Formatter;

import static java.lang.Math.abs;

public class MatrixClass {
    private double[][] matrix;
    private int rowsOrder[] = {0,1,2};

    public int getRowsOrder(int index) {
        return rowsOrder[index];
    }

    public void setSize(int rows, int columns){
        matrix = new double[rows][columns];
    }

    public void setElem(int rowNum, int colNum, double value){
        matrix[rowNum][colNum] = value;
    }

    public double getElem(int rowNum, int colNum){
        return matrix[rowNum][colNum];
    }

    public void maxElem(int rowNum, int colNum, int iter) {
        double maxValue = 0, value = 0;
        boolean maxExist = false;
        int maxI = iter, maxJ = iter, rowNumSwap;
        for(int i = iter; i < rowNum; i++) {
            for(int j = iter; j < colNum; j++) {
                value = matrix[i][j];
                if(((abs(value) > abs(maxValue)) || (!maxExist)) && (value != 0)) {
                    maxValue = value;
                    maxI = i;
                    maxJ = j;
                    maxExist = true;
                }
            }
        }
        if((maxI != iter) || (maxJ != iter)) {
            if(maxJ != iter) {
                for(int i = 0; i < rowNum; ++i) {
                    value = matrix[i][iter];
                    matrix[i][iter] = matrix[i][maxJ];
                    matrix[i][maxJ] = value;
                }
                rowNumSwap = rowsOrder[iter];
                rowsOrder[iter] = rowsOrder[maxJ];
                rowsOrder[maxJ] = rowNumSwap;
            }
            if(maxI != iter) {
                for(int j = iter; j < colNum+1; ++j) {
                    value = matrix[iter][j];
                    matrix[iter][j] =  matrix[maxI][j];
                    matrix[maxI][j] = value;
                }
            }
        }
    }

    public void printMatrix(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows + 1; j++) {
                Formatter formatter = new Formatter();
                formatter.format("%-10.6f ", matrix[i][j]);
                System.out.print(formatter);
            }
            System.out.println("");
        }
    }

    public void printMatrixLU(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                Formatter formatter = new Formatter();
                formatter.format("%-10.6f ", matrix[i][j]);
                System.out.print(formatter);
            }
            System.out.println("");
        }
    }

}
