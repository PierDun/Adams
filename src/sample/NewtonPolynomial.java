package sample;

import java.math.BigDecimal;
import java.math.MathContext;

public class NewtonPolynomial {
    BigDecimal [] coefficients;
    double [][] points;
    double h;

    NewtonPolynomial (int a) { coefficients = new BigDecimal[a]; }

    void findCoefficients (double [][] points) {
        this.points = points;
        coefficients[0] = BigDecimal.valueOf(points[1][0]);
        h = points[0][1] - points[0][0];

        for (int i = 1; i < coefficients.length; i++) {
            BigDecimal N = BigDecimal.valueOf((points[1][i] - points[1][0]) / Math.pow(h, i)).divide(factorial(i), MathContext.DECIMAL128);
            for (int j = 1; j < i; j++) {
                N = N.subtract(coefficients[j].multiply(BigDecimal.valueOf(Math.pow(h, j - i)).divide(factorial(i - j), MathContext.DECIMAL128)));
            }
            coefficients[i] = N;
        }
    }

    double getN (double x) {
        BigDecimal N = BigDecimal.valueOf(points[1][0]);
        double X = 1;
        for (int i = 1; i < coefficients.length; i++) {
            X = X * (x - points[0][i - 1]);
            N = N.add(coefficients[i].multiply(BigDecimal.valueOf(X)));
        }
        return N.doubleValue();
    }

    BigDecimal factorial (int x) {
        BigDecimal f = new BigDecimal("1");
        if (x == 0) return f;
        for (int i = 1; i <= x; i++) f = f.multiply(BigDecimal.valueOf(i));
        return f;
    }
}
