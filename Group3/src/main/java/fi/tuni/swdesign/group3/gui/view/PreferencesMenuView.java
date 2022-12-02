package fi.tuni.swdesign.group3.gui.view;

import java.io.IOException;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A class for an external view in which the preferences are either saved or loaded.
 * @author Lauri Puoskari
 */
public class PreferencesMenuView {
    
    /**
     * A constant representing the width of the MenuView.
     */
    private final static int MENU_WIDTH = 225;
    /**
     * A constant representing the height of the MenuView.
     */
    private final static int MENU_HEIGHT = 125;
    /**
     * A constant representing the length of a short horizontal gap between
     * visual elements.
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
     * A constant representing the first row of a GridPane.
     */
    private final static int FIRST_ROW = 0;
    /**
     * A constant representing the first column of a GridPane.
     */
    private final static int FIRST_COL = 0;
    /**
     * A constant representing the 2nd row of a GridPane.
     */
    private final static int SECOND_ROW = 1;
    /**
     * A constant representing the 2nd column of a GridPane.
     */
    private final static int SECOND_COL = 1;
    /**
     * A constant representing the 3rd row of a GridPane.
     */
    private final static int THIRD_ROW = 2;
    /**
     * A constant representing the lenght of a column span of two.
     */    
    private final static int COL_SPAN_OF_2 = 2;
    /**
     * A constant representing the lenght of a row span of one.
     */
    private final static int ROW_SPAN_OF_1 = 1;
    /**
     * A constant representing the font size of a title.
     */
    private final static int TITLE_FONT_SIZE = 12;
    /**
     * A pattern for checking if the string only contains ASCII-characters.
     */
    private static final Pattern ASCII_PATTERN = Pattern.compile("\\p{ASCII}+");
    /**
     * A constant string used for prompting the user to input the ID of the
     * preference.
     */
    private final static String PREF_ID_PROMPT = "ID for the preference (ASCII)";
    /**
     * A constant string used for informing the user that the given ID is invalid.
     */
    private final static String INVALID_ID = "Invalid ID!";
    /**
     * A constant string used for informing that the DataQuery is valid.
     */
    private final static String DQ_IS_VALID = "Data query is valid.";
    /**
     * A constant string used for informing the user that the parameters in the 
     * currently active DataTab are invalid.
     */
    private final static String INVALID_PARAMS = "Invalid parameters in DataTab!";
    /**
     * A constant string used for informing the user that an IOException has
     * occurred.
     */
    private final static String IOEX_OCCURRED = "IOException occurred!";
    /**
     * A constant string used to determine if the process succeeded in the
     * ViewModel and Model.
     */
    private final static String OK = "OK";
    /**
     * A constant string used for informing the user that the preferences were
     * successfully saved.
     */
    private final static String SAVE_OK = "Preferences successfully saved.";
    /**
     * A constant string used for informing the user that no preferences matching
     * the given ID were found.
     */
    private final static String NO_PREF_FOUND = "No preferences found.";
    /**
     * A constant string used for informing the user that the preferences were
     * successfully loaded.
     */
    private final static String LOAD_OK = "Preferences successfully loaded.";
    /**
     * A constant strign used for informing the user that the type of the
     * preferences and the DataTab are incompatible.
     */
    private final static String INVALID_MATCH = "Preferences and DataTab do not\nmatch!";
    /**
     * A constant string used for informing the user that the operation hasn't
     * been implemented to the given type of DataTab yet.
     */
    private final static String INVALID_TAB = "Invalid DataTab!";
    /**
     * A constant string representing the title of the preferences menu.
     */
    private final static String PREF_MENU = "Preferences menu";
    /**
     * A constant string representing the prompt of saving or loading preferences.
     */
    private final static String SAVE_OR_LOAD_PREF = "Save or load preferences:";
    /**
     * A constant string representing the saving preferences.
     */
    private final static String SAVE = "Save";
    /**
     * A constant string representing the loading preferences.
     */
    private final static String LOAD = "Load";
    /**
     * The current instance of MainView.
     */
    private MainView mainView;
    /**
     * The stage of the MenuView.
     */
    private final Stage stage;
    
    /**
     * A constructor in which the visual components and the functionality of the
     * MenuView are initialized.
     * @param mainView the current instance of MainView.
     */
    PreferencesMenuView(MainView mainView) {
        // Initializing the window.
        this.mainView = mainView;
        this.stage = new Stage();
        this.stage.initOwner(this.mainView.getStage());
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setResizable(false);
        this.stage.setTitle(PREF_MENU);
        
        // Initializing the layout.
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        
        // Initializing the infoLabel.
        Label infoLabel = new Label(SAVE_OR_LOAD_PREF);
        infoLabel.setFont(new Font(TITLE_FONT_SIZE));
        
        // Initializing the PreferencesID TextField.
        TextField prefIdField = new TextField();
        prefIdField.setPromptText(PREF_ID_PROMPT);
        
        // Initializing the Save and Load button.
        Button saveButton = new Button(SAVE);
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button(LOAD);
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        
        // Adding the visual components into the layout.
        gridPane.add(infoLabel, FIRST_COL, FIRST_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(prefIdField, FIRST_COL, SECOND_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(saveButton, FIRST_COL, THIRD_ROW);
        gridPane.add(loadButton, SECOND_COL, THIRD_ROW);
        
        // Initializing and setting the scene.
        Scene prefScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        this.stage.setScene(prefScene);
        saveButton.requestFocus();
        
        // Handling a click of the Save button.
        saveButton.setOnAction((ActionEvent t) -> {
            String prefId = prefIdField.getText();
            // Checking if the given ID is valid.
            if (!ASCII_PATTERN.matcher(prefId).matches()) {
                infoLabel.setText(INVALID_ID);
            }
            else {
                String infoMessage;
                DataTab dataTab = (DataTab) this.mainView.getTabPane()
                        .getSelectionModel().getSelectedItem();
                // Making and populating a DataQuery based on the parameters on 
                // the currently active DataTab.
                DataQuery query = DataQueryFactory.makeDataQuery(dataTab.getText());
                DataQueryPopulator.populateDataQuery(query, 
                        dataTab.getLocationBox().getSelectionModel().getSelectedItem().toString(), 
                        new String[]{dataTab.getStartTimeField().getText(), 
                        dataTab.getStartDateField().getText()},
                        new String[]{dataTab.getEndTimeField().getText(), 
                            dataTab.getEndDateField().getText()},
                        dataTab.getCbTreeRoot());
                // Checking if the DataQuery is valid.
                DataQueryValidityChecker dQChecker = 
                        DataQueryValidityChecker.makeDataQueryValidityChecker(this.mainView, query);
                String dqValidity = dQChecker.checkDataQueryValidity();
                if (!dqValidity.equals(DQ_IS_VALID)) {
                    infoMessage = INVALID_PARAMS;
                }
                else {
                    // Saving the preferences.
                    try {
                        infoMessage = this.mainView.getViewModel().savePreferences(query, prefId);
                    } catch (IOException ex) {
                        infoMessage = IOEX_OCCURRED;
                    }
                    
                }

                // Checking if the operation succeeded.
                if (infoMessage.equals(OK)) {
                    infoMessage = SAVE_OK;
                }
                // Informing the user.
                infoLabel.setText(infoMessage);
            }
        });
        
        // Handling a click of the Load button.
        loadButton.setOnAction((ActionEvent t) -> {
            String infoMessage;
            String prefId = prefIdField.getText();
            // Checking if the given ID is valid.
            if (!ASCII_PATTERN.matcher(prefId).matches()) {
                infoMessage = INVALID_ID;
            } 
            else {
                try {
                    // Loading the preferences.
                    DataQuery query = this.mainView.getViewModel()
                            .loadPreferences(prefIdField.getText());
                    if (query == null) {
                        infoMessage = NO_PREF_FOUND;
                    }
                    else {
                        // Updating the parameters if the DataQuery matches the
                        // currently active DataTab.
                        DataTab dataTab = (DataTab) this.mainView.getTabPane()
                            .getSelectionModel().getSelectedItem();
                        if (dataTab instanceof RoadDataTab) {
                            if (query instanceof RoadDataQuery) {
                                dataTab.updateParams(query);
                                infoMessage = LOAD_OK;
                            }
                            else {
                                infoMessage = INVALID_MATCH;
                            }
                        }
                        else if (dataTab instanceof WeatherDataTab) {
                            if (query instanceof WeatherDataQuery) {
                                dataTab.updateParams(query);
                                infoMessage = LOAD_OK;
                            }
                            else {
                                infoMessage = INVALID_MATCH;
                            }
                        }
                        else if (dataTab instanceof CombinedDataTab) {
                            if (query instanceof CombinedDataQuery) {
                                dataTab.updateParams(query);
                                infoMessage = LOAD_OK;
                            }
                            else {
                                infoMessage = INVALID_MATCH;
                            }
                        }
                        else {
                            infoMessage = INVALID_TAB;
                        }
                    }
                } catch (IOException ex) {
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
}
