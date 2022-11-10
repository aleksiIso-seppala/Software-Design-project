/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
/**
 *
 * @author Lauri Puoskari
 */
public class MainView extends Application {
    
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 500;

    private TabPane tabPane;
    private ArrayList<String> locations;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("RoadCast");
        this.tabPane = new TabPane();
        
        this.tabPane.getTabs().add(new StartMenuTab(this));
        stage.setScene(new Scene(tabPane, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();
        
        // Hardcode implementation for prototype.
        this.locations = new ArrayList<>();
        this.locations.add("Tampere");
        this.locations.add("Helsinki");
        this.locations.add("Oulu");
        this.locations.add("Rovaniemi");
        this.locations.add("Kuopio");
        Collections.sort(this.locations);
    }
    
    public TabPane getTabPane() {
        return this.tabPane;
    }
    
    public ArrayList<String> getLocations() {
        return this.locations;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public void initGUI() {
        launch();
    }

}
