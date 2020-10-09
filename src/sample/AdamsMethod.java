package sample;

public class AdamsMethod {
    double h;
    int N;
    double[][] points;
    double acc;
    String function;

    AdamsMethod (double acc, double x0, double xn, double y0) {
        this.acc = acc;
        N = (int) Math.ceil((-Math.log10(acc) + 1) * (xn - x0));
        h = (xn - x0) / N;
        points = new double[2][N];
        points[0][0] = x0;
        points[1][0] = y0;
        points[0][N - 1] = xn;
    }

    void solve () {
        double k0, k1, k2, k3, B, A;

        for (int i = 1; i < N - 1; i++) points[0][i] = points[0][i - 1] + h;

        for (int i = 1; i < 4; i++) {
            k0 = h * getFunction(points[0][i - 1], points[1][i - 1]);
            k1 = h * getFunction(points[0][i - 1] + h/2, points[1][i - 1] + k0/2);
            k2 = h * getFunction(points[0][i - 1] + h/2, points[1][i - 1] + k1/2);
            k3 = h * getFunction(points[0][i - 1] + h, points[1][i - 1] + k2);

            points[1][i] = points[1][i - 1] + (k0 + 2 * k1 + 2 * k2 + k3)/6;
        }

        for (int i = 4; i < N; i++) {
            points[1][i] = points[1][i - 1] + h/24 * (55 * getFunction(points[0][i - 1], points[1][i - 1])
                    - 59 *  getFunction(points[0][i - 2], points[1][i - 2])
                    + 37 * getFunction(points[0][i - 3], points[1][i - 3])
                    - 9 * getFunction(points[0][i - 4], points[1][i - 4]));

            B = points[1][i];
            A = -B;

            while (Math.abs(B - A) > acc) {
                A = B;
                B = points[1][i - 1] + h/24 * (9 * getFunction(points[0][i], points[1][i])
                        + 19 * getFunction(points[0][i - 1], points[1][i - 1])
                        - 5 * getFunction(points[0][i - 2], points[1][i - 2])
                        + getFunction(points[0][i - 3], points[1][i - 3]));
            }

            points[1][i] = B;
        }
    }

    double getFunction(double x, double y) {
        switch (function) {
            case "y' = 2 x^2" :
                return 2 * x * x;
            case "y' = x - y" :
                return x - y;
            case "y' = sin(x) + y":
                return Math.sin(x) + y;
        }
        return 0;
    }
}
