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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Lauri Puoskari
 */
public class RoadDataView {
    
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
    
    RoadDataView(MainView mainView) {
        this.mainView = mainView;
        Tab roadDataTab = new Tab("Road data");
        this.mainView.getTabPane().getTabs().add(roadDataTab);
        this.mainView.getTabPane().getSelectionModel().select(roadDataTab);
        
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
        
        Button trafficMsgButton = new Button("Traffic messages (#)");
        trafficMsgButton.setPrefWidth(LONG_ELEMENT_WIDTH);
        
        gridPane.add(locationLabel, 0, 0);
        gridPane.add(locationBox, 0, 1);
        gridPane.add(timelineLabel, 1, 0);
        gridPane.add(timelineHBox, 1, 1);
        gridPane.add(dataChart, 0, 2, 2, 1);
        gridPane.add(checkBoxTree, 2, 1, 1, 2);
        gridPane.add(buttonHBox, 0, 3, 2, 1);
        gridPane.add(trafficMsgButton, 2, 3);
        
        roadDataTab.setContent(gridPane);
        
        dataButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                RoadDataView.this.initDataMenu();
            }
        });
        
        prefButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                RoadDataView.this.initPrefMenu();
            }
        });
        
        trafficMsgButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent t) {
                RoadDataView.this.initTrafficMessagesView();
            }
        });
    }
    
    private void populateCheckBoxTree(TreeView checkBoxTree) {
        TreeItem root = new TreeItem();
        checkBoxTree.setRoot(root);
        checkBoxTree.setShowRoot(false);
        TreeItem maintenanceItem = new TreeItem("Maintenance");
        TreeItem conditionItem = new TreeItem("Condition forecast");
        root.getChildren().addAll(maintenanceItem, conditionItem);
        
        // Hardcode implementation for prototype.
        for (int i = 1; i < 6; i++) {
            CheckBox checkBox = new CheckBox("Maintenance task " + i);
            TreeItem checkBoxItem = new TreeItem(checkBox);
            maintenanceItem.getChildren().add(checkBoxItem);
        }
        
        CheckBox visibilityCheckBox = new CheckBox("Visibility");
        TreeItem visibilityItem = new TreeItem(visibilityCheckBox);
        CheckBox frictionCheckBox = new CheckBox("Friction");
        TreeItem frictionItem = new TreeItem(frictionCheckBox);
        CheckBox precipitationCheckBox = new CheckBox("Precipitation");
        TreeItem precipitationItem = new TreeItem(precipitationCheckBox);
        CheckBox slipperinessCheckBox = new CheckBox("Winter slipperiness");
        TreeItem slipperinessItem = new TreeItem(slipperinessCheckBox);
        CheckBox overallCheckBox = new CheckBox("Overall");
        TreeItem overallItem = new TreeItem(overallCheckBox);
        conditionItem.getChildren().addAll(visibilityItem, frictionItem, 
                precipitationItem, slipperinessItem, overallItem);
        
        TreeItem timeItem = new TreeItem("Time (hours)");
        Slider timeSlider = new Slider(2, 12, 2);
        timeSlider.setShowTickMarks(true);
        timeSlider.setShowTickLabels(true);
        timeSlider.setMajorTickUnit(2);
        timeSlider.setMinorTickCount(0);
        timeSlider.setBlockIncrement(2);
        timeSlider.setSnapToTicks(true);
        TreeItem sliderItem = new TreeItem(timeSlider);
        conditionItem.getChildren().add(timeItem);
        conditionItem.getChildren().add(sliderItem);
    }
    
    public void initDataMenu() {
        Stage dataStage = new Stage();
        
        dataStage.initOwner(this.mainView.getStage());
        dataStage.initModality(Modality.WINDOW_MODAL);
        dataStage.setResizable(false);
        
        dataStage.setTitle("Data menu");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        Label infoLabel = new Label("Save or load data:");
        infoLabel.setFont(new Font(16));
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        gridPane.add(infoLabel, 0, 0, 2, 1);
        gridPane.add(saveButton, 0, 1);
        gridPane.add(loadButton, 1, 1);
        Scene dataScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        dataStage.setScene(dataScene);
        dataStage.show();
    }
    
    public void initPrefMenu() {
        Stage prefStage = new Stage();
        
        prefStage.initOwner(this.mainView.getStage());
        prefStage.initModality(Modality.WINDOW_MODAL);
        prefStage.setResizable(false);
        
        prefStage.setTitle("Preferences menu");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        Label infoLabel = new Label("Save or load preferences:");
        infoLabel.setFont(new Font(16));
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        gridPane.add(infoLabel, 0, 0, 2, 1);
        gridPane.add(saveButton, 0, 1);
        gridPane.add(loadButton, 1, 1);
        Scene prefScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        prefStage.setScene(prefScene);
        prefStage.show();
    }
    
    public void initTrafficMessagesView() {
        Stage trafficMsgStage = new Stage();
        
        trafficMsgStage.initOwner(this.mainView.getStage());
        trafficMsgStage.setResizable(false);
        
        trafficMsgStage.setTitle("Traffic messages");
        
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
        trafficMsgStage.setScene(trafficMsgScene);
        trafficMsgStage.show();
    }
}
