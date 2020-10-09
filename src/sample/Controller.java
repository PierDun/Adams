package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    private LineChart<String, Double> chart;

    @FXML
    private ChoiceBox<String> funChose;

    @FXML
    private TextField x0;

    @FXML
    private TextField y0;

    @FXML
    private TextField xn;

    @FXML
    private TextField accuracy;

    double[][] points;

    @FXML
    void solve() {
        double x0 = Double.parseDouble(this.x0.getText());
        double y0 = Double.parseDouble(this.y0.getText());
        double xn = Double.parseDouble(this.xn.getText());
        double acc = Double.parseDouble(accuracy.getText());

        AdamsMethod method = new AdamsMethod(acc, x0, xn, y0);
        method.function = funChose.getValue();
        method.solve();
        points = method.points;

        NewtonPolynomial polynomial = new NewtonPolynomial (points[0].length);
        polynomial.findCoefficients(points);

        double step = method.h / 2;
        int c = 0;
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (double x = points[0][0]; x <= points[0][points[0].length - 1]; x += step) {
            if (Math.abs(x - points[0][c]) <= step / 2) {
                XYChart.Data<String, Double> data = new XYChart.Data<>(String.format("%4.2f", x), polynomial.getN(x));
                data.setNode(new Circle(3, Color.BLUE));
                series.getData().add(data);
                c++;
            } else {
                series.getData().add(new XYChart.Data<>(String.format("%4.2f", x), polynomial.getN(x)));
            }

        }

        chart.getData().clear();
        chart.getData().add(series);
    }

    public void initialize () {
        funChose.getItems().setAll("y' = 2 x^2", "y' = x - y", "y' = sin(x) + y");
    }
}
