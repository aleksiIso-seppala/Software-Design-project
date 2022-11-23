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
public class WeatherDataQuery extends DataQuery{
//
//    private ArrayList<String> selectedTempParams;
//    private ArrayList<String> selectedWindParams;
//    private ArrayList<String> selectedCloudParams;
    private ArrayList<String> selectedObsParams;
    private ArrayList<String> selectedPreParams;
    private ArrayList<String> selectedPerMonthParams;
    
    WeatherDataQuery(String dataType) {
        super(dataType);
        this.selectedObsParams = new ArrayList<>();
        this.selectedPreParams = new ArrayList<>();
        this.selectedPerMonthParams = new ArrayList<>();
    }
    
    public void setSelectedObsParams(ArrayList<String> selectedObsParams) {
        this.selectedObsParams = selectedObsParams;
    }
    public ArrayList<String> getSelectedObsParams() {
        return this.selectedObsParams;
    }
    
    public void setSelectedPreParams(ArrayList<String> selectedPreParams) {
        this.selectedPreParams = selectedPreParams;
    }
    public ArrayList<String> getSelectedPreParams() {
        return this.selectedPreParams;
    }
    
    public void setSelectedPerMonthParams(ArrayList<String> selectedPerMonthParams) {
        this.selectedPerMonthParams = selectedPerMonthParams;
    }
    public ArrayList<String> getSelectedPerMonthParams() {
        return this.selectedPerMonthParams;
    }

    @Override
    public void testPrint() {
        System.out.println(super.location + ": " + super.timelineStart[0] + " "
            + super.timelineStart[1] + " - " + super.timelineEnd[0] + " "
            + super.timelineEnd[1]);
        System.out.println(this.selectedObsParams);
        System.out.println(this.selectedPreParams);
        System.out.println(this.selectedPerMonthParams);
    }
}
