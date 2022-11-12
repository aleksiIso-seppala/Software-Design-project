/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataTab extends DataTab {
    private GridPane gridPane;
    private TreeView checkBoxTree;
    
    RoadDataTab(MainView mainView) {
        super(mainView);
        this.setText("Road data");
        this.gridPane = (GridPane) super.getContent();
        
        Button trafficMsgButton = new Button("Traffic messages (5)");
        trafficMsgButton.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        this.checkBoxTree = new TreeView();
        checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        this.gridPane.add(trafficMsgButton, 2, 3);
        this.gridPane.add(checkBoxTree, 2, 1, 1, 2);
        
        CheckBoxTreePopulator cbtPopulator = new CheckBoxTreePopulator();
        cbtPopulator.populateCheckBoxTree(checkBoxTree, this.getText());
        
        trafficMsgButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                TrafficMessageView trafMsgView = new TrafficMessageView(
                        RoadDataTab.this.mainView);
                trafMsgView.show();
            }
        });
        
    }

    @Override
    void populateQuery(DataQuery dataQuery) {
        RoadDataQuery query = (RoadDataQuery) dataQuery;
        query.setLocation((String) super.locationBox.getValue());
        query.setTimelineStart(super.startTimeField.getText(), super.startDateField.getText());
        query.setTimelineEnd(super.endTimeField.getText(), super.endDateField.getText());
        
        TreeItem root = this.checkBoxTree.getRoot();
        TreeItem maintenanceParent = (TreeItem) root.getChildren().get(0);
        ArrayList<String> selectedTasks = new ArrayList<>();
        for (int i = 0; i < maintenanceParent.getChildren().size(); i++) {
            TreeItem taskItem = (TreeItem) maintenanceParent.getChildren().get(i);
            CheckBox taskBox = (CheckBox) taskItem.getValue();
            if (taskBox.isSelected()) {
                selectedTasks.add(taskBox.getText());
            }
        }
        query.setSelectedTasks(selectedTasks);
        
        TreeItem forecastParent = (TreeItem) root.getChildren().get(1);
        ArrayList<String> selectedForecasts = new ArrayList<>();
        String forecastTime = "";
        for (int i = 0; i < forecastParent.getChildren().size(); i++) {
            TreeItem forecastItem = (TreeItem) forecastParent.getChildren().get(i);
            if (forecastItem.getValue().equals("Time (hours)")) {
                TreeItem fcTimeItem = (TreeItem) forecastItem.getChildren().get(0);
                HBox hBox = (HBox) fcTimeItem.getValue();
                for (int j = 0; j < hBox.getChildren().size(); j++) {
                    RadioButton timeButton = (RadioButton) hBox.getChildren().get(j);
                    if (timeButton.isSelected()) {
                        forecastTime = timeButton.getText();
                    }
                }
            }
            else {
                CheckBox forecastBox = (CheckBox) forecastItem.getValue();
                if (forecastBox.isSelected()) {
                    selectedForecasts.add(forecastBox.getText());
                }
            }
        }
        query.setSelectedForecasts(selectedForecasts);
        query.setForecastTime(forecastTime);
    }
}
