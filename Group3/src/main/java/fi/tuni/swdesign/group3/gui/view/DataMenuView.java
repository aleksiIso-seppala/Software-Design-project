package fi.tuni.swdesign.group3.gui.view;

import fi.tuni.swdesign.group3.classes.RoadData;
import java.io.IOException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class for an external view in which the data is either saved or loaded.
 * @author Lauri Puoskari
 */
public class DataMenuView {
    /**
     * A constant representing the width of the menu.
     */
    private final static int MENU_WIDTH = 225;
    /**
     * A constant representing the height of the menu.
     */
    private final static int MENU_HEIGHT = 125;
    /**
     * A constant representing the length of a short horizontal gap between visual
     * elements.
     */
    private final static int SHORT_H_GAP = 10;
    /**
     * A constant representing the length of a vertical gap between visual
     * elements.
     */
    private final static int V_GAP = 10;
    /**
     * A constant representing the length of a short-width visual element.
     */
    private final static int SHORT_ELEMENT_WIDTH = 80;
    /**
     * A constant string representing the data menu.
     */
    private final static String DATA_MENU = "Data menu";
    /**
     * A constant string representing the prompt of saving or loading the data.
     */
    private final static String SAVE_OR_LOAD = "Save or load data:";
    /**
     * A constant string representing the saving the data.
     */
    private final static String SAVE = "Save";
    /**
     * A constant string representing loading the data.
     */
    private final static String LOAD = "Load";
    /**
     * A constant string representing the prompt of giving the name of the dataset.
     */
    private final static String DATASET_ID_PROMPT = "ID of the dataset (ASCII)";
    /**
     * A constant string representing an empty string.
     */
    private static final String EMPTY_STR = "";
    /**
     * A constant string for informing the user about an invalid ID.
     */
    private static final String INVALID_ID = "Invalid ID!";
    /**
     * A constant string for informing the user about an IOException.
     */
    private static final String IOEX_OCCURRED = "IOException occurred!";
    /**
     * A constant string for informing the user about a succesful save.
     */
    private static final String SAVE_OK = "Data successfully saved.";
    /**
     * A constant string representing successful process.
     */
    private static final String OK = "OK";
    /**
     * A constant string for informing the user about no data being found with
     * given ID.
     */
    private static final String NO_DATA_FOUND = "No data found.";
    /**
     * A constant string for the traffic message label.
     */
    private static final String TRAFFIC_MSG_AMOUNT = "Amount of traffic messages: " ;
    /**
     * A constant string for informing the user about a successful load.
     */
    private static final String LOAD_OK = "Data successfully loaded.";
    /**
     * A pattern for checking if the ID given by the user contains only ASCII-
     * characters.
     */
    private static final Pattern ASCII_PATTERN = Pattern.compile("\\p{ASCII}+");
    /**
     * A constant int representing a large font size.
     */
    private static final int LARGE_FONT = 12;
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
     * A constant representing a length span of two columns.
     */
    protected static final int COL_SPAN_OF_2 = 2;
    /**
     * A constant representing a length span of one row.
     */
    protected static final int ROW_SPAN_OF_1 = 1;
    /**
     * The current instance of MainView.
     */
    private MainView mainView;
    /**
     * The stage of this MenuView.
     */
    private final Stage stage;
    
    /**
     * A constructor in which the visual components and the functionality of the
     * MenuView are initialized.
     * @param mainView the current instance of MainView.
     */
    DataMenuView(MainView mainView) {
        // Initializing the window.
        this.mainView = mainView;
        this.stage = new Stage();
        this.stage.initOwner(this.mainView.getStage());
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setResizable(false);
        this.stage.setTitle(DATA_MENU);
        
        // Initializing the layout.
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        
        // Initializing the infoLabel.
        Label infoLabel = new Label(SAVE_OR_LOAD);
        infoLabel.setFont(new Font(LARGE_FONT));
        
        // Initializing the textfield for IDs.
        TextField dataSetIdField = new TextField();
        dataSetIdField.setPromptText(DATASET_ID_PROMPT);
        
        // Initializing the save and load button.
        Button saveButton = new Button(SAVE);
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button(LOAD);
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        // Adding components to the layout.
        gridPane.add(infoLabel, FIRST_COL, FIRST_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(dataSetIdField, FIRST_COL, SECOND_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(saveButton, FIRST_COL, THIRD_ROW);
        gridPane.add(loadButton, SECOND_COL, THIRD_ROW);
        
        // Initializing and setting the scene.
        Scene dataScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        this.stage.setScene(dataScene);
        saveButton.requestFocus();
        
        // Handling a click of the save button.
        saveButton.setOnAction((ActionEvent t) -> {
            String dataSetId = dataSetIdField.getText();
            // Checking if the given ID is valid.
            if (!ASCII_PATTERN.matcher(dataSetId).matches()) {
                infoLabel.setText(INVALID_ID);
            }
            else {
                String infoMessage = EMPTY_STR;
                Tab dataTab = this.mainView.getTabPane().getSelectionModel()
                        .getSelectedItem();
                // Saving the data using appropriate classes.
                if (dataTab instanceof RoadDataTab roadDataTab) {
                    try {
                        infoMessage = this.mainView.getViewModel()
                                .saveData(dataSetId, roadDataTab.getRecentData(), 
                                        roadDataTab.getRecentQuery());
                    } catch (IOException ex) {
                        infoMessage = IOEX_OCCURRED;
                    }
                }
                else if (dataTab instanceof WeatherDataTab weatherDataTab) {
                    try {
                        infoMessage = this.mainView.getViewModel()
                                .saveData(dataSetId, weatherDataTab.getRecentData(), 
                                        weatherDataTab.getRecentQuery());
                    } catch (IOException ex) {
                        infoMessage = IOEX_OCCURRED;
                    }
                }
                else if (dataTab instanceof CombinedDataTab combinedDataTab) {
                    try {
                        infoMessage = this.mainView.getViewModel()
                                .saveData(dataSetId, 
                                        combinedDataTab.getRecentRoadData(),
                                        combinedDataTab.getRecentWeatherData(),
                                        combinedDataTab.getRecentQuery());
                    } catch (IOException ex) {
                        infoMessage = IOEX_OCCURRED;
                    }
                }
                // Checking if save was succesful.
                if (infoMessage.equals(OK)) {
                    infoMessage = SAVE_OK;
                }
                // Informing the user.
                infoLabel.setText(infoMessage);
            }
        });
        
        // Handling a click of the load button.
        loadButton.setOnAction((ActionEvent t) -> {
            String infoMessage;
            RoadData[] datas;
            String dataSetId = dataSetIdField.getText();
            // Checking if the given ID is valid.
            if (!ASCII_PATTERN.matcher(dataSetId).matches()) {
                infoMessage = INVALID_ID;
            } 
            else {
                try {
                    // Loading the data and query associated with the data.
                    datas = DataMenuView.this.mainView.getViewModel()
                            .loadDataBase(dataSetId);
                    DataQuery dataQuery = DataMenuView.this.mainView
                            .getViewModel().loadPreferences(dataSetId);
                    // Checking if data was found.
                    if (datas == null) {
                        infoMessage = NO_DATA_FOUND;
                    } else {
                        // Making one DataVisualizer if only Road- or WeatherData
                        // was loaded, Making two if both.
                        DataVisualizer dv1 = DataVisualizer
                                .makeDataVisualizer(mainView, datas[0], dataQuery);
                        if (datas.length > 1) {
                            DataVisualizer dv2 = DataVisualizer
                                    .makeDataVisualizer(mainView, datas[1], 
                                            dataQuery);
                            DataMenuView.this
                                    .showLoadedData(dataSetIdField.getText(), 
                                            dv1, dv2);
                        } else {
                            DataMenuView.this
                                    .showLoadedData(dataSetIdField.getText(), dv1);
                        }
                        infoMessage = LOAD_OK;
                    }
                }
                catch (IOException ex) {
                    infoMessage = IOEX_OCCURRED;
                }
            }
            // Informing the user.
            infoLabel.setText(infoMessage);
        });
    }
    
    /**
     * A method for showing the MenuView.
     */
    public void show() {
        this.stage.show();
    }
    
    /**
     * A method for showing the loaded data in an external window.
     * @param dataSetId The ID of the dataset.
     * @param visualizers The DataVisualizers used for visualizing the data.
     */
    private void showLoadedData(String dataSetId, DataVisualizer... visualizers) {
        TabPane chartTabPane = new TabPane();
        // Checking and casting the DataVisualizers.
        RoadDataVisualizer roadDV = null;
        WeatherDataVisualizer weatherDV = null;
        for (DataVisualizer visualizer : visualizers) {
            if (visualizer instanceof RoadDataVisualizer roadDataVisualizer) {
                roadDV = roadDataVisualizer;
            } else if (visualizer instanceof 
                    WeatherDataVisualizer weatherDataVisualizer) {
                weatherDV = weatherDataVisualizer;
            }
        }
        // Initializing the traffic message label.
        Label trafficMsgLabel = new Label(EMPTY_STR);
        // Visualizing the data.
        if (roadDV != null) {
            roadDV.visualizeData(chartTabPane);
            trafficMsgLabel.setText(TRAFFIC_MSG_AMOUNT
                    + roadDV.getData().getNumberOfTrafficMessages());
            trafficMsgLabel.setAlignment(Pos.CENTER);
        }
        if (weatherDV != null) {
            weatherDV.visualizeData(chartTabPane);
        }
        // Initializing the external view.
        Stage loadStage = new Stage();
        loadStage.initOwner(this.mainView.getStage());
        loadStage.setTitle(dataSetId);
        VBox vBox = new VBox();
        vBox.setSpacing(V_GAP);
        Label titleLabel = new Label(dataSetId);
        titleLabel.setFont(new Font(LARGE_FONT));
        titleLabel.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleLabel, chartTabPane, trafficMsgLabel);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        loadStage.setScene(scene);
        loadStage.show();
    }
}