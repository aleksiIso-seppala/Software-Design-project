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

    private ArrayList<String> selectedTempParams;
    private ArrayList<String> selectedWindParams;
    private ArrayList<String> selectedCloudParams;
    
    WeatherDataQuery(String dataType) {
        super(dataType);
        this.selectedTempParams = new ArrayList<>();
        this.selectedWindParams = new ArrayList<>();
        this.selectedCloudParams = new ArrayList<>();
    }
    
    public void setSelectedTempParams(ArrayList<String> selectedTempParams) {
        this.selectedTempParams = selectedTempParams;
    }
    public ArrayList<String> getSelectedTempParams() {
        return this.selectedTempParams;
    }
    
    public void setSelectedWindParams(ArrayList<String> selectedWindParams) {
        this.selectedWindParams = selectedWindParams;
    }
    public ArrayList<String> getSelectedWindParams() {
        return this.selectedWindParams;
    }
    
    public void setSelectedCloudParams(ArrayList<String> selectedCloudParams) {
        this.selectedCloudParams = selectedCloudParams;
    }
    public ArrayList<String> getSelectedCloudParams() {
        return this.selectedCloudParams;
    }

    @Override
    public void testPrint() {
        System.out.println(super.location + ": " + super.timelineStart[0] + " "
            + super.timelineStart[1] + " - " + super.timelineEnd[0] + " "
            + super.timelineEnd[1]);
        System.out.println(this.selectedTempParams);
        System.out.println(this.selectedWindParams);
        System.out.println(this.selectedCloudParams);
    }
}
