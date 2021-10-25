package psm;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {

    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture future;

    @FXML
    private NumberAxis xAxis ;

    @FXML
    private NumberAxis yAxis ;

    @FXML
    LineChart<Integer, Double> chart;

    Struna struna;
    private boolean animating = false;

    public Controller(){
        struna = new Struna();
    }

    public void updateChart(double[] y){
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        for (int i = 0; i < y.length; i++) {
            series.getData().add(new XYChart.Data<>(i, y[i]));
        }
        chart.getData().clear();
        chart.getData().add(series);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(40);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-1);
        yAxis.setUpperBound(1);
        yAxis.setTickUnit(0.1);

        updateChart(struna.getData());
    }

    @FXML
    public void onChartClicked()
    {
        struna.computeStep();
        updateChart(struna.getData());
    }

    @FXML
    public void onResetClicked()
    {
        struna = new Struna();
        updateChart(struna.getData());
        if(animating) {
            future.cancel(true);
            future = null;
            animating = false;
        }
    }

    @FXML
    public void onAnimateClicked() {
        onResetClicked();
        animating = true;
        Runnable dataGetter = () -> {
            Platform.runLater(this::onChartClicked);
        };

        future = service.scheduleWithFixedDelay(dataGetter, 0, 30, TimeUnit.MILLISECONDS);
    }
}
