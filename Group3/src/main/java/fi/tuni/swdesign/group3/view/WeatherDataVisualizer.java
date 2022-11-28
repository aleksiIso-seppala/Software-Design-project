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
import javafx.geometry.Pos;
import javafx.scene.chart.AreaChart;
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
 * A subclass of an abstract base class DataVisualizer for visualizing WeatherData
 * using tables and charts.
 * @author Lauri Puoskari
 */
public class WeatherDataVisualizer extends DataVisualizer{
    /**
     * The data to be visualized.
     */
    private RoadWeatherData data;
    /**
     * An ArrayList which contains the types of observed values the user
     * has selected to be visualized.
     */
    
    private WeatherDataQuery query;
    
    private ArrayList<String> obsTypesToVisualize;
    /**
     * An ArrayList which contains the types of predicted values the user
     * has selected to be visualized.
     */
    private ArrayList<String> preTypesToVisualize;
    /**
     * An ArrayList which contains the types of daily values per month the user
     * has selected to be visualized.
     */
    private ArrayList<String> perMonthTypesToVisualize;
    
    /**
     * A constructor in which the current instance of MainView is stored to the
     * base class and the data in this class.
     * @param mainView the current instance of MainView.
     * @param data the data to be visualized.
     */
    WeatherDataVisualizer(MainView mainView, RoadWeatherData data, WeatherDataQuery query) {
        super(mainView);
        this.data = data;
        this.query = query;
    }
    
    /**
     * A getter-method for the types of observed values selected by the user to
     * be visualized.
     * @return the types of observed values selected.
     */
    public ArrayList<String> getObsTypesToVisualize() {
        return this.obsTypesToVisualize;
    }
    
    /**
     * A setter-method for the types of observed values selected by the user to
     * be visualized.
     * @param values the types of observed values selected.
     */
    public void setObsTypesToVisualize(ArrayList<String> values) {
        this.obsTypesToVisualize = values;
    }
    
    /**
     * A getter-method for the types of predicted values selected by the user to
     * be visualized.
     * @return the types of predicted values selected.
     */
    public ArrayList<String> getPreTypesToVisualize() {
        return this.preTypesToVisualize;
    }
    
    /**
     * A setter-method for the types of predicted values selected by the user to
     * be visualized.
     * @param forecasts the types of predicted values selected.
     */
    public void setPreTypesToVisualize(ArrayList<String> forecasts) {
        this.preTypesToVisualize = forecasts;
    }
    
    /**
     * A getter-method for the types ofdaily values per month selected by the 
     * user to be visualized.
     * @return the types ofdaily values per month selected.
     */
    public ArrayList<String> getPerMonthTypesToVisualize() {
        return perMonthTypesToVisualize;
    }
    
    /**
     * A getter-method for the types ofdaily values per month selected by the 
     * user to be visualized.
     * @param perMonthTypesToVisualize the types ofdaily values per month selected
     */
    public void setPerMonthTypesToVisualize(ArrayList<String> perMonthTypesToVisualize) {
        this.perMonthTypesToVisualize = perMonthTypesToVisualize;
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
        if (!this.query.getSelectedObsParams().isEmpty()) {
            Tab observedTab = new Tab(OBSERVED_VALUES);
            observedTab.setContent(visualizeObservedValues());
            chartTabPane.getTabs().add(observedTab);
        }
        if (!this.query.getSelectedPreParams().isEmpty()) {
            Tab predictedTab = new Tab(PREDICTED_VALUES);
            predictedTab.setContent(visualizePredictedValues());
            chartTabPane.getTabs().add(predictedTab);
        }
    }
    
    /**
     * A method for visualizing the observed values using a table.
     * @return VBox which contains the table (GridPane) and its title.
     */
    private VBox visualizeObservedValues() {
        VBox observedView = new VBox();
        observedView.setAlignment(Pos.CENTER);
        observedView.setSpacing(V_GAP);
        
        GridPane observedGrid = new GridPane();
        observedGrid.setAlignment(Pos.CENTER);
        observedGrid.setGridLinesVisible(true);
        
        Label tableTitle = new Label(OBS_VALUES_TITLE 
                + this.data.getLocation() 
                + LINE_WITH_SPACES 
                + this.query.getTimelineStart()[0]
                + " " + this.query.getTimelineStart()[1] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[0]
                + " " + this.query.getTimelineEnd()[1]);
        tableTitle.setAlignment(Pos.CENTER);
        
        Label temperatureLabel = new Label(TEMPERATURE_TITLE);
        temperatureLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        temperatureLabel.setAlignment(Pos.CENTER);
        Label windLabel = new Label(WIND_SPEED_TITLE);
        windLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        windLabel.setAlignment(Pos.CENTER);
        Label cloudLabel = new Label(CLOUDINESS_TITLE);
        cloudLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        cloudLabel.setAlignment(Pos.CENTER);
        
        observedGrid.addRow(0, temperatureLabel, windLabel,
                cloudLabel);
        
        Label temperatureValue = new Label();
        if (this.query.getSelectedObsParams().contains(TEMPERATURE)) {
            temperatureValue.setText(Float.toString(this.data.getTemperature()));
        }
        temperatureValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        temperatureValue.setAlignment(Pos.CENTER);
        
        Label windValue = new Label();
        if (this.query.getSelectedObsParams().contains(WIND_SPEED)) {
            windValue.setText(Float.toString(this.data.getWind()));
        }
        windValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        windValue.setAlignment(Pos.CENTER);
        
        Label cloudValue = new Label();
        if (this.query.getSelectedObsParams().contains(CLOUDINESS)) {
            cloudValue.setText(Float.toString(this.data.getCloudiness()));
        }
        cloudValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        cloudValue.setAlignment(Pos.CENTER);
        
        observedGrid.addRow(1, temperatureValue, windValue, cloudValue);
        observedView.getChildren().addAll(tableTitle, observedGrid);
        return observedView;
    }
    
    /**
     * A method for visualizing the predicted values using a LineChart.
     * @return LineChart in which the data is visualized.
     */
    private LineChart visualizePredictedValues() {
        HashMap<String, RoadWeatherData> forecasts = this.data.getForecasts();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart predictedChart = new LineChart(xAxis,yAxis);
        predictedChart.setTitle(PRE_VALUES_TITLE
                + this.data.getLocation() 
                + LINE_WITH_SPACES 
                + this.query.getTimelineStart()[0]
                + " " + this.query.getTimelineStart()[1] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[0]
                + " " + this.query.getTimelineEnd()[1]);
        xAxis.setLabel(FORECAST_TIME_TITLE);
        yAxis.setLabel(WEATHER_VALUE_AXIS_LABEL);
        predictedChart.setCreateSymbols(false);
            
        for (String type : this.query.getSelectedPreParams()) {
            XYChart.Series series = new XYChart.Series();
            series.setName(type);
            for (String time : forecasts.keySet()) {
                if (type.equals(TEMPERATURE)) {
                    series.getData().add(new XYChart.Data(time, forecasts.get(time)
                            .getTemperature()));
                }
                else if (type.equals(WIND_SPEED)) {
                    series.getData().add(new XYChart.Data(time, forecasts.get(time)
                            .getWind()));
                }
            }
            predictedChart.getData().add(series);
        }
        
        return predictedChart;
    }
    
    /**
     * A method for visualizing the daily values per month using a AreaChart.
     * @return AreaChart in which the data is visualized.
     */
    private AreaChart visualizePerMonthValues() {
        HashMap<String, RoadWeatherData> dailyValues = new HashMap<>();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart perMonthChart = new AreaChart(xAxis, yAxis);
        perMonthChart.setTitle(DAILY_VALUE_TITLE 
                + this.data.getLocation() 
                + LINE_WITH_SPACES 
                + LINE_WITH_SPACES 
                + this.query.getTimelineStart()[0]
                + " " + this.query.getTimelineStart()[1] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[0]
                + " " + this.query.getTimelineEnd()[1]);
        xAxis.setLabel(DATE_AXIS_LABEL);
        yAxis.setLabel(TEMPERATURE_TITLE);
        perMonthChart.setCreateSymbols(false);
        
        for (String type : this.query.getSelectedPreParams()) {
            XYChart.Series series = new XYChart.Series();
            series.setName(type);
            for (String date : dailyValues.keySet()) {
                if (type.equals(AVG_TEMPERATURE)) {
                    series.getData().add(new XYChart.Data(date, dailyValues.get(date)
                            .getAVGTemperature()));
                }
                else if (type.equals(MAX_MIN_TEMPERATURE)) {
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
