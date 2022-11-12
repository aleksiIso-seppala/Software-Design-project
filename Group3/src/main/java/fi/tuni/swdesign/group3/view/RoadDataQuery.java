/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataQuery extends DataQuery {
    private final static Integer SELECTED = 1;
    
    private String location;
    private String timelineStart;
    private String timelineEnd;
    private ArrayList<String> selectedTasks;
    private ArrayList<String> selectedForecasts;
    private String forecastTime;
    
    RoadDataQuery(String dataType) {
        super(dataType);
        this.selectedTasks = new ArrayList<>();
        this.selectedForecasts = new ArrayList<>();
    }

    @Override
    void setParams(TreeItem root) {
        TreeItem maintenanceItem = (TreeItem) root.getChildren().get(0);
        for (int i = 0; i < maintenanceItem.getChildren().size(); i++) {
            TreeItem item = (TreeItem) maintenanceItem.getChildren().get(i);
            CheckBox checkBox = (CheckBox) item.getValue();
            if (checkBox.isSelected()) {
                this.selectedTasks.add(checkBox.getText());
            }
        }
        
        TreeItem forecastItem = (TreeItem) root.getChildren().get(1);
        for (int i = 0; i < forecastItem.getChildren().size(); i++) {
            TreeItem item = (TreeItem) forecastItem.getChildren().get(i);
            if (item.getValue().equals("Time (hours)")) {
                item.getChildren().get(0);
            }
            CheckBox checkBox = (CheckBox) item.getValue();
            if (checkBox.isSelected()) {
                this.selectedForecasts.add(checkBox.getText());
            }
        }
    }
    
    public void setSelectedTasks(ArrayList<String> selectedTasks) {
        this.selectedTasks = selectedTasks;
    }
    
    public ArrayList<String> getSelectedTasks() {
        return this.selectedTasks;
    }
    
    public void setSelectedForecasts(ArrayList<String> selectedForecasts) {
        this.selectedForecasts = selectedForecasts;
    }
    
    public ArrayList<String> getSelectedForecasts() {
        return this.selectedForecasts;
    }
    
    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }
    
    public String getForecastTime() {
        return this.forecastTime;
    }
}
