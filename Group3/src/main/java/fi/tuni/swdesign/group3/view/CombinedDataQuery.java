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
public class CombinedDataQuery extends DataQuery {

    private ArrayList<String> selectedTasks;
    private ArrayList<String> selectedForecasts;
    private String forecastTime;
    private ArrayList<String> selectedObsParams;
    private ArrayList<String> selectedPreParams;
    private ArrayList<String> selectedPerMonthParams;
    
    CombinedDataQuery(String dataType) {
        super(dataType);
        this.selectedTasks = new ArrayList<>();
        this.selectedForecasts = new ArrayList<>();
        this.selectedObsParams = new ArrayList<>();
        this.selectedPreParams = new ArrayList<>();
        this.selectedPerMonthParams = new ArrayList<>();
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
    
     public ArrayList<String> getSelectedObsParams() {
        return selectedObsParams;
    }

    public void setSelectedObsParams(ArrayList<String> selectedObsParams) {
        this.selectedObsParams = selectedObsParams;
    }

    public ArrayList<String> getSelectedPreParams() {
        return selectedPreParams;
    }

    public void setSelectedPreParams(ArrayList<String> selectedPreParams) {
        this.selectedPreParams = selectedPreParams;
    }

    public ArrayList<String> getSelectedPerMonthParams() {
        return selectedPerMonthParams;
    }

    public void setSelectedPerMonthParams(ArrayList<String> selectedPerMonthParams) {
        this.selectedPerMonthParams = selectedPerMonthParams;
    }

    @Override
    public void testPrint() {
        System.out.println(super.location + ": " + super.timelineStart[0] + " "
            + super.timelineStart[1] + " - " + super.timelineEnd[0] + " "
            + super.timelineEnd[1]);
        System.out.println(this.selectedTasks);
        System.out.println(this.selectedForecasts);
        System.out.println(this.forecastTime);
        System.out.println(this.selectedObsParams);
        System.out.println(this.selectedPreParams);
        System.out.println(this.selectedPerMonthParams);
    }
    
}
