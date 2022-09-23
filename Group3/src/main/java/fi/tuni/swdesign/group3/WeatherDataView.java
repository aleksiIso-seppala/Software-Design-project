/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lauri Puoskari
 */
public class WeatherDataView {
    
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 500;
    private final static int H_GAP = 20;
    private final static int V_GAP = 10;
    private Scene scene;
    private MainView mainView;
    
    WeatherDataView(MainView mainView) {
        this.mainView = mainView;
        Tab weatherDataTab = new Tab("Weather data");
        this.mainView.getTabPane().getTabs().add(weatherDataTab);
        this.mainView.getTabPane().getSelectionModel().select(weatherDataTab);
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(H_GAP);
        gridPane.setVgap(V_GAP);
        gridPane.setAlignment(Pos.CENTER);
    }
    
}
