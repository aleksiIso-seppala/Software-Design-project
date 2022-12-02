package fi.tuni.swdesign.group3.gui.view;

import fi.tuni.swdesign.group3.classes.RoadTrafficData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
    private final RoadTrafficData data;
    /**
     * An ArrayList which contains the names of the maintenance tasks the user
     * has selected to be visualized.
     */
    
    private final RoadDataQuery query;

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
     * A getter-method for the RoadTrafficData to be visualized.
     * @return the RoadTrafficData to be visualized.
     */
    public RoadTrafficData getData() {
        return data;
    }

    /**
     * A getter-method for the RoadDataQuery associated with the data.
     * @return the RoadDataQuery associated with the data.
     */
    public RoadDataQuery getQuery() {
        return query;
    }
    
    
    
    /**
     * A method for visualizing the data. Calls appropriate methods for visualizing
     * different types of data. Overrides the abstract function of base class
     * DataVisualizer.
     * @param chartTabPane the TabPane in which the Tabs containing the visualized
     * data will be added.
     */
    @Override
    public void visualizeData(TabPane chartTabPane) {
        if (!this.query.getSelectedTasks().isEmpty()) {
            Tab maintenanceTab = new Tab(MAINTENANCE_TASKS);
            // Visualizing the maintenance tasks.
            if (this.data.getMaintenanceTasks() != null) {
                BarChart maintenanceChart = visualizeMaintenanceTasks();
                if (maintenanceChart.getData().isEmpty()) {
                    maintenanceTab.setContent(new Label(NO_DATA_STR));
                }
                else {
                    maintenanceTab.setContent(maintenanceChart);
                }
            }
            else {
                maintenanceTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(maintenanceTab); 
        }
        if (!this.query.getSelectedForecasts().isEmpty()) {
            // Visualizing the condition forecasts.
            Tab forecastTab = new Tab (COND_FORECAST);
            if (this.data.getForecasts() != null) {
                forecastTab.setContent(visualizeConditionForecast());
            }
            else {
                forecastTab.setContent(new Label(NO_DATA_STR));
            }
            chartTabPane.getTabs().add(forecastTab);
        }
    }
    
    /**
     * A method for visualizing the maintenance tasks using a BarChart.
     * @return BarChart in which the maintenance tasks are visualized.
     */
    private BarChart visualizeMaintenanceTasks() {
        // Initializing the BarChart.
        HashMap<String,Integer> maintenanceTasks = this.data.getMaintenanceTasks();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> maintenanceChart = 
                new BarChart<>(xAxis,yAxis);
        maintenanceChart.setTitle(MAINT_TASKS_TITLE + 
                this.data.getLocation() 
                + LINE_WITH_SPACES 
                + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        xAxis.setLabel(MAINT_TASK_AXIS_LABEL);
        yAxis.setLabel(AMOUNT_AXIS_LABEL);
        maintenanceChart.setLegendVisible(false);
        
        // Initializing the data to be visualized.
        for (String type : this.query.getSelectedTasks()) {
            String typeCapsFormat = type.replace(" ", "_").toUpperCase();
            if (this.data.getMaintenanceTasks().containsKey(typeCapsFormat)) {
                XYChart.Series series = new XYChart.Series();
                series.setName(type);
                series.getData().add(new XYChart.Data(type, maintenanceTasks.get(typeCapsFormat)));
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
        // Initializing the table.
        int col_count = 0;
        VBox forecastView = new VBox();
        forecastView.setAlignment(Pos.CENTER);
        forecastView.setSpacing(V_GAP);
        GridPane forecastGrid = new GridPane();
        forecastGrid.setAlignment(Pos.CENTER);
        forecastGrid.setGridLinesVisible(true);
        Label tableTitle = new Label(COND_FORECAST_TITLE 
                + this.data.getLocation() 
                + LINE_WITH_SPACES 
                + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        tableTitle.setAlignment(Pos.CENTER);
        
        // Initializing the forecast time column and adding it into the table.
        Label timeLabel = new Label(FORECAST_TIME_TITLE);
        timeLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        timeLabel.setAlignment(Pos.CENTER);
        String time = this.query.getForecastTime();
        Label timeValue = new Label(time);
        timeValue.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
        timeValue.setAlignment(Pos.CENTER);
        forecastGrid.addColumn(col_count, timeLabel, timeValue);
        col_count++;
        
        // Initializing the rest of the columns.
        for (String forecast : this.query.getSelectedForecasts()) {
            Label forecastLabel = new Label(forecast);
            forecastLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            forecastLabel.setAlignment(Pos.CENTER);
            String value;
            switch (forecast) {
                case PRECIPITATION_TITLE -> value = this.data.getForecasts()
                            .get(this.query.getForecastTime()).getPrecipitation();
                case WINTER_SLIP_TITLE -> {
                    if (this.data.getForecasts().get(this.query.getForecastTime())
                            .isWinterSlipperines()) {
                        value = YES;
                    }
                    else {
                        value = NO;
                    }
                }
                case OVERALL_COND_TITLE -> value = this.data.getForecasts()
                            .get(this.query.getForecastTime()).getOverAllcondition();
                default -> { value = "";
                }
            }
            
            // Changing the format of the value from "FORECAST_VALUE" to
            // "Forecast value".
            if (!value.equals("")) {
                value = value.substring(0,1) + value.substring(1).replace("_", " ").toLowerCase();
            }
            
            // Adding the column into the table.
            Label valueLabel = new Label(value);
            valueLabel.setPrefSize(GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
            valueLabel.setAlignment(Pos.CENTER);
            forecastGrid.addColumn(col_count, forecastLabel, valueLabel);
            col_count++;
        }
        forecastView.getChildren().addAll(tableTitle, forecastGrid);
        return forecastView;
    }
}
