/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author Lauri Puoskari
 */
public class StartMenuView {
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 500;
    private final static int H_GAP = 20;
    private final static int V_GAP = 10;
    private final static int BUTTON_WIDTH = 150;
    private Scene scene;
    private MainView mainView;
    
    StartMenuView(MainView mainView, TabPane tabPane) {
        this.mainView = mainView;
        Tab startMenuTab = new Tab("Start menu");
        startMenuTab.setClosable(false);
        tabPane.getTabs().add(startMenuTab);
        this.scene = new Scene(tabPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(H_GAP);
        gridPane.setVgap(V_GAP);
        Label startMenuLabel = new Label("Welcome to RoadCast!\n"
                + "Please choose which data you want to view:");
        startMenuLabel.setFont(new Font(20));
        gridPane.add(startMenuLabel, 0, 0, 5, 1);
        Button roadDataButton = new Button("Road data");
        Button weatherDataButton = new Button("Weather data");
        Button combinedDataButton = new Button("Combined data");
        gridPane.add(roadDataButton, 0, 1);
        gridPane.add(weatherDataButton, 2, 1);
        gridPane.add(combinedDataButton, 4, 1);
        roadDataButton.setMinWidth(BUTTON_WIDTH);
        weatherDataButton.setMinWidth(BUTTON_WIDTH);
        combinedDataButton.setMinWidth(BUTTON_WIDTH);
        gridPane.setAlignment(Pos.CENTER);
        startMenuTab.setContent(gridPane);
        
        roadDataButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                startDataView("RoadDataView");
            }
        });
        
        weatherDataButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                startDataView("WeatherDataView");
            }
        });
        
        combinedDataButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                startDataView("CombinedDataView");
            }
        });
    }
    
    Scene getScene() {
        return this.scene;
    }
    
    void startDataView(String dataViewName) {
        if (dataViewName.equals("RoadDataView")) {
            RoadDataView roadDataView = new RoadDataView(this.mainView);
        }
        else if (dataViewName.equals("WeatherDataView")) {
            WeatherDataView weatherDataView = new WeatherDataView(this.mainView);
        }
        else if (dataViewName.equals("CombinedDataView")) {
            CombinedDataView combinedDataView = new CombinedDataView(this.mainView);
        }
    }
}
