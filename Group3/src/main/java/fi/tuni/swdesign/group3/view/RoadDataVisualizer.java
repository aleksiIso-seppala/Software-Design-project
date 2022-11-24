/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.RoadData;
import fi.tuni.swdesign.group3.RoadTrafficData;
import fi.tuni.swdesign.group3.RoadTrafficDataForecast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataVisualizer extends DataVisualizer{
    private RoadTrafficData data;
    private ArrayList<String> mTasksToVisualize;
    private ArrayList<String> forecastsToVisualize;

    RoadDataVisualizer(MainView mainView, RoadTrafficData data) {
        super(mainView);
        this.data = data;
        this.mTasksToVisualize = new ArrayList();
        this.forecastsToVisualize = new ArrayList();
    }
    
    public ArrayList<String> getMTasksToVisualize() {
        return this.mTasksToVisualize;
    }
    
    public void setMTasksToVisualize(ArrayList<String> MTasks) {
        this.mTasksToVisualize = MTasks;
    }
    
    public ArrayList<String> getForecastsToVisualize() {
        return this.forecastsToVisualize;
    }
    
    public void setForecastsToVisualize(ArrayList<String> forecasts) {
        this.forecastsToVisualize = forecasts;
    }
    
    @Override
    public void visualizeData() {
        DataTab dataTab = (DataTab) super.mainView.getTabPane().
                getSelectionModel().getSelectedItem();
        TabPane chartTabPane = dataTab.getChartTabPane();
        chartTabPane.getTabs().clear();
        if (!this.mTasksToVisualize.isEmpty()) {
            Tab maintenanceTab = new Tab("Maintenance tasks");
            if (!this.data.getMaintenanceTasks().isEmpty()) {
                maintenanceTab.setContent(visualizeMaintenanceTasks());
            }
            else {
                maintenanceTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(maintenanceTab); 
        }
        if (!this.forecastsToVisualize.isEmpty()) {
            Tab forecastTab = new Tab ("Condition forecast");
            if (!this.data.getForecasts().isEmpty()) {
                forecastTab.setContent(visualizeConditionForecast());
            }
            else {
                forecastTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(forecastTab);
        }
        
    }
    
    private BarChart visualizeMaintenanceTasks() {
        HashMap<String,Integer> maintenanceTasks = this.data.getMaintenanceTasks();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> maintenanceChart = 
            new BarChart<String,Number>(xAxis,yAxis);
        maintenanceChart.setTitle("Maintenance tasks: " + 
                this.data.getLocation() + " - " + this.data.getTime());
        xAxis.setLabel("Maintenance task");
        yAxis.setLabel("Amount");
        
//        Set<String> taskTypes = maintenanceTasks.keySet();
        for (String type : this.mTasksToVisualize) {
            XYChart.Series series = new XYChart.Series();
            series.setName(type);
            series.getData().add(new XYChart.Data(type, maintenanceTasks.get(type)));
            maintenanceChart.getData().add(series);
        }
        
        return maintenanceChart;
    }
    
    private VBox visualizeConditionForecast() {
        HashMap<String, RoadTrafficDataForecast> forecasts = this.data.getForecasts();
        int row_count = 0;
        VBox forecastView = new VBox();
        forecastView.setAlignment(Pos.CENTER);
        forecastView.setSpacing(V_GAP);
        
        GridPane forecastGrid = new GridPane();
        forecastGrid.setAlignment(Pos.CENTER);
        forecastGrid.setGridLinesVisible(true);
        
        Label tableTitle = new Label("Road condition forecast: " + 
                this.data.getLocation() + " - " + this.data.getTime());
        tableTitle.setAlignment(Pos.CENTER);
        
        Label timeLabel = new Label("Forecast time (hrs)");
        timeLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        timeLabel.setAlignment(Pos.CENTER);
        Label precipitationLabel = new Label("Precipitation");
        precipitationLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        precipitationLabel.setAlignment(Pos.CENTER);
        Label winterSlipLabel = new Label("Winter slipperiness");
        winterSlipLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        winterSlipLabel.setAlignment(Pos.CENTER);
        Label overallLabel = new Label("Overall condition");
        overallLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        overallLabel.setAlignment(Pos.CENTER);
        
        forecastGrid.addRow(row_count, timeLabel, precipitationLabel, winterSlipLabel,
                overallLabel);
        row_count++;
        
        Set<String> forecastTimes = forecasts.keySet();
        for (String time : forecastTimes) {
            Label timeValue = new Label(time);
            timeValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            timeValue.setAlignment(Pos.CENTER);
            Label precipitationValue = new Label(forecasts.get(time).getPrecipitation());
            precipitationValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            precipitationValue.setAlignment(Pos.CENTER);
            Label winterSlipValue = new Label();
            if (forecasts.get(time).isWinterSlipperines()) {
                winterSlipValue.setText("Yes");
            }
            else if (this.forecastsToVisualize.contains("Winter Slipperiness")) {
                winterSlipValue.setText("No");
            }
            winterSlipValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            winterSlipValue.setAlignment(Pos.CENTER);
            Label overallValue = new Label(forecasts.get(time).getOverAllcondition());
            overallValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            overallValue.setAlignment(Pos.CENTER);
            forecastGrid.addRow(row_count, timeValue, precipitationValue, 
                    winterSlipValue, overallValue);
            row_count++;
        }
        
        forecastView.getChildren().addAll(tableTitle, forecastGrid);
        
        return forecastView;
    }
}
