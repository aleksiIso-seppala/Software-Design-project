/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A subclass of an abstract base class DataVisualizer for visualizing RoadData
 * using tables and charts.
 * @author Lauri Puoskari
 */
public class RoadDataVisualizer extends DataVisualizer{
    /**
     * The data to be visualized.
     */
    private RoadTrafficData data;
    /**
     * An ArrayList which contains the names of the maintenance tasks the user
     * has selected to be visualized.
     */
    
    private RoadDataQuery query;
    
    private ArrayList<String> mTasksToVisualize;
    /**
     * An ArrayList which contains the types of the condition forecasts the user
     * has selected to be visualized.
     */
    private ArrayList<String> forecastsToVisualize;

    /**
     * A constructor in which the current instance of MainView is stored to the
     * base class and the data in this class.
     * @param mainView the current instance of MainView.
     * @param data the data to be visualized.
     */
    RoadDataVisualizer(MainView mainView, RoadTrafficData data, RoadDataQuery query) {
        super(mainView);
        this.data = data;
        this.query = query;
        
    }
    
    /**
     * A getter-method for the names of the maintenance tasks selected to be 
     * visualized by the user.
     * @return the names of the maintenance tasks selected.
     */
    public ArrayList<String> getMTasksToVisualize() {
        return this.mTasksToVisualize;
    }
    
    /**
     * A setter-method for the names of the maintenance tasks selected to be 
     * visualized by the user.
     * @param MTasks the names of the maintenance tasks selected.
     */
    public void setMTasksToVisualize(ArrayList<String> MTasks) {
        this.mTasksToVisualize = MTasks;
    }
    
    /**
     * A getter-method for the types of condition forecasts selected by the user
     * to be visualized.
     * @return the types of condition forecasts selected.
     */
    public ArrayList<String> getForecastsToVisualize() {
        return this.forecastsToVisualize;
    }
    
    /**
     * A setter-method for the types of condition forecasts selected by the user
     * to be visualized.
     * @param forecasts the types of condition forecasts selected.
     */
    public void setForecastsToVisualize(ArrayList<String> forecasts) {
        this.forecastsToVisualize = forecasts;
    }
    
    /**
     * A method for visualizing the data. Calls appropriate methods for visualizing
     * different types of data. Overrides the abstract function of base class
     * DataVisualizer.
     */
    @Override
    public void visualizeData() {
        DataTab dataTab = (DataTab) super.mainView.getTabPane().
                getSelectionModel().getSelectedItem();
        TabPane chartTabPane = dataTab.getChartTabPane();
        if (!this.query.getSelectedTasks().isEmpty()) {
            Tab maintenanceTab = new Tab(MAINTENANCE_TASKS);
            if (this.data.getMaintenanceTasks() != null) {
                maintenanceTab.setContent(visualizeMaintenanceTasks());
            }
            else {
                maintenanceTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(maintenanceTab); 
        }
        if (!this.query.getSelectedForecasts().isEmpty()) {
            Tab forecastTab = new Tab (COND_FORECAST);
            if (this.data.getForecasts() != null) {
                forecastTab.setContent(visualizeConditionForecast());
            }
            else {
                forecastTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(forecastTab);
        }
        visualizeTrafficMsgs();
        
    }
    
    /**
     * A method for visualizing the maintenance tasks using a BarChart.
     * @return BarChart in which the maintenance tasks are visualized.
     */
    private BarChart visualizeMaintenanceTasks() {
        HashMap<String,Integer> maintenanceTasks = this.data.getMaintenanceTasks();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> maintenanceChart = 
                new BarChart<>(xAxis,yAxis);
        maintenanceChart.setTitle(MAINT_TASKS_TITLE + 
                this.data.getLocation() 
                + LINE_WITH_SPACES 
                + this.query.getTimelineStart()[0]
                + " " + this.query.getTimelineStart()[1] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[0]
                + " " + this.query.getTimelineEnd()[1]);
        xAxis.setLabel(MAINT_TASK_AXIS_LABEL);
        yAxis.setLabel(AMOUNT_AXIS_LABEL);
        
        for (String type : this.query.getSelectedTasks()) {
            if (this.data.getMaintenanceTasks().containsKey(type)) {
                XYChart.Series series = new XYChart.Series();
                series.setName(type);
                series.getData().add(new XYChart.Data(type, maintenanceTasks.get(type)));
                maintenanceChart.getData().add(series);
            }
        }
        
        return maintenanceChart;
    }
    
    /**
     * A method for visualizing the condition forecasts using a table.
     * @return VBox which contains the table (GridPane) and its title.
     */
    private VBox visualizeConditionForecast() {
        HashMap<String, RoadTrafficDataForecast> forecasts = this.data.getForecasts();
        int row_count = 0;
        VBox forecastView = new VBox();
        forecastView.setAlignment(Pos.CENTER);
        forecastView.setSpacing(V_GAP);
        
        GridPane forecastGrid = new GridPane();
        forecastGrid.setAlignment(Pos.CENTER);
        forecastGrid.setGridLinesVisible(true);
        
        Label tableTitle = new Label(COND_FORECAST_TITLE 
                + this.data.getLocation() 
                + LINE_WITH_SPACES 
                + this.query.getTimelineStart()[0]
                + " " + this.query.getTimelineStart()[1] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[0]
                + " " + this.query.getTimelineEnd()[1]);
        tableTitle.setAlignment(Pos.CENTER);
        
        Label timeLabel = new Label(FORECAST_TIME_TITLE);
        timeLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        timeLabel.setAlignment(Pos.CENTER);
        Label precipitationLabel = new Label(PRECIPITATION_TITLE);
        precipitationLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        precipitationLabel.setAlignment(Pos.CENTER);
        Label winterSlipLabel = new Label(WINTER_SLIP_TITLE);
        winterSlipLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        winterSlipLabel.setAlignment(Pos.CENTER);
        Label overallLabel = new Label(OVERALL_COND_TITLE);
        overallLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        overallLabel.setAlignment(Pos.CENTER);
        
        forecastGrid.addRow(row_count, timeLabel, precipitationLabel, winterSlipLabel,
                overallLabel);
        row_count++;
        
//        Set<String> forecastTimes = forecasts.keySet();
//        for (String time : this.query) {
        String time = this.query.getForecastTime();
        Label timeValue = new Label(time);
        timeValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        timeValue.setAlignment(Pos.CENTER);
        Label precipitationValue = new Label(forecasts.get(time).getPrecipitation());
        precipitationValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        precipitationValue.setAlignment(Pos.CENTER);
        Label winterSlipValue = new Label();
        if (forecasts.get(time).isWinterSlipperines()) {
            winterSlipValue.setText(YES);
        }
        else if (this.forecastsToVisualize.contains(WINTER_SLIP_TITLE)) {
            winterSlipValue.setText(NO);
        }
        winterSlipValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        winterSlipValue.setAlignment(Pos.CENTER);
        Label overallValue = new Label(forecasts.get(time).getOverAllcondition());
        overallValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        overallValue.setAlignment(Pos.CENTER);
        forecastGrid.addRow(row_count, timeValue, precipitationValue, 
                winterSlipValue, overallValue);
        row_count++;
//        }
        
        forecastView.getChildren().addAll(tableTitle, forecastGrid);
        
        return forecastView;
    }
    
    /**
     * A method for visualizing the amount of traffic messages.
     */
    public void visualizeTrafficMsgs() {
        if (super.mainView.getTabPane().getSelectionModel().getSelectedItem()
                instanceof RoadDataTab) {
            RoadDataTab rdTab = (RoadDataTab) super.mainView.getTabPane()
                    .getSelectionModel().getSelectedItem();
            rdTab.getTrafficMsgLabel().setText(TRAFFIC_MSG_AMOUNT 
                    + this.data.getNumberOfTrafficMessages());
        }
        else if (super.mainView.getTabPane().getSelectionModel().getSelectedItem()
                instanceof CombinedDataTab) {
            CombinedDataTab cdTab = (CombinedDataTab) super.mainView.getTabPane()
                    .getSelectionModel().getSelectedItem();
            cdTab.getTrafficMsgLabel().setText(TRAFFIC_MSG_AMOUNT
                    + this.data.getNumberOfTrafficMessages());
        }
    }
}
