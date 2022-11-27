/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

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
    private final static String DATASET_NAME_PROMPT = "Name of the dataset";
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
    private Stage stage;
    
    /**
     * A constructor in which the visual components and the functionality of the
     * MenuView are initialized.
     * @param mainView the current instance of MainView.
     */
    DataMenuView(MainView mainView) {
        this.mainView = mainView;
        this.stage = new Stage();
        
        this.stage.initOwner(this.mainView.getStage());
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setResizable(false);
        
        this.stage.setTitle(DATA_MENU);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        Label infoLabel = new Label(SAVE_OR_LOAD);
        infoLabel.setFont(new Font(16));
        TextField dataSetNameField = new TextField();
        dataSetNameField.setPromptText(DATASET_NAME_PROMPT);
        Button saveButton = new Button(SAVE);
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button(LOAD);
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        gridPane.add(infoLabel, FIRST_COL, FIRST_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(dataSetNameField, FIRST_COL, SECOND_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(saveButton, FIRST_COL, THIRD_ROW);
        gridPane.add(loadButton, SECOND_COL, THIRD_ROW);
        Scene dataScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        this.stage.setScene(dataScene);
        saveButton.requestFocus();
    }
    
    /**
     * A method for showing the MenuView.
     */
    public void show() {
        this.stage.show();
    }
}
