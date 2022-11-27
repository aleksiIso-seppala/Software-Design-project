/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * A class for a tab which contains the starting menu.
 * @author Lauri Puoskari
 */
public class StartMenuTab extends Tab {

    /**
     * A constant representing a horizontal gap between visual components.
     */
    private final static int H_GAP = 20;
    /**
     * A constant representing a vertical gap between visual components.
     */
    private final static int V_GAP = 10;
    /**
     * A constant representing width of a button.
     */
    private final static int BUTTON_WIDTH = 150;
    /**
     * A constant representing the first column of a GridPane.
     */
    private final static int FIRST_COL = 0;
    /**
     * A constant representing the first row of a GridPane.
     */
    private final static int FIRST_ROW = 0;
    /**
     * A constant representing the second row of a GridPane.
     */
    private final static int SECOND_ROW = 1;
    /**
     * A constant representing the 3rd column of a GridPane.
     */
    private final static int THIRD_COL = 2;
    /**
     * A constant representing the 5th column of a GridPane.
     */
    private final static int FIFTH_COL = 4;
    /**
     * A constant representing the length of a column span of 5.
     */
    private final static int COL_SPAN_OF_5 = 5;
    /**
     * A constant representing the length of a row span of 1.
     */
    private final static int ROW_SPAN_OF_1 = 1;
    /**
     * A constant representing the font size of a title.
     */
    private final static int TITLE_FONT_SIZE = 20;
    /**
     * A constant string representing start menu.
     */
    private final static String START_MENU = "Start menu";
    /**
     * A constant string representing the welcome prompt in the start menu.
     */
    private final static String WELCOME_PROMPT = "Welcome to RoadCast!\n"
                + "Please choose which data you want to view:";
    /**
     * A constant string representing road data.
     */
    private final static String ROAD_DATA = "Road data";
    /**
     * A constant string representing weather data.
     */
    private final static String WEATHER_DATA = "Weather data";
    /**
     * A constant string representing combined data.
     */
    private final static String COMBINED_DATA = "Combined data";
    /**
     * The current instance of MainView.
     */
    private MainView mainView;
    
    /**
     * A constructor in which the visual components and the functionality of the
     * StartMenu are initialized.
     * @param mainView the current instance of MainView.
     */
    public StartMenuTab(MainView mainView) {
        super(START_MENU);
        this.setClosable(false);
        
        this.mainView = mainView;
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(H_GAP);
        gridPane.setVgap(V_GAP);
        Label startMenuLabel = new Label(WELCOME_PROMPT);
        startMenuLabel.setFont(new Font(TITLE_FONT_SIZE));
        gridPane.add(startMenuLabel, FIRST_COL, FIRST_ROW, COL_SPAN_OF_5, ROW_SPAN_OF_1);
        Button roadDataButton = new Button(ROAD_DATA);
        Button weatherDataButton = new Button(WEATHER_DATA);
        Button combinedDataButton = new Button(COMBINED_DATA);
        gridPane.add(roadDataButton, FIRST_COL, SECOND_ROW);
        gridPane.add(weatherDataButton, THIRD_COL, SECOND_ROW);
        gridPane.add(combinedDataButton, FIFTH_COL, SECOND_ROW);
        roadDataButton.setMinWidth(BUTTON_WIDTH);
        weatherDataButton.setMinWidth(BUTTON_WIDTH);
        combinedDataButton.setMinWidth(BUTTON_WIDTH);
        gridPane.setAlignment(Pos.CENTER);
        this.setContent(gridPane);
        
        roadDataButton.setOnAction((ActionEvent t) -> {
            RoadDataTab roadDataTab = new RoadDataTab(StartMenuTab.this.mainView);
            StartMenuTab.this.getTabPane().getTabs().add(roadDataTab);
            StartMenuTab.this.getTabPane().getSelectionModel().select(roadDataTab);
        });
        
        weatherDataButton.setOnAction((ActionEvent t) -> {
            WeatherDataTab weatherDataTab = new WeatherDataTab(StartMenuTab.this.mainView);
            StartMenuTab.this.getTabPane().getTabs().add(weatherDataTab);
            StartMenuTab.this.getTabPane().getSelectionModel().select(weatherDataTab);
        });
        
        combinedDataButton.setOnAction((ActionEvent t) -> {
            CombinedDataTab combinedDataTab = new CombinedDataTab(StartMenuTab.this.mainView);
            StartMenuTab.this.getTabPane().getTabs().add(combinedDataTab);
            StartMenuTab.this.getTabPane().getSelectionModel().select(combinedDataTab);
        });
    }
    }
