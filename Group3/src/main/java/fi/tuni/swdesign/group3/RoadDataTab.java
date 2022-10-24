/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataTab extends DataTab {
    
    RoadDataTab(MainView mainView) {
        super(mainView);
        this.setText("Road data");
        GridPane gridPane = (GridPane) super.getContent();
        
        Button trafficMsgButton = new Button("Traffic messages (5)");
        trafficMsgButton.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        TreeView checkBoxTree = new TreeView();
        checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        gridPane.add(trafficMsgButton, 2, 3);
        gridPane.add(checkBoxTree, 2, 1, 1, 2);
        
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
}
