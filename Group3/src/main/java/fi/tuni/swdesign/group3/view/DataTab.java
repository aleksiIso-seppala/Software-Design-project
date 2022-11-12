/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

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
public abstract class DataTab extends Tab {
    
    private final static int WINDOW_WIDTH = 800;
    private final static int WINDOW_HEIGHT = 500;
    private final static int MENU_WIDTH = 225;
    private final static int MENU_HEIGHT = 100;
    private final static int H_GAP = 20;
    private final static int SHORT_H_GAP = 10;
    private final static int V_GAP = 10;
    protected final static int LONG_ELEMENT_WIDTH = 220;
    private final static int MED_ELEMENT_WIDTH = 150;
    private final static int SHORT_ELEMENT_WIDTH = 80;
    private final static int VERY_SHORT_ELEMENT_WIDTH = 55;
    private final static int DATA_CHART_HEIGHT = 300;
    private final static int DATA_CHART_WIDTH = 500;
    static final int CHECK_BOX_TREE_HEIGHT = 300;
    static final int CHECK_BOX_TREE_WIDTH = 220;
    private Scene scene;
    protected MainView mainView;
    protected ChoiceBox locationBox;
    protected TextField startTimeField;
    protected TextField startDateField;
    protected TextField endTimeField;
    protected TextField endDateField;
    
    DataTab(MainView mainView) {
        super();
        
        this.mainView = mainView;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(H_GAP);
        gridPane.setVgap(V_GAP);
        gridPane.setAlignment(Pos.CENTER);
        
        Label locationLabel = new Label("Location");
        
        ChoiceBox locationBox = new ChoiceBox();
        locationBox.setPrefWidth(MED_ELEMENT_WIDTH);
        locationBox.getItems().addAll(this.mainView.getLocations());
        locationBox.getSelectionModel().selectFirst();
        this.locationBox = locationBox;
        
        Label timelineLabel = new Label("Timeline");
        
        this.startTimeField = new TextField();
        this.startDateField = new TextField();
        this.endTimeField = new TextField();
        this.endDateField = new TextField();
        
        this.startTimeField.setPromptText("hh.mm");
        this.startDateField.setPromptText("dd.mm.yyyy");
        this.endTimeField.setPromptText("hh.mm");
        this.endDateField.setPromptText("dd.mm.yyyy");
        
        Label lineLabel = new Label("–");
        
        HBox timelineHBox = new HBox();
        timelineHBox.getChildren().addAll(this.startTimeField, this.startDateField, 
                lineLabel, this.endTimeField, this.endDateField);
        timelineHBox.setSpacing(SHORT_H_GAP);
        timelineHBox.setAlignment(Pos.CENTER_LEFT);
        
        this.startTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        this.startDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        this.endTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        this.endDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        LineChart dataChart = new LineChart(new NumberAxis(), new NumberAxis());
        dataChart.setPrefSize(DATA_CHART_WIDTH, DATA_CHART_HEIGHT);
        
//        TreeView checkBoxTree = new TreeView();
//        checkBoxTree.setPrefSize(CHECK_BOX_TREE_WIDTH, CHECK_BOX_TREE_HEIGHT);
        
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
//        gridPane.add(checkBoxTree, 2, 1, 1, 2);
        gridPane.add(buttonHBox, 0, 3, 2, 1);
        
        this.setContent(gridPane);
        
        dataButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                DataMenuView dataMenuView = new DataMenuView(DataTab.this.
                        mainView);
                dataMenuView.show();
            }
        });
        
        prefButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                PreferencesMenuView prefMenuView = new PreferencesMenuView(
                        DataTab.this.mainView);
                prefMenuView.show();
            }
        });
        
        calculateButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                DataQueryFactory dqFactory = new DataQueryFactory();
                DataQuery dataQuery = dqFactory.makeDataQuery(DataTab.this.getText());
                DataTab.this.populateQuery(dataQuery);
                
                // Print tests.
//                RoadDataQuery query = (RoadDataQuery) dataQuery;
//                System.out.println(query.getLocation());
//                System.out.println(query.getTimelineStart()[0] + " " + query.getTimelineStart()[1]);
//                System.out.println(query.getTimelineEnd()[0] + " " + query.getTimelineEnd()[1]);
//                System.out.println(query.getSelectedTasks());
//                System.out.println(query.getSelectedForecasts());
//                System.out.println(query.getForecastTime());
            }
            
        });
    }
    
    abstract void populateQuery(DataQuery query);
}
