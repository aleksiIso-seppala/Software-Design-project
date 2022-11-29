/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
     * A method for updating the data visualizations.Overrides the abstract
     * method in base class DataTab
     * @param query the query used to fetch the data.
     * @param visualizers the DataVisualizers used for the visualization.
     */
    @Override
    public void updateChart(DataQuery query, DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        DataVisualizer visualizer = visualizers[0];
            if (visualizer != null) {
                WeatherDataVisualizer weatherDV = (WeatherDataVisualizer) visualizer;
                WeatherDataQuery weatherDQ = (WeatherDataQuery) query;
                weatherDV.setObsTypesToVisualize(weatherDQ.getSelectedObsParams());
                weatherDV.setPreTypesToVisualize(weatherDQ.getSelectedPreParams());
                weatherDV.setPerMonthTypesToVisualize(weatherDQ.getSelectedPerMonthParams());
                weatherDV.visualizeData();
            }
            else {
                Tab noDataTab = new Tab("No data");
                Label noDataLabel = new Label("No data was found.");
                noDataTab.setContent(noDataLabel);
                this.chartTabPane.getTabs().add(noDataTab);
            }
        
    }
}
