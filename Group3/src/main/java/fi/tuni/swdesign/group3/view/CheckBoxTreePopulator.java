/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lauri Puoskari
 */
public class CheckBoxTreePopulator {
    
    private static final int SHORT_H_GAP = 10;
    
    CheckBoxTreePopulator() {
    }
    
    public void populateCheckBoxTree(TreeView checkBoxTree, String tabName) {
        TreeItem root = new TreeItem();
        checkBoxTree.setRoot(root);
        checkBoxTree.setShowRoot(false);
        if (tabName.equals("Road data") | tabName.equals("Combined data")) {
            TreeItem maintenanceItem = new TreeItem("Maintenance");
            TreeItem conditionItem = new TreeItem("Condition forecast");
            root.getChildren().addAll(maintenanceItem, conditionItem);

            // Hardcode implementation for prototype.
            for (int i = 1; i < 6; i++) {
                CheckBox checkBox = new CheckBox("Maintenance task " + i);
                TreeItem checkBoxItem = new TreeItem(checkBox);
                maintenanceItem.getChildren().add(checkBoxItem);
            }

            CheckBox precipitationCheckBox = new CheckBox("Precipitation");
            TreeItem precipitationItem = new TreeItem(precipitationCheckBox);
            CheckBox slipperinessCheckBox = new CheckBox("Winter slipperiness");
            TreeItem slipperinessItem = new TreeItem(slipperinessCheckBox);
            CheckBox overallCheckBox = new CheckBox("Overall");
            TreeItem overallItem = new TreeItem(overallCheckBox);
            conditionItem.getChildren().addAll( 
                    precipitationItem, slipperinessItem, overallItem);

            TreeItem timeItem = new TreeItem("Time (hours)");

            RadioButton radioButton1 = new RadioButton("2");
            RadioButton radioButton2 = new RadioButton("4");
            RadioButton radioButton3 = new RadioButton("6");
            RadioButton radioButton4 = new RadioButton("12");
            ToggleGroup toggleGroup = new ToggleGroup();
            toggleGroup.getToggles().addAll(radioButton1, radioButton2, 
                    radioButton3, radioButton4);
            radioButton1.setSelected(true);
            HBox timeHBox = new HBox();
            timeHBox.getChildren().addAll(radioButton1, radioButton2, 
                    radioButton3, radioButton4);
            timeHBox.setSpacing(SHORT_H_GAP);
            TreeItem timeHBoxItem = new TreeItem(timeHBox);

            conditionItem.getChildren().add(timeItem);
            timeItem.getChildren().add(timeHBoxItem);
        }
        if (tabName.equals("Weather data") | tabName.equals("Combined data")) {
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
//            observedItem.getChildren().addAll(averageTempItem, maxMinTempItem, 
//                    obsTempItem, preTempItem);
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
}
