/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
/**
 * A class for the main view component.
 * @author Lauri Puoskari
 */
public class MainView extends Application {
    /**
     * A constant representing the width of the window.
     */
    private final static int WINDOW_WIDTH = 850;
    /**
     * A constant representing the height of the window.
     */
    private final static int WINDOW_HEIGHT = 600;
    /**
     * The TabPane containing the opened DataTabs.
     */
    private TabPane tabPane;
    /**
     * An ArrayList of all the locations implemented in the program.
     */
    private ArrayList<String> locations;
    /**
     * The stage of the MainView.
     */
    private Stage stage;
    /**
     * The main ViewModel component.
     */
    private ViewModel viewModel;
    
    /**
     * The method which initializes the MainView and the rest of the program.
     * @param stage the stage of the MainView.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("RoadCast");
        stage.setResizable(false);
        this.tabPane = new TabPane();
        
        this.tabPane.getTabs().add(new StartMenuTab(this));
        stage.setScene(new Scene(tabPane, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();
        
        ModelFactory mf = new ModelFactory();
        ViewModelFactory vmf = new ViewModelFactory(mf);
        this.viewModel = vmf.getViewModel();
        
        this.locations = this.viewModel.getLocations();
        
        //runAutoUpdate((Model) mf.getModel());
        
    }
    
    /*
    A way to autoupdate GUI if we want to
    private void runAutoUpdate(Model m){
        Thread thread = new Thread(()->{
            Random r = new Random();
            while(true){
                m.updateData();
                try {
                    Thread.sleep(r.nextInt(500)+1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();    
    }
*/
    /**
     * A getter-method for the TabPane which contains all the opened DataTabs.
     * @return TabPane which contains all the opened DataTabs.
     */
    public TabPane getTabPane() {
        return this.tabPane;
    }
    /**
     * A getter-method for all the implemented locations.
     * @return ArrayList of the implemented locations.
     */
    public ArrayList<String> getLocations() {
        return this.locations;
    }
    
    /**
     * A getter-method for the stage of the MainView.
     * @return the stage of the MainView.
     */
    public Stage getStage() {
        return this.stage;
    }
    
    /**
     * A getter-method for the main ViewModel component.
     * @return the main ViewModel component.
     */
    public ViewModel getViewModel() {
        return this.viewModel;
    }
       
    /**
     * A method for initializing the GUI outside of the class.
     */
    public void initGUI() {
        launch();
    }

}
