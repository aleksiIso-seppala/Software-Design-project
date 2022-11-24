/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lauri Puoskari
 */
public class CombinedDataTab extends DataTab{
    
    private TreeView checkBoxTree;
    
    CombinedDataTab(MainView mainView) {
        super(mainView);
        this.setText("Combined data");
        GridPane gridPane = (GridPane) super.getContent();
        
        Button trafficMsgButton = new Button("Traffic messages (5)");
        trafficMsgButton.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        gridPane.add(trafficMsgButton, 2, 3);
        gridPane.add(this.checkBoxTree, 2, 1, 1, 2);
        
        CheckBoxTreePopulator cbtPopulator = new CheckBoxTreePopulator(super.mainView);
        cbtPopulator.populateCheckBoxTree(this.checkBoxTree, this.getText());
        
        trafficMsgButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                TrafficMessageView trafMsgView = new TrafficMessageView(
                        CombinedDataTab.this.mainView);
                trafMsgView.show();
            }
        });
    }
    
    @Override
    public TreeItem getCbTreeRoot() {
        return this.checkBoxTree.getRoot();
    }

    @Override
    public void updateChart(DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        RoadDataVisualizer roadDV = null;
        WeatherDataVisualizer weatherDV = null;
        for (int i = 0; i < visualizers.length; i++) {
            if (visualizers[i] instanceof RoadDataVisualizer) {
                roadDV = (RoadDataVisualizer) visualizers[i];
            }
            else if (visualizers[i] instanceof WeatherDataVisualizer) {
                weatherDV = (WeatherDataVisualizer) visualizers[i];
            }
        }
        if (roadDV != null) {
            roadDV.visualizeData();
        }
        if (weatherDV != null) {
            weatherDV.visualizeData();
        }
    }
}
