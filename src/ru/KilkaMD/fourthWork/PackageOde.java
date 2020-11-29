package ru.KilkaMD.fourthWork;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class PackageOde implements FirstOrderDifferentialEquations {

    public double [] getInitialConditions() {
        return new double [] { 0.0 };
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        ProblemClass func = new ProblemClass();
        yDot[0] = func.funcComputation(y[0], t);
    }
}
