/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.application.Application;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
/**
 *
 * @author Lauri Puoskari
 */
public class MainView extends Application {

    private TabPane tabPane;
    private ArrayList<String> locations;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("RoadCast");
        this.tabPane = new TabPane();
        StartMenuView startMenuView = new StartMenuView(this, this.tabPane);
        stage.setScene(startMenuView.getScene());
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
