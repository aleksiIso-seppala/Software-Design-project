/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;

/**
 * A class for storing the CombinedData parameters given by the user into an
 * object.
 * @author Lauri Puoskari
 */
public class CombinedDataQuery extends DataQuery {

    /**
     * ArrayList of the names of the maintenance tasks selected by the user.
     */
    private ArrayList<String> selectedTasks;
    /**
     * ArrayList of the types of condition forecasts selected by the user.
     */
    private ArrayList<String> selectedForecasts;
    /**
     * The amount of hours of the forecast selected by the user as a string.
     */
    private String forecastTime;
    /**
     * ArrayList of the types of the observed values chosen by the user.
     */
    private ArrayList<String> selectedObsParams;
    /**
     * ArrayList of the types of the predicted values chosen by the user.
     */
    private ArrayList<String> selectedPreParams;
    /**
     * ArrayList of the types of the daily values per month chosen by the user.
     */
    private ArrayList<String> selectedPerMonthParams;
    
    /**
     * A constructor in which the data type of the query is stored into the 
     * base class and the data structures are intialized.
     * @param dataType the data type of the DataQuery.
     */
    CombinedDataQuery(String dataType) {
        super(dataType);
        this.selectedTasks = new ArrayList<>();
        this.selectedForecasts = new ArrayList<>();
        this.selectedObsParams = new ArrayList<>();
        this.selectedPreParams = new ArrayList<>();
        this.selectedPerMonthParams = new ArrayList<>();
    }

    /**
     * A setter-method for the maintenance tasks selected by the user.
     * @param selectedTasks an ArrayList of the names of the maintenance tasks.
     */
    public void setSelectedTasks(ArrayList<String> selectedTasks) {
        this.selectedTasks = selectedTasks;
    }
    
    /**
     * A getter-method for the maintenance tasks selected by the user.
     * @return an ArrayList of the names of the maintenance tasks.
     */
    public ArrayList<String> getSelectedTasks() {
        return this.selectedTasks;
    }
    
    /**
     * A setter-method for the condition forecasts selected by the user.
     * @param selectedForecasts an ArrayList of the types of condition forecasts.
     */
    public void setSelectedForecasts(ArrayList<String> selectedForecasts) {
        this.selectedForecasts = selectedForecasts;
    }
    
    /**
     * A getter-method for the condition forecasts selected by the user.
     * @return an ArrayList of the types of condition forecasts.
     */
    public ArrayList<String> getSelectedForecasts() {
        return this.selectedForecasts;
    }
    
    /**
     * A setter-method for the forecast time selected by the user.
     * @param forecastTime the amount of forecast hours as a string.
     */
    public void setForecastTime(String forecastTime) {
        this.forecastTime = forecastTime;
    }
    
    /**
     * A getter-method for the forecast time selected by the user.
     * @return the amount of forecast hours as a string.
     */
    public String getForecastTime() {
        return this.forecastTime;
    }
    
    /**
     * A setter-method for the types of observed values selected by the user.
     * @param selectedObsParams an ArrayList of the types of observed values.
     */
    public void setSelectedObsParams(ArrayList<String> selectedObsParams) {
        this.selectedObsParams = selectedObsParams;
    }
    /**
     * A getter-method for the types of observed values selected by the user.
     * @return an ArrayList of the types of observed values.
     */
    public ArrayList<String> getSelectedObsParams() {
        return this.selectedObsParams;
    }
    
    /**
     * A setter-method for the types of predicted values selected by the user.
     * @param selectedPreParams an ArrayList of the types of predicted values.
     */
    public void setSelectedPreParams(ArrayList<String> selectedPreParams) {
        this.selectedPreParams = selectedPreParams;
    }
    /**
     * A getter-method for the types of predicted values selected by the user.
     * @return an ArrayList of the types of predicted values.
     */
    public ArrayList<String> getSelectedPreParams() {
        return this.selectedPreParams;
    }
    
    /**
     * A setter-method for the types of daily values per month selected by the user.
     * @param selectedPerMonthParams an ArrayList of the types of daily values per month.
     */
    public void setSelectedPerMonthParams(ArrayList<String> selectedPerMonthParams) {
        this.selectedPerMonthParams = selectedPerMonthParams;
    }
    /**
     * A getter-method for the types of daily values per month selected by the user.
     * @return an ArrayList of the types of daily values per month.
     */
    public ArrayList<String> getSelectedPerMonthParams() {
        return this.selectedPerMonthParams;
    }

    /**
     * A method for printing the contents of the query. Implements the abstract
     * method of the class DataQuery.
     */
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
