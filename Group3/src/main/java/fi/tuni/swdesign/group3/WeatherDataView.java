/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Lauri Puoskari
 */
public class WeatherDataView {
    
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
        
        Label locationLabel = new Label("Location");
        
        ChoiceBox locationBox = new ChoiceBox();
        locationBox.setPrefWidth(MED_ELEMENT_WIDTH);
        locationBox.getItems().addAll(this.mainView.getLocations());
        
        Label timelineLabel = new Label("Timeline");
        
        TextField startTimeField = new TextField();
        TextField startDateField = new TextField();
        TextField endTimeField = new TextField();
        TextField endDateField = new TextField();
        
        startTimeField.setPromptText("hh.mm");
        startDateField.setPromptText("dd.mm.yyyy");
        endTimeField.setPromptText("hh.mm");
        endDateField.setPromptText("dd.mm.yyyy");
        
        Label lineLabel = new Label("–");
        
        HBox timelineHBox = new HBox();
        timelineHBox.getChildren().addAll(startTimeField, startDateField, 
                lineLabel, endTimeField, endDateField);
        timelineHBox.setSpacing(SHORT_H_GAP);
        timelineHBox.setAlignment(Pos.CENTER_LEFT);
        
        startTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        startDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        endTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        endDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        LineChart dataChart = new LineChart(new NumberAxis(), new NumberAxis());
        dataChart.setPrefSize(DATA_CHART_WIDTH, DATA_CHART_HEIGHT);
        
        TreeView checkBoxTree = new TreeView();
        checkBoxTree.setPrefSize(CHECK_BOX_TREE_WIDTH, CHECK_BOX_TREE_HEIGHT);
        populateCheckBoxTree(checkBoxTree);
        
        Button calculateButton = new Button("Calculate");
        calculateButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        Label errorInfoLabel = new Label("Invalid parameters!");
        errorInfoLabel.setTextFill(Color.RED);
        errorInfoLabel.setPrefWidth(LONG_ELEMENT_WIDTH);
        
        Button dataButton = new Button("Data");
        dataButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        Button prefButton = new Button("Preferences");
        prefButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(SHORT_H_GAP);
        buttonHBox.setAlignment(Pos.CENTER_LEFT);
        buttonHBox.getChildren().addAll(calculateButton, errorInfoLabel, 
                dataButton, prefButton);
        
        gridPane.add(locationLabel, 0, 0);
        gridPane.add(locationBox, 0, 1);
        gridPane.add(timelineLabel, 1, 0);
        gridPane.add(timelineHBox, 1, 1);
        gridPane.add(dataChart, 0, 2, 2, 1);
        gridPane.add(checkBoxTree, 2, 1, 1, 2);
        gridPane.add(buttonHBox, 0, 3, 2, 1);
        
        weatherDataTab.setContent(gridPane);
        
        dataButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                DataMenuView dataMenuView = new DataMenuView(WeatherDataView.this.
                        mainView);
                dataMenuView.show();
            }
        });
        
        prefButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                PreferencesMenuView prefMenuView = new PreferencesMenuView(
                        WeatherDataView.this.mainView);
                prefMenuView.show();
            }
        });
    }
    
    private void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = new TreeItem();
        checkBoxTree.setRoot(root);
        checkBoxTree.setShowRoot(false);
        TreeItem temperatureItem = new TreeItem("Temperature");
        TreeItem windItem = new TreeItem("Wind");
        TreeItem cloudinessItem = new TreeItem("Cloudiness");
        root.getChildren().addAll(temperatureItem, windItem, cloudinessItem);
        
        CheckBox averageTempBox = new CheckBox("Average daily/month");
        CheckBox maxMinTempBox = new CheckBox("Max & min values/month");
        CheckBox obsTempBox = new CheckBox("Observed values");
        CheckBox preTempBox = new CheckBox("Predicted values");
        TreeItem averageTempItem = new TreeItem(averageTempBox);
        TreeItem maxMinTempItem = new TreeItem(maxMinTempBox);
        TreeItem obsTempItem = new TreeItem(obsTempBox);
        TreeItem preTempItem = new TreeItem(preTempBox);
        temperatureItem.getChildren().addAll(averageTempItem, maxMinTempItem, 
                obsTempItem, preTempItem);
        
        CheckBox obsWindBox = new CheckBox("Observed values");
        CheckBox preWindBox = new CheckBox("Predicted values");
        TreeItem obsWindItem = new TreeItem(obsWindBox);
        TreeItem preWindItem = new TreeItem(preWindBox);
        windItem.getChildren().addAll(obsWindItem, preWindItem);
        
        CheckBox obsCloudBox = new CheckBox("Observed values");
        TreeItem obsCloudItem = new TreeItem(obsCloudBox);
        cloudinessItem.getChildren().add(obsCloudItem);
    }
}
