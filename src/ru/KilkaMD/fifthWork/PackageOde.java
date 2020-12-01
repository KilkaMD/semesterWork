package ru.KilkaMD.fifthWork;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

/**
 * PackageOde - класс, реализующий интерфейс класса FirstOrderDifferentialEquations
 */
public class PackageOde implements FirstOrderDifferentialEquations {

    /**
     * Метод для инициализации начальных данных
     * @return вектор начальных данных
     */
    public double [] getInitialConditions() {
        return new double [] { 1.0, 1.0 };
    }

    /**
     * Метод для инициализации размерности задачи
     * @return 2, так как решается система уравнений
     */
    @Override
    public int getDimension() {
        return 2;
    }

    /**
     * Заглушка
     * @param t заглушка
     * @param y заглушка
     * @param yDot заглушка
     * @throws MaxCountExceededException заглушка
     * @throws DimensionMismatchException заглушка
     */
    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
    }
}
