/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.RoadData;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * An abstract base class for a tab in which the data parameters are set and
 * the data is visualized.
 * @author Lauri Puoskari
 */
public abstract class DataTab extends Tab {

    /**
     * A constant that represents the medium horizontal gap between visual
     * elements.
     */
    protected final static int H_GAP = 20;
    /**
     * A constant that represents the short horizontal gap between visual
     * elements.
     */
    protected final static int SHORT_H_GAP = 10;
    /**
     * A constant that represents the vertical gap between the visual elements.
     */
    protected final static int V_GAP = 10;
    /**
     * A constant that represents the width of a long-width visual element.
     */
    protected final static int LONG_ELEMENT_WIDTH = 220;
    /**
     * A constant that represents the width of a medium-width visual element.
     */
    protected final static int MED_ELEMENT_WIDTH = 150;
    /**
     * A constant that represents the width of a short-width visual element.
     */
    protected final static int SHORT_ELEMENT_WIDTH = 80;
    /**
     * A constant that represents the width of a very short-width visual element.
     */
    protected final static int VERY_SHORT_ELEMENT_WIDTH = 55;
    /**
     * A constant that represents the height of the check box treeview.
     */
    protected static final int CHECK_BOX_TREE_HEIGHT = 300;
    /**
     * A constant that represents the width of the check box treeview.
     */
    protected static final int CHECK_BOX_TREE_WIDTH = 220;
    /**
     * A constant representing the first row of a grid.
     */
    protected static final int FIRST_ROW = 0;
    /**
     * A constant representing the first column of a grid.
     */
    protected static final int FIRST_COL = 0;
    /**
     * A constant representing the 2nd row of a grid.
     */
    protected static final int SECOND_ROW = 1;
    /**
     * A constant representing the 2nd column of a grid.
     */
    protected static final int SECOND_COL = 1;
    /**
     * A constant representing the 3rd row of a grid.
     */
    protected static final int THIRD_ROW = 2;
    /**
     * A constant representing the 3rd column of a grid.
     */
    protected static final int THIRD_COL = 2;
    /**
     * A constant representing the 4th row of a grid.
     */
    protected static final int FOURTH_ROW = 3;
    /**
     * A constant representing the 4th column of a grid.
     */
    protected static final int FOURHT_COL = 3;
    /**
     * A constant representing a length span of one column.
     */
    protected static final int COL_SPAN_OF_1 = 1;
    /**
     * A constant representing a length span of two columns.
     */
    protected static final int COL_SPAN_OF_2 = 2;
    /**
     * A constant representing a length span of one row.
     */
    protected static final int ROW_SPAN_OF_1 = 1;
    /**
     * A constant representing a length span of two rows.
     */
    protected static final int ROW_SPAN_OF_2 = 2;
    /**
     * A constant string representing location.
     */
    protected static final String LOCATION = "Location";
    /**
     * A constant string representing timeline.
     */
    protected static final String TIMELINE = "Timeline";
    /**
     * A constant string representing the format in which the time should be
     * typed.
     */
    protected static final String TIME_FORMAT = "hh.mm";
    /**
     * A constant string representing the format in which the date should be
     * typed.
     */
    protected static final String DATE_FORMAT = "dd.mm.yyyy";
    /**
     * A constant string representing calculate.
     */
    protected static final String CALCULATE = "Calculate";
    /**
     * A constant string representing data.
     */
    protected static final String DATA = "Data";
    /**
     * A constant string representing preferences.
     */
    protected static final String PREFERENCES = "Preferences";
    /**
     * A constant string representing a line.
     */
    protected static final String LINE = "–";
    /**
     * A constant string representing that the DataQuery is valid.
     */
    protected static final String DQ_IS_VALID = "Data query is valid.";
    /**
     * A constant string representing an empty string.
     */
    protected static final String EMPTY_STR = "";
    /**
     * A constant string representing road data.
     */
    protected static final String ROAD_DATA = "Road data";
    /**
     * A constant string representing weather data.
     */
    protected static final String WEATHER_DATA = "Weather data";
    /**
     * A constant string representing combined data.
     */
    protected static final String COMBINED_DATA = "Combined data";
    /**
     * A constant string representing the amount of traffic messages.
     */
    protected static final String TRAFFIC_MSG_AMOUNT = "Amount of traffic messages: ";
    /**
     * The current instance of the MainView.
     */
    protected MainView mainView;
    /**
     * The ChoiceBox in which the location is selected.
     */
    protected ChoiceBox locationBox;
    /**
     * The TextField in which the time of the start of the timeline is typed.
     */
    protected TextField startTimeField;
    /**
     * The TextField in which the date of the start of the timeline is typed.
     */
    protected TextField startDateField;
    /**
     * The TextField in which the time of the end of the timeline is typed.
     */
    protected TextField endTimeField;
    /**
     * The TextField in which the date of the end of the timeline is typed.
     */
    protected TextField endDateField;
    /**
     * The TabPane in which the data is visualized.
     */
    protected TabPane chartTabPane;
    /**
     * The label in which the error messages are visualized.
     */
    protected Label errorInfoLabel;
    
    /**
     * A constructor in which the visual template of a DataTab and its 
     * functionality is initialized.
     * @param mainView the current instance of MainView.
     */
    DataTab(MainView mainView) {
        super();
        
        this.mainView = mainView;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(H_GAP);
        gridPane.setVgap(V_GAP);
        gridPane.setAlignment(Pos.CENTER);
        
        Label locationLabel = new Label(LOCATION);
        
        this.locationBox = new ChoiceBox();
        this.locationBox.setPrefWidth(MED_ELEMENT_WIDTH);
        this.locationBox.getItems().addAll(this.mainView.getLocations());
        this.locationBox.getSelectionModel().selectFirst();
        
        Label timelineLabel = new Label(TIMELINE);
        
        this.startTimeField = new TextField();
        this.startDateField = new TextField();
        this.endTimeField = new TextField();
        this.endDateField = new TextField();
        
        this.startTimeField.setPromptText(TIME_FORMAT);
        this.startDateField.setPromptText(DATE_FORMAT);
        this.endTimeField.setPromptText(TIME_FORMAT);
        this.endDateField.setPromptText(DATE_FORMAT);
        
        Label lineLabel = new Label(LINE);
        
        HBox timelineHBox = new HBox();
        timelineHBox.getChildren().addAll(this.startTimeField, this.startDateField, 
                lineLabel, this.endTimeField, this.endDateField);
        timelineHBox.setSpacing(SHORT_H_GAP);
        timelineHBox.setAlignment(Pos.CENTER_LEFT);
        
        this.startTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        this.startDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        this.endTimeField.setPrefWidth(VERY_SHORT_ELEMENT_WIDTH);
        this.endDateField.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        this.chartTabPane = new TabPane();
        
        Button calculateButton = new Button(CALCULATE);
        calculateButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        this.errorInfoLabel = new Label();
        this.errorInfoLabel.setTextFill(Color.RED);
        this.errorInfoLabel.setPrefWidth(LONG_ELEMENT_WIDTH);
        
        Button dataButton = new Button(DATA);
        dataButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        Button prefButton = new Button(PREFERENCES);
        prefButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(SHORT_H_GAP);
        buttonHBox.setAlignment(Pos.CENTER_LEFT);
        buttonHBox.getChildren().addAll(calculateButton, this.errorInfoLabel, 
                dataButton, prefButton);
        
        gridPane.add(locationLabel, FIRST_COL, FIRST_ROW);
        gridPane.add(this.locationBox, FIRST_COL, SECOND_ROW);
        gridPane.add(timelineLabel, SECOND_COL, FIRST_ROW);
        gridPane.add(timelineHBox, SECOND_COL, SECOND_ROW);
        gridPane.add(this.chartTabPane, FIRST_COL, THIRD_ROW,
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(buttonHBox, FIRST_COL, FOURTH_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        
        this.setContent(gridPane);
        
        dataButton.setOnAction((ActionEvent t) -> {
            DataMenuView dataMenuView = new DataMenuView(DataTab.this.
                    mainView);
            dataMenuView.show();
        });
        
        prefButton.setOnAction((ActionEvent t) -> {
            PreferencesMenuView prefMenuView = new PreferencesMenuView(
                    DataTab.this.mainView);
            prefMenuView.show();
        });
        
        calculateButton.setOnAction((ActionEvent t) -> {
            DataQuery dataQuery = DataQueryFactory.makeDataQuery(DataTab.this.getText());
            
            DataQueryPopulator.populateDataQuery(dataQuery,
                    DataTab.this.locationBox.getValue().toString(),
                    new String[]{DataTab.this.startTimeField.getText(),
                        DataTab.this.startDateField.getText()},
                    new String[]{DataTab.this.endTimeField.getText(),
                        DataTab.this.endDateField.getText()},
                    DataTab.this.getCbTreeRoot());
            
            DataQueryValidityChecker dqValidityChecker =
                    DataQueryValidityChecker.makeDataQueryValidityChecker(
                            mainView, dataQuery);
            String dqValidity = dqValidityChecker.checkDataQueryValidity();
            if (dqValidity.equals(DQ_IS_VALID)) {
                DataTab.this.errorInfoLabel.setText(EMPTY_STR);
                RoadData[] datas = this.mainView.getViewModel()
                        .onCalculateButtonPress(dataQuery);
                DataVisualizer dv1 = DataVisualizer
                        .makeDataVisualizer(mainView, datas[0], dataQuery);
                
                if (datas.length > 1) {
                    DataVisualizer dv2 = DataVisualizer
                            .makeDataVisualizer(mainView, datas[1], dataQuery);
                    this.updateChart(dataQuery, dv1, dv2);
                }
                else {
                    this.updateChart(dataQuery, dv1);
                }
            }
            else {
                DataTab.this.errorInfoLabel.setText(dqValidity);
            }
        });
    }
    
    /**
     * An abstract getter-method for the root of the CheckBoxTree.
     * @return the root item of the CheckBoxTree.
     */
    public abstract TreeItem getCbTreeRoot();
    
    /**
     * A getter-method for the TabPane in which the data is visualized.
     * @return the TabPane in which the data is visualized.
     */
    public TabPane getChartTabPane() {
        return this.chartTabPane;
    }

    public ChoiceBox getLocationBox() {
        return locationBox;
    }

    public void setLocationBox(ChoiceBox locationBox) {
        this.locationBox = locationBox;
    }

    public TextField getStartTimeField() {
        return startTimeField;
    }

    public void setStartTimeField(TextField startTimeField) {
        this.startTimeField = startTimeField;
    }

    public TextField getStartDateField() {
        return startDateField;
    }

    public void setStartDateField(TextField startDateField) {
        this.startDateField = startDateField;
    }

    public TextField getEndTimeField() {
        return endTimeField;
    }

    public void setEndTimeField(TextField endTimeField) {
        this.endTimeField = endTimeField;
    }

    public TextField getEndDateField() {
        return endDateField;
    }

    public void setEndDateField(TextField endDateField) {
        this.endDateField = endDateField;
    }
    
    
    
    /**
     * An abstract method for updating the visualization of the data.
     * @param visualizers DataVisualizers used for visualizing the data.
     */
    public abstract void updateChart(DataQuery query, DataVisualizer... visualizers);
    
    public abstract void updateParams(DataQuery query);
}
