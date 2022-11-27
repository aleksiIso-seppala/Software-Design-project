/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private final static int MENU_HEIGHT = 100;
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
    private final static int TITLE_FONT_SIZE = 16;
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
    private Stage stage;
    
    /**
     * A constructor in which the visual components and the functionality of the
     * MenuView are initialized.
     * @param mainView the current instance of MainView.
     */
    PreferencesMenuView(MainView mainView) {
        this.mainView = mainView;
        this.stage = new Stage();
        
        this.stage.initOwner(this.mainView.getStage());
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setResizable(false);
        
        this.stage.setTitle(PREF_MENU);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(V_GAP);
        gridPane.setHgap(SHORT_H_GAP);
        Label infoLabel = new Label(SAVE_OR_LOAD_PREF);
        infoLabel.setFont(new Font(TITLE_FONT_SIZE));
        Button saveButton = new Button(SAVE);
        saveButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        Button loadButton = new Button(LOAD);
        loadButton.setPrefWidth(SHORT_ELEMENT_WIDTH);
        gridPane.add(infoLabel, FIRST_COL, FIRST_ROW, 
                COL_SPAN_OF_2, ROW_SPAN_OF_1);
        gridPane.add(saveButton, FIRST_COL, SECOND_ROW);
        gridPane.add(loadButton, SECOND_COL, SECOND_ROW);
        Scene prefScene = new Scene(gridPane, MENU_WIDTH, MENU_HEIGHT);
        this.stage.setScene(prefScene);
    }
    
    /**
     * A method for showing the MenuView.
     */
    public void show() {
        this.stage.show();
    }
}
