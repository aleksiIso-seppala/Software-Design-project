/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.RoadTrafficData;
import fi.tuni.swdesign.group3.RoadWeatherData;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

/**
 * A subclass of DataTab for a tab in which the CombinedData parameters are set and
 * the CombinedData is visualized.
 * @author Lauri Puoskari
 */
public class CombinedDataTab extends DataTab{
    /**
     * The TreeView in which the parameter CheckBoxes are placed.
     */
    private TreeView checkBoxTree;
    /**
     * The label that shows the current amount of traffic messages.
     */
    private Label trafficMsgLabel;
    
    private RoadTrafficData recentRoadData;
    private RoadWeatherData recentWeatherData;
    private CombinedDataQuery recentQuery;
    
    /**
     * A constructor in which the visual elements of the CombinedDataTab and its 
     * functionality is initialized.
     * @param mainView the current instance of MainView.
     */
    CombinedDataTab(MainView mainView) {
        super(mainView);
        this.setText(COMBINED_DATA);
        GridPane gridPane = (GridPane) super.getContent();
        
        this.trafficMsgLabel = new Label(TRAFFIC_MSG_AMOUNT);
        this.trafficMsgLabel.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        gridPane.add(this.trafficMsgLabel, THIRD_COL, FOURTH_ROW);
        gridPane.add(this.checkBoxTree, THIRD_COL, SECOND_ROW, 
                COL_SPAN_OF_1, ROW_SPAN_OF_2);
        
        TreeItem root = new TreeItem();
        this.checkBoxTree.setRoot(root);
        this.checkBoxTree.setShowRoot(false);
        CombinedDataCBTPopulator cbtPopulator = (CombinedDataCBTPopulator) 
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

    public RoadTrafficData getRecentRoadData() {
        return recentRoadData;
    }

    public RoadWeatherData getRecentWeatherData() {
        return recentWeatherData;
    }

    public CombinedDataQuery getRecentQuery() {
        return recentQuery;
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
        RoadDataVisualizer roadDV = null;
        WeatherDataVisualizer weatherDV = null;
        for (DataVisualizer visualizer : visualizers) {
            if (visualizer instanceof RoadDataVisualizer roadDataVisualizer) {
                roadDV = roadDataVisualizer;
            } else if (visualizer instanceof WeatherDataVisualizer weatherDataVisualizer) {
                weatherDV = weatherDataVisualizer;
            }
        }
        if (roadDV != null) {
            roadDV.visualizeData(this.chartTabPane);
            this.recentRoadData = roadDV.getData();
        }
        if (weatherDV != null) {
            weatherDV.visualizeData(this.chartTabPane);
            this.recentWeatherData = weatherDV.getData();
        }
        this.recentQuery = (CombinedDataQuery) query;
    }
    
    /**
     * A getter-method for the traffic message label.
     * @return the label in which the amount of traffic messages is shown.
     */
    public Label getTrafficMsgLabel() {
        return trafficMsgLabel;
    }

    @Override
    public void updateParams(DataQuery query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
