/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataQuery extends DataQuery {
    
    private ArrayList<String> selectedTasks;
    private ArrayList<String> selectedForecasts;
    private String forecastTime;
    
    RoadDataQuery(String dataType) {
        super(dataType);
        this.selectedTasks = new ArrayList<>();
        this.selectedForecasts = new ArrayList<>();
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

    @Override
    public void testPrint() {
        System.out.println(super.location + ": " + super.timelineStart[0] + " "
            + super.timelineStart[1] + " - " + super.timelineEnd[0] + " "
            + super.timelineEnd[1]);
        System.out.println(this.selectedTasks);
        System.out.println(this.selectedForecasts);
        System.out.println(this.forecastTime);
    }
}
