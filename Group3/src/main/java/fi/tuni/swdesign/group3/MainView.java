/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.application.Application;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
/**
 *
 * @author Lauri Puoskari
 */
public class MainView extends Application {

    private TabPane tabPane;

    @Override
    public void start(Stage stage) {
        stage.setTitle("RoadCast");
        this.tabPane = new TabPane();
        StartMenuView startMenuView = new StartMenuView(this, this.tabPane);
        stage.setScene(startMenuView.getScene());
        stage.show();
    }
    
    TabPane getTabPane() {
        return this.tabPane;
    }
    
    public void initGUI() {
        launch();
    }

}
