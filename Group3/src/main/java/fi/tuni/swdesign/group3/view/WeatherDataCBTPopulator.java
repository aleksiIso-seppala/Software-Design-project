/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Lauri
 */
public class WeatherDataCBTPopulator extends CheckBoxTreePopulator{

    WeatherDataCBTPopulator(MainView mainView) {
        super(mainView);
    }
    
    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = checkBoxTree.getRoot();
        TreeItem observedItem = new TreeItem("Observed values");
        TreeItem predictedItem = new TreeItem("Predicted values");
        TreeItem perMonthItem = new TreeItem("Values per month");
        root.getChildren().addAll(observedItem, predictedItem, perMonthItem);

        CheckBox averageTempBox = new CheckBox("Average temperature");
        CheckBox maxMinTempBox = new CheckBox("Max & min temperatures");
        CheckBox obsTempBox = new CheckBox("Temperature");
        CheckBox preTempBox = new CheckBox("Temperature");
        TreeItem averageTempItem = new TreeItem(averageTempBox);
        TreeItem maxMinTempItem = new TreeItem(maxMinTempBox);
        TreeItem obsTempItem = new TreeItem(obsTempBox);
        TreeItem preTempItem = new TreeItem(preTempBox);
        observedItem.getChildren().add(obsTempItem);
        predictedItem.getChildren().add(preTempItem);
        perMonthItem.getChildren().addAll(averageTempItem, maxMinTempItem);
        CheckBox obsWindBox = new CheckBox("Wind speed");
        CheckBox preWindBox = new CheckBox("Wind speed");
        TreeItem obsWindItem = new TreeItem(obsWindBox);
        TreeItem preWindItem = new TreeItem(preWindBox);
        observedItem.getChildren().add(obsWindItem);
        predictedItem.getChildren().add(preWindItem);

        CheckBox obsCloudBox = new CheckBox("Cloudiness");
        TreeItem obsCloudItem = new TreeItem(obsCloudBox);
        observedItem.getChildren().add(obsCloudItem);
        
    }
    
}
