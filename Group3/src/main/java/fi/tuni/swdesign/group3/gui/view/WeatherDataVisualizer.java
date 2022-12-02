package fi.tuni.swdesign.group3.gui.view;

import fi.tuni.swdesign.group3.classes.RoadWeatherData;
import java.util.TreeMap;
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
    private final RoadWeatherData data;
    /**
     * The DataQuery associated with the data to be visualized.
     */
    private final WeatherDataQuery query;
    
    
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
    
    public RoadWeatherData getData() {
        return data;
    }

    public WeatherDataQuery getQuery() {
        return query;
    }
    
    
    
    /**
     * A method for visualizing the data.Calls appropriate methods for visualizing
     * different types of data. Overrides the abstract function of base class
     * DataVisualizer.
     * @param chartTabPane The TabPane in which the Tabs with the visualized 
     * data will be added.
     */
    @Override
    public void visualizeData(TabPane chartTabPane) {
        if (!this.query.getSelectedObsParams().isEmpty()) {
            // Visualizing the Observed values.
            Tab observedTab = new Tab(OBSERVED_VALUES);
            observedTab.setContent(visualizeObservedValues());
            chartTabPane.getTabs().add(observedTab);
        }
        if (!this.query.getSelectedPreParams().isEmpty()) {
            // Visualizing the Predicted values.
            Tab predictedTab = new Tab(PREDICTED_VALUES);
            predictedTab.setContent(visualizePredictedValues());
            chartTabPane.getTabs().add(predictedTab);
        }
        if (!this.query.getSelectedPerMonthParams().isEmpty()) {
            // Visualizing the Values per month.
            Tab perMonthTab = new Tab("Daily values per month");
            perMonthTab.setContent(visualizePerMonthValues());
            chartTabPane.getTabs().add(perMonthTab);
        }
    }
    
    /**
     * A method for visualizing the observed values using a table.
     * @return VBox which contains the table (GridPane) and its title.
     */
    private VBox visualizeObservedValues() {
        // Initializing the table.
        VBox observedView = new VBox();
        observedView.setAlignment(Pos.CENTER);
        observedView.setSpacing(V_GAP);
        GridPane observedGrid = new GridPane();
        observedGrid.setAlignment(Pos.CENTER);
        observedGrid.setGridLinesVisible(true);
        Label tableTitle = new Label(OBS_VALUES_TITLE 
                + this.data.getLocation() 
                + COLON + SPACE
                + this.query.getTimelineStart()[TIME_I]
                + COMMA + SPACE 
                + this.query.getTimelineStart()[DATE_I] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[TIME_I]
                + COMMA + SPACE 
                + this.query.getTimelineEnd()[DATE_I]);
        tableTitle.setAlignment(Pos.CENTER);
        int col_count = 0;
        
        // Initializing the columns.
        for (String obsType : this.query.getSelectedObsParams()) {
            Label obsTypeLabel = new Label(obsType);
            obsTypeLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            obsTypeLabel.setAlignment(Pos.CENTER);
            Label obsValueLabel = new Label();
            obsValueLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            obsValueLabel.setAlignment(Pos.CENTER);
            switch (obsType) {
                case TEMPERATURE -> obsValueLabel
                        .setText(Float.toString(this.data.getTemperature()));
                case WIND_SPEED -> obsValueLabel
                        .setText(Float.toString(this.data.getWind()));
                case CLOUDINESS -> obsValueLabel
                        .setText(Float.toString(this.data.getCloudiness()));
                default -> {
                }
            }
            observedGrid.addColumn(col_count, obsTypeLabel, obsValueLabel);
            col_count++;
        }
        
        observedView.getChildren().addAll(tableTitle, observedGrid);
        return observedView;
    }
    
    /**
     * A method for visualizing the predicted values using a LineChart.
     * @return LineChart in which the data is visualized.
     */
    private LineChart visualizePredictedValues() {
        // Initializing the LineChart.
        TreeMap<String, RoadWeatherData> forecasts = this.data.getForecasts();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart predictedChart = new LineChart(xAxis,yAxis);
        predictedChart.setTitle(PRE_VALUES_TITLE
                + this.data.getLocation() 
                + COLON + SPACE
                + this.query.getTimelineStart()[TIME_I]
                + COMMA + SPACE
                + this.query.getTimelineStart()[DATE_I] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[TIME_I]
                + COMMA + SPACE 
                + this.query.getTimelineEnd()[DATE_I]);
        xAxis.setLabel(FORECAST_TIME_TITLE);
        yAxis.setLabel(WEATHER_VALUE_AXIS_LABEL);
        predictedChart.setCreateSymbols(false);
            
        // Initializing the data and adding it into the chart.
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
        // Initializing the AreaChart.
        TreeMap<String, Float[]> dailyValues = this.data.getMonthylAverage();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final AreaChart perMonthChart = new AreaChart(xAxis, yAxis);
        perMonthChart.setTitle(DAILY_VALUE_TITLE 
                + this.query.getLocation()
                + COLON + SPACE
                + this.query.getTimelineStart()[TIME_I]
                + COMMA + SPACE 
                + this.query.getTimelineStart()[DATE_I] 
                + LINE_WITH_SPACES 
                + this.query.getTimelineEnd()[TIME_I]
                + COMMA + SPACE
                + this.query.getTimelineEnd()[DATE_I]);
        xAxis.setLabel(DATE_AXIS_LABEL);
        yAxis.setLabel(TEMPERATURE_TITLE);
        perMonthChart.setCreateSymbols(false);
        
        // Initializing and adding the data into the chart.
        XYChart.Series seriesAvg = new XYChart.Series();
        XYChart.Series seriesMin = new XYChart.Series();
        XYChart.Series seriesMax = new XYChart.Series();
        seriesAvg.setName(AVG_TEMPERATURE);
        seriesMax.setName("Max temperature");
        seriesMin.setName("Min temperature");
        for (String type : this.query.getSelectedPerMonthParams()) {
            for (String date : dailyValues.keySet()) {
                
                if (type.equals(AVG_TEMPERATURE) & !dailyValues.get(date)[0].isNaN()) {
                    seriesAvg.getData().add(new XYChart.Data(date, dailyValues.get(date)[0]));
                }
                else if (type.equals(MAX_MIN_TEMPERATURE)
                        & !dailyValues.get(date)[1].isNaN()
                        & !dailyValues.get(date)[2].isNaN()) {
                    seriesMax.getData().add(new XYChart.Data(date, dailyValues.get(date)[2]));
                    seriesMin.getData().add(new XYChart.Data(date, dailyValues.get(date)[1]));
                }
            }
            if (type.equals(AVG_TEMPERATURE)) {
                perMonthChart.getData().add(seriesAvg);
            }
            else if (type.equals(MAX_MIN_TEMPERATURE)) {
                perMonthChart.getData().add(seriesMin);
                perMonthChart.getData().add(seriesMax);
            }
        }
        return perMonthChart;
    }
}
