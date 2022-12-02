package fi.tuni.swdesign.group3.gui.view;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * A subclass of CheckBoxTreePopulator for populating the checkbox treeview in 
 * the WeatherDataTab.
 * @author Lauri Puoskari
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

        // Populating the Observed values item.
        CheckBox obsTempBox = new CheckBox(TEMPERATURE);
        TreeItem obsTempItem = new TreeItem(obsTempBox);
        CheckBox obsWindBox = new CheckBox(WIND_SPEED);
        TreeItem obsWindItem = new TreeItem(obsWindBox);
        CheckBox obsCloudBox = new CheckBox(CLOUDINESS);
        TreeItem obsCloudItem = new TreeItem(obsCloudBox);
        observedItem.getChildren().addAll(obsTempItem, obsWindItem, obsCloudItem);
        
        // Populating the Predicted values item.
        CheckBox preTempBox = new CheckBox(TEMPERATURE);
        TreeItem preTempItem = new TreeItem(preTempBox);
        CheckBox preWindBox = new CheckBox(WIND_SPEED);
        TreeItem preWindItem = new TreeItem(preWindBox);
        predictedItem.getChildren().addAll(preTempItem, preWindItem);
        
        // Populating the Values per month item.
        CheckBox averageTempBox = new CheckBox(AVG_TEMPERATURE);
        TreeItem averageTempItem = new TreeItem(averageTempBox);
        CheckBox maxMinTempBox = new CheckBox(MAX_MIN_TEMPERATURE);
        TreeItem maxMinTempItem = new TreeItem(maxMinTempBox);
        perMonthItem.getChildren().addAll(averageTempItem, maxMinTempItem);
    }
}
