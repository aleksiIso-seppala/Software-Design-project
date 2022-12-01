/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.RoadTrafficData;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * A subclass of DataTab for a tab in which the RoadData parameters are set and
 * the RoadData is visualized.
 * @author Lauri Puoskari
 */
public class RoadDataTab extends DataTab {
    /**
     * The GridPane in which the visual elements are placed.
     */
    private GridPane gridPane;
    /**
     * The TreeView in which the parameter CheckBoxes are placed.
     */
    private TreeView checkBoxTree;
    /**
     * The label that shows the current amount of traffic messages.
     */
    private Label trafficMsgLabel;
    
    private RoadTrafficData recentData;
    private RoadDataQuery recentQuery;
    
    /**
     * A constructor in which the visual elements of the RoadDataTab and its 
     * functionality is initialized.
     * @param mainView the current instance of MainView.
     */
    RoadDataTab(MainView mainView) {
        super(mainView);
        this.setText(ROAD_DATA);
        this.gridPane = (GridPane) super.getContent();
        
        this.trafficMsgLabel = new Label(TRAFFIC_MSG_AMOUNT);
        this.trafficMsgLabel.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, 
                DataTab.CHECK_BOX_TREE_HEIGHT);
        
        this.gridPane.add(this.trafficMsgLabel, THIRD_COL, FOURTH_ROW);
        this.gridPane.add(this.checkBoxTree, THIRD_COL, SECOND_ROW, 
                COL_SPAN_OF_1, ROW_SPAN_OF_2);
        
        TreeItem root = new TreeItem();
        this.checkBoxTree.setRoot(root);
        this.checkBoxTree.setShowRoot(false);
        RoadDataCBTPopulator cbtPopulator = (RoadDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, this.getText());
        cbtPopulator.populateCheckBoxTree(this.checkBoxTree);
    }

    /**
     * A getter-method for the root of the CheckBoxTree. Overrides the abstract
     * method in base class DataTab
     * @return the root item of the CheckBoxTree.
     */
    @Override
    public TreeItem getCbTreeRoot() {
        return this.checkBoxTree.getRoot();
    }

    /**
     * A method for updating the data visualizations.Overrides the abstract
     * method in base class DataTab
     * @param query the query used to fetch the data.
     * @param visualizers the DataVisualizers used for the visualization.
     */
    @Override
    public void updateChart(DataQuery query, DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        if (visualizers.length > 0) {
            RoadDataVisualizer visualizer = (RoadDataVisualizer) visualizers[0];
            RoadDataQuery roadDQ = (RoadDataQuery) query;
            visualizer.visualizeData(this.chartTabPane);
            this.trafficMsgLabel.setText(TRAFFIC_MSG_AMOUNT 
                    + visualizer.getData().getNumberOfTrafficMessages());
            this.recentData = visualizer.getData();
            this.recentQuery = visualizer.getQuery();
        }
    }
    
    /**
     * A getter-method for the traffic message label.
     * @return the label in which the amount of traffic messages is shown.
     */
    public Label getTrafficMsgLabel() {
        return trafficMsgLabel;
    }

    public RoadTrafficData getRecentData() {
        return recentData;
    }

    public RoadDataQuery getRecentQuery() {
        return recentQuery;
    }

    @Override
    public void updateParams(DataQuery query) {
        RoadDataQuery roadDQ = (RoadDataQuery) query;
        super.locationBox.getSelectionModel().select(roadDQ.getLocation());
        TreeItem root = this.checkBoxTree.getRoot();
        for (Object object : root.getChildren()) {
            TreeItem item = (TreeItem) object;
            for (Object subObject : item.getChildren()) {
                TreeItem subItem = (TreeItem) subObject;
                if (item.getValue().equals("Maintenance")) {
                    CheckBox taskBox = (CheckBox) subItem.getValue();
                    if (roadDQ.getSelectedTasks().contains(taskBox.getText())) {
                        taskBox.setSelected(true);
                    }
                }
                else if (item.getValue().equals("Condition forecast")) {
                    if (subItem.getValue() instanceof CheckBox checkBox) {
                        if (roadDQ.getSelectedForecasts().contains(checkBox.getText())) {
                            checkBox.setSelected(true);
                        }
                    }
                    else {
                        TreeItem toggleGroupItem = (TreeItem) subItem.getChildren().get(0);
                        HBox hBox = (HBox) toggleGroupItem.getValue();
                        for (var node : hBox.getChildren()) {
                            RadioButton timeButton = (RadioButton) node;
                            if (roadDQ.getForecastTime().equals(timeButton.getText())) {
                                timeButton.setSelected(true);
                            }
                        }
                    }
                }
            }
            
        }
        
    }
    
   
    
    

}
