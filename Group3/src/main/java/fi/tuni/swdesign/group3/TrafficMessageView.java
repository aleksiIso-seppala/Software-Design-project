/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Lauri Puoskari
 */
public class TrafficMessageView {
    
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 500;
    private final static int MENU_WIDTH = 225;
    private final static int MENU_HEIGHT = 100;
    private final static int H_GAP = 20;
    private final static int SHORT_H_GAP = 10;
    private final static int V_GAP = 10;
    private final static int LONG_ELEMENT_WIDTH = 220;
    private final static int MED_ELEMENT_WIDTH = 150;
    private final static int SHORT_ELEMENT_WIDTH = 80;
    private final static int VERY_SHORT_ELEMENT_WIDTH = 55;
    private final static int DATA_CHART_HEIGHT = 300;
    private final static int DATA_CHART_WIDTH = 500;
    private final static int CHECK_BOX_TREE_HEIGHT = 300;
    private final static int CHECK_BOX_TREE_WIDTH = 220;
    private MainView mainView;
    private Stage stage;
    
    TrafficMessageView(MainView mainView) {
        this.mainView = mainView;
        this.stage = new Stage();
        
        this.stage.initOwner(this.mainView.getStage());
        this.stage.setResizable(false);
        
        this.stage.setTitle("Traffic messages");
        
        TreeView treeView = new TreeView();
        TreeItem root = new TreeItem();
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
        for (int i = 1; i < 6; i++) {
            TreeItem trafficMsgItem = new TreeItem("Traffic message " + i);
            TreeItem trafficMsgDesc = new TreeItem("Description of traffic message");
            trafficMsgItem.getChildren().add(trafficMsgDesc);
            root.getChildren().add(trafficMsgItem);
        }
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(treeView, 0, 0);
        
        Scene trafficMsgScene = new Scene(gridPane, MENU_WIDTH, CHECK_BOX_TREE_HEIGHT);
        this.stage.setScene(trafficMsgScene);
    }
    
    public void show() {
        this.stage.show();
    }
}
