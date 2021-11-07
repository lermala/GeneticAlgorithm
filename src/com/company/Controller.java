package com.company;

import javafx.application.Application;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Controller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("");
        // определение осей
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Признаки");
        // создание графика
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Признаки особи с наименьшим эвклидовым расстоянием в виде графика и график функции Y=ln(2x)");
        // Делаем график искомой функции
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Искомая функция");

        // создаем популяцию с 20ю особями, по 100 признаков у каждой
        Population population = new Population(20, 100);
        population.createPopulation(); // заполняем популяцию особями
        double[] x = population.getxForRequiredFunc();
        double[] y = population.getRequiredFunction();
        for (int i = 0; i < x.length; i++){
            series1.getData().add(new XYChart.Data(x[i], y[i]));
        }

        // создание графика
        final LineChart<Number,Number> lineChart2 = new LineChart<Number,Number>(xAxis,yAxis);

        // выполняем генетический алгоритм
        population.startGeneticAlg();

        // Делаем график признаков особи с наименьшим эвклидовым расстоянием
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Признаки особи");
        double[] x2 = population.getIndWithMinEuclidDist().getSigns();
        double[] y2 = WorkWithNumbers.getFunction(x2);
        // все х и у вбиваем в график поточечно
        for (int i = 0; i < x.length; i++){
            series2.getData().add(new XYChart.Data(x2[i], y2[i]));
        }

        // считаем эвклидового расстояние между ними
        double euc = WorkWithNumbers.getEuclideanDistance(y, y2);
        System.out.println("euc=" + euc);

        FlowPane root = new FlowPane(lineChart, lineChart2);
        Scene scene  = new Scene(root);

        //Scene scene  = new Scene(lineChart, 800,600);
        // lineChart.getData().addAll(series1, series2);

        lineChart.getData().add(series1);
        lineChart2.getData().add(series2);

        stage.setScene(scene);
        stage.show();
    }
}
