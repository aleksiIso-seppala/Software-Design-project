/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.TreeView;

/**
 * A subclass of CheckBoxTreePopulator for populating the checkbox treeview in 
 * the CombinedDataTab.
 * @author Lauri
 */
public class CombinedDataCBTPopulator extends CheckBoxTreePopulator {

    /**
     * A contructor for the class, in which the current instance of the MainView
     * is stored.
     * @param mainView the current instance of the MainView.
     */
    CombinedDataCBTPopulator(MainView mainView) {
        super(mainView);
    }
    
    /**
     * A method for populating the checkbox tree of a CombinedDataTab. Overrides
     * the abstract method of the abstract baseclass CheckBoxTreePopulator.
     * @param checkBoxTree the TreeView component of the datatab.
     */
    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        RoadDataCBTPopulator subRoadDataCBTP = (RoadDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, ROAD_DATA);
        WeatherDataCBTPopulator subWeatherDataCBTP = (WeatherDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, WEATHER_DATA);
        subRoadDataCBTP.populateCheckBoxTree(checkBoxTree);
        subWeatherDataCBTP.populateCheckBoxTree(checkBoxTree);
    }
    
}
