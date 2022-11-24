/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataTab extends DataTab {
    private GridPane gridPane;
    private TreeView checkBoxTree;
    private Label trafficMsgLabel;
    
    RoadDataTab(MainView mainView) {
        super(mainView);
        this.setText("Road data");
        this.gridPane = (GridPane) super.getContent();
        
        this.trafficMsgLabel = new Label("Amount of traffic messages: ");
        this.trafficMsgLabel.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        this.gridPane.add(this.trafficMsgLabel, 2, 3);
        this.gridPane.add(this.checkBoxTree, 2, 1, 1, 2);
        
        TreeItem root = new TreeItem();
        this.checkBoxTree.setRoot(root);
        this.checkBoxTree.setShowRoot(false);
        RoadDataCBTPopulator cbtPopulator = (RoadDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, this.getText());
        cbtPopulator.populateCheckBoxTree(this.checkBoxTree);
        
//        trafficMsgButton.setOnAction(new EventHandler<>() {
//            @Override
//            public void handle(ActionEvent t) {
//                TrafficMessageView trafMsgView = new TrafficMessageView(
//                        RoadDataTab.this.mainView);
//                trafMsgView.show();
//            }
//        });
        
    }

    @Override
    public TreeItem getCbTreeRoot() {
        return this.checkBoxTree.getRoot();
    }

    @Override
    public void updateChart(DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        if (visualizers.length > 0) {
            RoadDataVisualizer visualizer = (RoadDataVisualizer) visualizers[0];
            visualizer.visualizeData();
        }
    }
    
    public Label getTrafficMsgLabel() {
        return trafficMsgLabel;
    }

}
