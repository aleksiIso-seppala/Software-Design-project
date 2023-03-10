package fi.tuni.swdesign.group3.gui.view;

import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 * A class for populating an empty DataQuery with parameters selected by the 
 * user.
 * @author Lauri Puoskari
 */
public class DataQueryPopulator {
    /**
     * A constant string representing road data.
     */
    private final static String ROAD_DATA = "Road data";
    /**
     * A constant string representing weather data.
     */
    private final static String WEATHER_DATA = "Weather data";
    /**
     * A constant string representing combined data.
     */
    private final static String COMBINED_DATA = "Combined data";
    /**
     * A constant string representing maintenance TreeItem.
     */
    private final static String MAINTENANCE = "Maintenance";
    /**
     * A constant string representing condition forecast TreeItem.
     */
    private final static String COND_FORECAST = "Condition forecast";
    /**
     * A constant string representing an empty string.
     */
    private final static String EMPTY_STR = "";
    /**
     * A constant string representing forecast time TreeItem.
     */
    private final static String FORECAST_TIME = "Time (hours)";
    /**
     * A constant string representing observed values TreeItem.
     */
    private final static String OBSERVED_VALUES = "Observed values";
    /**
     * A constant string representing predicted values TreeItem.
     */
    private final static String PREDICTED_VALUES = "Predicted values";
    /**
     * A constant string representing daily values of month TreeItem.
     */
    private final static String PER_MONTH_VALUES = "Values per month";
    /**
     * A constant string representing the maintenance task checkbox "All";
     */
    private final static String ALL = "All";
    
    /**
     * An empty constructor.
     */
    DataQueryPopulator() {
    }
    
    /**
     * A method for populating an empty DataQuery with parameters selected by 
     * the user. Determines the data type of the query and forwards the task
     * to an appropriate method.
     * @param dataQuery the DataQuery to be populated.
     * @param location the location selected by the user.
     * @param startTime an string array in which the first element is the time
     * and second element is the date of the start of the timeline.
     * @param endTime an string array in which the first element is the time
     * and second element is the date of the end of the timeline.
     * @param root the root element of the CheckBoxTree of the DataTab which the
     * user used.
     */
    public static void populateDataQuery(DataQuery dataQuery, String location, 
            String[] startTime, String[] endTime, TreeItem root) {
        dataQuery.setLocation(location);
        dataQuery.setTimelineStart(startTime);
        dataQuery.setTimelineEnd(endTime);
        switch (dataQuery.getDataType()) {
            case ROAD_DATA -> populateRoadDataQuery(dataQuery, root);
            case WEATHER_DATA -> populateWeatherDataQuery(dataQuery, root);
            case COMBINED_DATA -> populateCombinedDataQuery(dataQuery, root);
            default -> {}
        }
    }
    
    /**
     * A method for populating an empty RoadDataQuery with parameters selected by 
     * the user.
     * @param dataQuery the DataQuery to be populated.
     * @param root the root element of the CheckBoxTree of the DataTab which the
     * user used.
     */
    private static void populateRoadDataQuery(DataQuery dataQuery, TreeItem root) {
        RoadDataQuery query = (RoadDataQuery) dataQuery;
        // Checking the CheckBoxTree for selected parameters.
        for (var object : root.getChildren()) {
            TreeItem treeItem = (TreeItem) object;
            if (treeItem.getValue().equals(MAINTENANCE)) {
                boolean isAllSelected = false;
                ArrayList<String> selectedTasks = new ArrayList<>();
                // Checking the maintenance tasks.
                for (var taskObject : treeItem.getChildren()) {
                    TreeItem taskItem = (TreeItem) taskObject;
                    CheckBox taskBox = (CheckBox) taskItem.getValue();
                    if (taskBox.getText().equals(ALL) & taskBox.isSelected()) {
                        isAllSelected = true;
                    }
                    if (taskBox.isSelected() | isAllSelected) {
                        selectedTasks.add(taskBox.getText());
                    }
                }
                query.setSelectedTasks(selectedTasks);
            }
            else if (treeItem.getValue().equals(COND_FORECAST)) {
                ArrayList<String> selectedForecasts = new ArrayList<>();
                String forecastTime = EMPTY_STR;
                for (var forecastObject : treeItem.getChildren()) {
                    // Checking the condition forecasts.
                    TreeItem forecastItem = (TreeItem) forecastObject;
                    if (forecastItem.getValue().equals(FORECAST_TIME)) {
                        TreeItem fcTimeItem = 
                                (TreeItem) forecastItem.getChildren().get(0);
                        HBox hBox = (HBox) fcTimeItem.getValue();
                        for (var node : hBox.getChildren()) {
                            RadioButton timeButton = (RadioButton) node;
                            if (timeButton.isSelected()) {
                                forecastTime = timeButton.getText();
                            }
                        }
                    }
                    else {
                        CheckBox forecastBox = (CheckBox) forecastItem.getValue();
                        if (forecastBox.isSelected()) {
                            selectedForecasts.add(forecastBox.getText());
                        }
                    }
                }
                query.setSelectedForecasts(selectedForecasts);
                query.setForecastTime(forecastTime);
            }
        }
    }
    
    /**
     * A method for populating an empty WeatherDataQuery with parameters selected by 
     * the user.
     * @param dataQuery the DataQuery to be populated.
     * @param root the root element of the CheckBoxTree of the DataTab which the
     * user used.
     */
    private static void populateWeatherDataQuery(DataQuery dataQuery, TreeItem root) {
        WeatherDataQuery query = (WeatherDataQuery) dataQuery;
        // Checking the CheckBoxTree for selected parameters.
        for (var object : root.getChildren()) {
            TreeItem treeItem = (TreeItem) object;
            if (treeItem.getValue().equals(OBSERVED_VALUES)) {
                ArrayList<String> selectedObsParams = new ArrayList<>();
                // Checking the observed values.
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedObsParams.add(paramBox.getText());
                    }
                }
                query.setSelectedObsParams(selectedObsParams);
            }
            else if (treeItem.getValue().equals(PREDICTED_VALUES)) {
                ArrayList<String> selectedPreParams = new ArrayList<>();
                // Checking the predicted values.
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedPreParams.add(paramBox.getText());
                    }
                }
                query.setSelectedPreParams(selectedPreParams);
            }
            else if (treeItem.getValue().equals(PER_MONTH_VALUES)) {
                ArrayList<String> selectedPerMonthParams = new ArrayList<>();
                // Checking the values per month.
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedPerMonthParams.add(paramBox.getText());
                    }
                }
                query.setSelectedPerMonthParams(selectedPerMonthParams);
            }
        }
    }
    
    /**
     * A method for populating an empty CombinedDataQuery with parameters 
     * selected by the user.
     * @param dataQuery the DataQuery to be populated.
     * @param root the root element of the CheckBoxTree of the DataTab which the
     * user used.
     */
    private static void populateCombinedDataQuery(DataQuery dataQuery, TreeItem root) {
        CombinedDataQuery query = (CombinedDataQuery) dataQuery;
        // Initializing subDataQuerys.
        RoadDataQuery subRoadDataQuery = (RoadDataQuery) 
                DataQueryFactory.makeDataQuery(ROAD_DATA);
        WeatherDataQuery subWeatherDataQuery = (WeatherDataQuery) 
                DataQueryFactory.makeDataQuery(WEATHER_DATA);
        // Populating subDataQuerys.
        populateRoadDataQuery(subRoadDataQuery, root);
        populateWeatherDataQuery(subWeatherDataQuery, root);
        
        query.setSubRoadDQ(subRoadDataQuery);
        query.setSubWeatherDQ(subWeatherDataQuery);
        
        // Storing the location/time parameters into the subDataQuerys as well.
        query.getSubRoadDQ().setLocation(query.getLocation());
        query.getSubRoadDQ().setTimelineStart(query.getTimelineStart());
        query.getSubRoadDQ().setTimelineEnd(query.getTimelineEnd());
        
        query.getSubWeatherDQ().setLocation(query.getLocation());
        query.getSubWeatherDQ().setTimelineStart(query.getTimelineStart());
        query.getSubWeatherDQ().setTimelineEnd(query.getTimelineEnd());
    }
}