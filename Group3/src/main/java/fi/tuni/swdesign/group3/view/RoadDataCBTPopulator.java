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
 * @author Lauri
 */
public class RoadDataCBTPopulator extends CheckBoxTreePopulator {
    
    RoadDataCBTPopulator(MainView mainView) {
        super(mainView);
    }

    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = checkBoxTree.getRoot();
        TreeItem maintenanceItem = new TreeItem("Maintenance");
        TreeItem conditionItem = new TreeItem("Condition forecast");
        root.getChildren().addAll(maintenanceItem, conditionItem);
        for (var taskType : super.mainView.getViewModel().getMaintenanceTaskTypes()) {
            CheckBox checkBox = new CheckBox(taskType);
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
        timeHBox.setSpacing(super.SHORT_H_GAP);
        TreeItem timeHBoxItem = new TreeItem(timeHBox);
        
        conditionItem.getChildren().add(timeItem);
        timeItem.getChildren().add(timeHBoxItem);
    }
}
