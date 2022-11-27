/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * A subclass of CheckBoxTreePopulator for populating the checkbox treeview in 
 * the WeatherDataTab.
 * @author Lauri
 */
public class WeatherDataCBTPopulator extends CheckBoxTreePopulator{

    /**
     * A contructor for the class, in which the current instance of the MainView
     * is stored.
     * @param mainView the current instance of the MainView.
     */
    WeatherDataCBTPopulator(MainView mainView) {
        super(mainView);
    }
    
    /**
     * A method for populating the checkbox tree of a WeatherDataTab. Overrides
     * the abstract method of the abstract baseclass CheckBoxTreePopulator.
     * @param checkBoxTree the TreeView component of the datatab.
     */
    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = checkBoxTree.getRoot();
        TreeItem observedItem = new TreeItem(OBSERVED_VALUES);
        TreeItem predictedItem = new TreeItem(PREDICTED_VALUES);
        TreeItem perMonthItem = new TreeItem(VALUES_PER_MONTH);
        root.getChildren().addAll(observedItem, predictedItem, perMonthItem);

        CheckBox averageTempBox = new CheckBox(AVG_TEMPERATURE);
        CheckBox maxMinTempBox = new CheckBox(MAX_MIN_TEMPERATURE);
        CheckBox obsTempBox = new CheckBox(TEMPERATURE);
        CheckBox preTempBox = new CheckBox(TEMPERATURE);
        TreeItem averageTempItem = new TreeItem(averageTempBox);
        TreeItem maxMinTempItem = new TreeItem(maxMinTempBox);
        TreeItem obsTempItem = new TreeItem(obsTempBox);
        TreeItem preTempItem = new TreeItem(preTempBox);
        observedItem.getChildren().add(obsTempItem);
        predictedItem.getChildren().add(preTempItem);
        perMonthItem.getChildren().addAll(averageTempItem, maxMinTempItem);
        CheckBox obsWindBox = new CheckBox(WIND_SPEED);
        CheckBox preWindBox = new CheckBox(WIND_SPEED);
        TreeItem obsWindItem = new TreeItem(obsWindBox);
        TreeItem preWindItem = new TreeItem(preWindBox);
        observedItem.getChildren().add(obsWindItem);
        predictedItem.getChildren().add(preWindItem);

        CheckBox obsCloudBox = new CheckBox(CLOUDINESS);
        TreeItem obsCloudItem = new TreeItem(obsCloudBox);
        observedItem.getChildren().add(obsCloudItem);
        
    }
    
}
