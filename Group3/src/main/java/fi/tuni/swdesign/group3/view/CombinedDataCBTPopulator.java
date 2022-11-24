/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Lauri
 */
public class CombinedDataCBTPopulator extends CheckBoxTreePopulator {

    CombinedDataCBTPopulator(MainView mainView) {
        super(mainView);
    }
    
    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        RoadDataCBTPopulator subRoadDataCBTP = (RoadDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, "Road data");
        WeatherDataCBTPopulator subWeatherDataCBTP = (WeatherDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, "Weather data");
        subRoadDataCBTP.populateCheckBoxTree(checkBoxTree);
        subWeatherDataCBTP.populateCheckBoxTree(checkBoxTree);
    }
    
}
