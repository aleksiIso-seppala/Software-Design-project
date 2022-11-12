/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lauri Puoskari
 */
public class DataQueryPopulator {
    
    DataQueryPopulator() {
    }
    
    public void populateDataQuery(DataQuery dataQuery, String location, 
            String[] startTime, String[] endTime, TreeItem root) {
        dataQuery.setLocation(location);
        dataQuery.setTimelineStart(startTime);
        dataQuery.setTimelineEnd(endTime);
        switch (dataQuery.getDataType()) {
            case "Road data" -> populateRoadDataQuery(dataQuery, root);
            case "Weather data" -> populateWeatherDataQuery(dataQuery, root);
            default -> populateCombinedDataQuery(dataQuery, root);
        };
    }
    
    public void populateRoadDataQuery(DataQuery dataQuery, TreeItem root) {
        RoadDataQuery query = (RoadDataQuery) dataQuery;
        
        for (var object : root.getChildren()) {
            TreeItem treeItem = (TreeItem) object;
            if (treeItem.getValue().equals("Maintenance")) {
                ArrayList<String> selectedTasks = new ArrayList<>();
                for (var taskObject : treeItem.getChildren()) {
                    TreeItem taskItem = (TreeItem) taskObject;
                    CheckBox taskBox = (CheckBox) taskItem.getValue();
                    if (taskBox.isSelected()) {
                        selectedTasks.add(taskBox.getText());
                    }
                }
                query.setSelectedTasks(selectedTasks);
            }
            else if (treeItem.getValue().equals("Condition forecast")) {
                ArrayList<String> selectedForecasts = new ArrayList<>();
                String forecastTime = "";
                for (var forecastObject : treeItem.getChildren()) {
                    TreeItem forecastItem = (TreeItem) forecastObject;
                    if (forecastItem.getValue().equals("Time (hours)")) {
                        TreeItem fcTimeItem = (TreeItem) forecastItem.getChildren().get(0);
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
    
    public void populateWeatherDataQuery(DataQuery dataQuery, TreeItem root) {
        WeatherDataQuery query = (WeatherDataQuery) dataQuery;
        
        for (var object : root.getChildren()) {
            TreeItem treeItem = (TreeItem) object;
            if (treeItem.getValue().equals("Temperature")) {
                ArrayList<String> selectedTempParams = new ArrayList<>();
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedTempParams.add(paramBox.getText());
                    }
                }
                query.setSelectedTempParams(selectedTempParams);
            }
            else if (treeItem.getValue().equals("Wind")) {
                ArrayList<String> selectedWindParams = new ArrayList<>();
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedWindParams.add(paramBox.getText());
                    }
                }
                query.setSelectedWindParams(selectedWindParams);
            }
            else if (treeItem.getValue().equals("Cloudiness")) {
                ArrayList<String> selectedCloudParams = new ArrayList<>();
                for (var paramObject : treeItem.getChildren()) {
                    TreeItem paramItem = (TreeItem) paramObject;
                    CheckBox paramBox = (CheckBox) paramItem.getValue();
                    if (paramBox.isSelected()) {
                        selectedCloudParams.add(paramBox.getText());
                    }
                }
                query.setSelectedCloudParams(selectedCloudParams);
            }
        }
    }
    
    public void populateCombinedDataQuery(DataQuery dataQuery, TreeItem root) {
        CombinedDataQuery query = (CombinedDataQuery) dataQuery;
        
        DataQueryFactory dqFactory = new DataQueryFactory();
        RoadDataQuery subRoadDataQuery = (RoadDataQuery) 
                dqFactory.makeDataQuery("Road data");
        WeatherDataQuery subWeatherDataQuery = (WeatherDataQuery) 
                dqFactory.makeDataQuery("Weather data");
        
        populateRoadDataQuery(subRoadDataQuery, root);
        populateWeatherDataQuery(subWeatherDataQuery, root);
        
        query.setSelectedTasks(subRoadDataQuery.getSelectedTasks());
        query.setSelectedForecasts(subRoadDataQuery.getSelectedForecasts());
        query.setForecastTime(subRoadDataQuery.getForecastTime());
        
        query.setSelectedTempParams(subWeatherDataQuery.getSelectedTempParams());
        query.setSelectedWindParams(subWeatherDataQuery.getSelectedWindParams());
        query.setSelectedCloudParams(subWeatherDataQuery.getSelectedWindParams());
    }
}
