package ru.KilkaMD.fourthWork;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * PackageOde - класс, реализующий интерфейс класса FirstOrderDifferentialEquations, для нахождения решения задачи c шагом h с помощью функций математического пакета
 */
public class PackageOde implements FirstOrderDifferentialEquations {

    /**
     * Метод для инициализации начальных данных y_0
     * @return вектор начальных данных
     */
    public double [] getInitialConditions() {
        return new double [] { 0.0 };
    }

    /**
     * Метод для инициализации размерности задачи
     * @return 1, так как решается только одно уравнение
     */
    @Override
    public int getDimension() {
        return 1;
    }

    /**
     * Метод для вычисления значения y' = f(x_m,y_m)
     * @param t x_m
     * @param y вектор итерации
     * @param yDot вектор значений, полученных после итерации
     * @throws MaxCountExceededException встроенные исключения для корректной работы метода
     * @throws DimensionMismatchException встроенные исключения для корректной работы метода
     */
    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        ProblemClass func = new ProblemClass();
        yDot[0] = func.funcComputation(y[0], t);
    }
}
