/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.RoadWeatherData;
import static fi.tuni.swdesign.group3.view.RoadDataVisualizer.GRID_CELL_HEIGHT;
import static fi.tuni.swdesign.group3.view.RoadDataVisualizer.GRID_CELL_WIDTH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Lauri
 */
public class WeatherDataVisualizer extends DataVisualizer{
    private RoadWeatherData data;
    private ArrayList<String> obsTypesToVisualize;
    private ArrayList<String> preTypesToVisualize;
    private ArrayList<String> perMonthTypesToVisualize;
    
    WeatherDataVisualizer(MainView mainView, RoadWeatherData data) {
        super(mainView);
        this.data = data;
    }
    
    public ArrayList<String> getObsTypesToVisualize() {
        return this.obsTypesToVisualize;
    }
    
    public void setObsTypesToVisualize(ArrayList<String> values) {
        this.obsTypesToVisualize = values;
    }
    
    public ArrayList<String> getPreTypesToVisualize() {
        return this.preTypesToVisualize;
    }
    
    public void setPreTypesToVisualize(ArrayList<String> forecasts) {
        this.preTypesToVisualize = forecasts;
    }
    
    public ArrayList<String> getPerMonthTypesToVisualize() {
        return perMonthTypesToVisualize;
    }
    
    public void setPerMonthTypesToVisualize(ArrayList<String> perMonthTypesToVisualize) {
        this.perMonthTypesToVisualize = perMonthTypesToVisualize;
    }
    
    @Override
    public void visualizeData() {
        DataTab dataTab = (DataTab) super.mainView.getTabPane().
                getSelectionModel().getSelectedItem();
        TabPane chartTabPane = dataTab.getChartTabPane();
        if (!this.obsTypesToVisualize.isEmpty()) {
            Tab observedTab = new Tab("Observed values");
            observedTab.setContent(visualizeObservedValues());
            chartTabPane.getTabs().add(observedTab);
        }
        if (!this.preTypesToVisualize.isEmpty()) {
            Tab predictedTab = new Tab("Predicted values");
            predictedTab.setContent(visualizePredictedValues());
            chartTabPane.getTabs().add(predictedTab);
        }
    }
    
    private VBox visualizeObservedValues() {
        VBox observedView = new VBox();
        observedView.setAlignment(Pos.CENTER);
        observedView.setSpacing(super.V_GAP);
        
        GridPane observedGrid = new GridPane();
        observedGrid.setAlignment(Pos.CENTER);
        observedGrid.setGridLinesVisible(true);
        
        Label tableTitle = new Label("Observed weather values: " + 
                this.data.getLocation() + " - " + this.data.getTime());
        tableTitle.setAlignment(Pos.CENTER);
        
        Label temperatureLabel = new Label("Temperature (°C)");
        temperatureLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        temperatureLabel.setAlignment(Pos.CENTER);
        Label windLabel = new Label("Wind speed (m/s)");
        windLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        windLabel.setAlignment(Pos.CENTER);
        Label cloudLabel = new Label("Cloudiness");
        cloudLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        cloudLabel.setAlignment(Pos.CENTER);
        
        observedGrid.addRow(0, temperatureLabel, windLabel,
                cloudLabel);
        
        Label temperatureValue = new Label();
        if (this.obsTypesToVisualize.contains("Temperature")) {
            temperatureValue.setText(Float.toString(this.data.getTemperature()));
        }
        temperatureValue.setPrefSize(super.GRID_CELL_WIDTH, super.GRID_CELL_HEIGHT);
        temperatureValue.setAlignment(Pos.CENTER);
        
        Label windValue = new Label();
        if (this.obsTypesToVisualize.contains("Wind speed")) {
            windValue.setText(Float.toString(this.data.getWind()));
        }
        windValue.setPrefSize(super.GRID_CELL_WIDTH, super.GRID_CELL_HEIGHT);
        windValue.setAlignment(Pos.CENTER);
        
        Label cloudValue = new Label();
        if (this.obsTypesToVisualize.contains("Cloudiness")) {
            cloudValue.setText(Float.toString(this.data.getCloudiness()));
        }
        cloudValue.setPrefSize(super.GRID_CELL_WIDTH, super.GRID_CELL_HEIGHT);
        cloudValue.setAlignment(Pos.CENTER);
        
        observedGrid.addRow(1, temperatureValue, windValue, cloudValue);
        observedView.getChildren().addAll(tableTitle, observedGrid);
        return observedView;
    }
    
    private LineChart visualizePredictedValues() {
        HashMap<String, RoadWeatherData> forecasts = this.data.getForecasts();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart predictedChart = new LineChart(xAxis,yAxis);
        predictedChart.setTitle("Predicted weather values: " + 
                this.data.getLocation() + " - " + this.data.getTime());
        xAxis.setLabel("Forecast time (hrs)");
        yAxis.setLabel("Amount (°C / m/s / cloudiness)");
        predictedChart.setCreateSymbols(false);
            
        for (String type : this.preTypesToVisualize) {
            XYChart.Series series = new XYChart.Series();
            series.setName(type);
            for (String time : forecasts.keySet()) {
                if (type.equals("Temperature")) {
                    series.getData().add(new XYChart.Data(time, forecasts.get(time)
                            .getTemperature()));
                }
                else if (type.equals("Wind speed")) {
                    series.getData().add(new XYChart.Data(time, forecasts.get(time)
                            .getWind()));
                }
            }
            predictedChart.getData().add(series);
        }
        
        return predictedChart;
    }
    
    private AreaChart visualizePerMonthValues() {
        HashMap<String, RoadWeatherData> dailyValues = new HashMap<>();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart perMonthChart = new AreaChart(xAxis, yAxis);
        perMonthChart.setTitle("Predicted weather values: " + 
                this.data.getLocation() + " - " + this.data.getTime());
        xAxis.setLabel("Date (dd.mm)");
        yAxis.setLabel("Temperature (°C)");
        perMonthChart.setCreateSymbols(false);
        
        for (String type : this.perMonthTypesToVisualize) {
            XYChart.Series series = new XYChart.Series();
            series.setName(type);
            for (String date : dailyValues.keySet()) {
                if (type.equals("Average temperature")) {
                    series.getData().add(new XYChart.Data(date, dailyValues.get(date)
                            .getAVGTemperature()));
                }
                else if (type.equals("Max & min temperatures")) {
                    series.getData().add(new XYChart.Data(date, dailyValues.get(date)
                            .getMINTemperature()));
                    series.getData().add(new XYChart.Data(date, dailyValues.get(date)
                            .getMAXTemperature()));
                }
            }
            perMonthChart.getData().add(series);
        }
        return perMonthChart;
    }
}
