/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

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
     * A method for updating the data visualizations. Overrides the abstract
     * method in base class DataTab
     * @param visualizers the DataVisualizers used for the visualization.
     */
    @Override
    public void updateChart(DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        if (visualizers.length > 0) {
            RoadDataVisualizer visualizer = (RoadDataVisualizer) visualizers[0];
            visualizer.visualizeData();
        }
    }
    
    /**
     * A getter-method for the traffic message label.
     * @return the label in which the amount of traffic messages is shown.
     */
    public Label getTrafficMsgLabel() {
        return trafficMsgLabel;
    }

}
