/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * A subclass of DataTab for a tab in which the WeatherData parameters are set and
 * the WeatherData is visualized.
 * @author Lauri Puoskari
 */
public class WeatherDataTab extends DataTab {
    /**
     * The TreeView in which the parameter CheckBoxes are placed.
     */
    private TreeView checkBoxTree;
    
    /**
     * A constructor in which the visual elements of the WeatherDataTab and its 
     * functionality is initialized.
     * @param mainView the current instance of MainView.
     */
    WeatherDataTab(MainView mainView) {
        super(mainView);
        this.setText(WEATHER_DATA);
        GridPane gridPane = (GridPane) super.getContent();
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        gridPane.add(this.checkBoxTree, THIRD_COL, SECOND_ROW, 
                COL_SPAN_OF_1, ROW_SPAN_OF_2);
        
        TreeItem root = new TreeItem();
        this.checkBoxTree.setRoot(root);
        this.checkBoxTree.setShowRoot(false);
        WeatherDataCBTPopulator cbtPopulator = (WeatherDataCBTPopulator) 
                CheckBoxTreePopulator.makeCBTPopulator(mainView, this.getText());
        cbtPopulator.populateCheckBoxTree(this.checkBoxTree);
    }
       
    /**
     * A getter-method for the root of the CheckBoxTree. Overrides the abstract
     * method in base class DataTab
     * @return the root item of the CheckBoxTree.
     */
    @Override
    public TreeItem getCbTreeRoot() {
        return this.checkBoxTree.getRoot();
    }

    /**
     * A method for updating the data visualizations. Overrides the abstract
     * method in base class DataTab
     * @param visualizers the DataVisualizers used for the visualization.
     */
    @Override
    public void updateChart(DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        if (visualizers.length > 0) {
            WeatherDataVisualizer visualizer = (WeatherDataVisualizer) visualizers[0];
            visualizer.visualizeData();
        }
    }
}
