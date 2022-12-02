package fi.tuni.swdesign.group3.gui.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 * A subclass of CheckBoxTreePopulator for populating the checkbox treeview in 
 * the RoadDataTab.
 * @author Lauri Puoskari
 */
public class RoadDataCBTPopulator extends CheckBoxTreePopulator {
    
    /**
     * A contructor for the class, in which the current instance of the MainView
     * is stored.
     * @param mainView the current instance of the MainView.
     */
    RoadDataCBTPopulator(MainView mainView) {
        super(mainView);
    }

    /**
     * A method for populating the checkbox tree of a RoadDataTab. Overrides
     * the abstract method of the abstract baseclass CheckBoxTreePopulator.
     * @param checkBoxTree the TreeView component of the datatab.
     */
    @Override
    public void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = checkBoxTree.getRoot();
        TreeItem maintenanceItem = new TreeItem(MAINTENANCE);
        TreeItem conditionItem = new TreeItem(COND_FORECAST);
        root.getChildren().addAll(maintenanceItem, conditionItem);
        
        // Populating the Maintenance item.
        CheckBox allCBox = new CheckBox("All");
        TreeItem allItem = new TreeItem(allCBox);
        maintenanceItem.getChildren().add(allItem);
        for (var taskType : super.mainView.getViewModel().getMaintenanceTaskTypes()) {
            CheckBox checkBox = new CheckBox(taskType);
            TreeItem checkBoxItem = new TreeItem(checkBox);
            maintenanceItem.getChildren().add(checkBoxItem);
        }
        
        // Populating the Condition forecast item.
        CheckBox precipitationCheckBox = new CheckBox(PRECIPITATION);
        TreeItem precipitationItem = new TreeItem(precipitationCheckBox);
        CheckBox slipperinessCheckBox = new CheckBox(WINTER_SLIP);
        TreeItem slipperinessItem = new TreeItem(slipperinessCheckBox);
        CheckBox overallCheckBox = new CheckBox(OVERALL_COND);
        TreeItem overallItem = new TreeItem(overallCheckBox);
        conditionItem.getChildren().addAll( 
                precipitationItem, slipperinessItem, overallItem);
        
        // Populating the Forecast time item.
        TreeItem timeItem = new TreeItem(FORECAST_TIME);
        RadioButton radioButton1 = new RadioButton(TWO_HOURS);
        RadioButton radioButton2 = new RadioButton(FOUR_HOURS);
        RadioButton radioButton3 = new RadioButton(SIX_HOURS);
        RadioButton radioButton4 = new RadioButton(TWELVE_HOURS);
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(radioButton1, radioButton2, 
                radioButton3, radioButton4);
        radioButton1.setSelected(true);
        HBox timeHBox = new HBox();
        timeHBox.getChildren().addAll(radioButton1, radioButton2, 
                radioButton3, radioButton4);
        timeHBox.setSpacing(CheckBoxTreePopulator.SHORT_H_GAP);
        TreeItem timeHBoxItem = new TreeItem(timeHBox);
        conditionItem.getChildren().add(timeItem);
        timeItem.getChildren().add(timeHBoxItem);
    }
}
