package fi.tuni.swdesign.group3.gui.view;

import fi.tuni.swdesign.group3.classes.RoadTrafficData;
import fi.tuni.swdesign.group3.classes.RoadWeatherData;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
    /**
     * RoadTrafficData containing the most recent data fetched in the current
     * runtime.
     */
    private RoadTrafficData recentRoadData;
    /**
     * RoadWeatherData containing the most recent data fetched in the current
     * runtime.
     */
    private RoadWeatherData recentWeatherData;
    /**
     * CombinedDataQuery containing the most recent query associated with the
     * most recent data fetch.
     */
    private CombinedDataQuery recentQuery;
    
    /**
     * A constructor in which the visual elements of the CombinedDataTab and its 
     * functionality is initialized.
     * @param mainView the current instance of MainView.
     */
    CombinedDataTab(MainView mainView) {
        // Initializing the default structure of DataTab.
        super(mainView);
        
        this.setText(COMBINED_DATA);
        GridPane gridPane = (GridPane) super.getContent();
        
        // Initializing the label for traffic messages.
        this.trafficMsgLabel = new Label(TRAFFIC_MSG_AMOUNT);
        this.trafficMsgLabel.setPrefWidth(DataTab.LONG_ELEMENT_WIDTH);
        
        // Initializing the CheckBoxTree.
        this.checkBoxTree = new TreeView();
        this.checkBoxTree.setPrefSize(DataTab.CHECK_BOX_TREE_WIDTH, DataTab.CHECK_BOX_TREE_HEIGHT);
        
        // Adding the additional visual components to the Tab.
        gridPane.add(this.trafficMsgLabel, THIRD_COL, FOURTH_ROW);
        gridPane.add(this.checkBoxTree, THIRD_COL, SECOND_ROW, 
                COL_SPAN_OF_1, ROW_SPAN_OF_2);
        
        // Populating the CheckBoxTree with appropriate parameters.
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
    
    /**
     * A getter-method for the traffic message label.
     * @return the label in which the amount of traffic messages is shown.
     */
    public Label getTrafficMsgLabel() {
        return trafficMsgLabel;
    }

    /**
     * A getter-method for the most recent RoadTrafficData fetched during 
     * current runtime.
     * @return the most recent RoadTrafficData fetched.
     */
    public RoadTrafficData getRecentRoadData() {
        return recentRoadData;
    }

    /**
     * A getter-method for the most recent RoadWeatherData fetched during 
     * current runtime.
     * @return the most recent RoadWeatherData fetched.
     */
    public RoadWeatherData getRecentWeatherData() {
        return recentWeatherData;
    }

    /**
     * A getter method for the CombinedDataQuery associated with the most
     * recent data fetch.
     * @return CombinedDataQuery associated with the most recent data fetch.
     */
    public CombinedDataQuery getRecentQuery() {
        return recentQuery;
    }
    
    /**
     * A method for updating the data visualizations. Overrides the abstract
     * method in base class DataTab
     * @param query the query used to fetch the data.
     * @param visualizers the DataVisualizers used for the visualization.
     */
    @Override
    public void updateChart(DataQuery query, DataVisualizer... visualizers) {
        this.chartTabPane.getTabs().clear();
        
        // Visualizing the Road- and WeatherData individually.
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
            this.trafficMsgLabel.setText(TRAFFIC_MSG_AMOUNT 
                    + this.recentRoadData.getNumberOfTrafficMessages());
        }
        if (weatherDV != null) {
            weatherDV.visualizeData(this.chartTabPane);
            this.recentWeatherData = weatherDV.getData();
        }
        this.recentQuery = (CombinedDataQuery) query;
    }

    /**
     * A method for updating the parameters according to the loaded preferences.
     * Overrides the abstract method in base class DataTab.
     * @param query DataQuery containing the loaded preferences.
     */
    @Override
    public void updateParams(DataQuery query) {
        CombinedDataQuery combinedDQ = (CombinedDataQuery) query;
        
        // Updating the LocationChoiceBox.
        super.locationBox.getSelectionModel().select(combinedDQ.getLocation());
        
        // Updating the CheckBoxTree.
        TreeItem root = this.checkBoxTree.getRoot();
        for (Object object : root.getChildren()) {
            TreeItem item = (TreeItem) object;
            for (Object subObject : item.getChildren()) {
                TreeItem subItem = (TreeItem) subObject;
                if (item.getValue().equals(MAINTENANCE)) {
                    CheckBox taskBox = (CheckBox) subItem.getValue();
                    if (combinedDQ.getSubRoadDQ().getSelectedTasks()
                            .contains(taskBox.getText())) {
                        taskBox.setSelected(true);
                    }
                }
                else if (item.getValue().equals(COND_FORECAST)) {
                    if (subItem.getValue() instanceof CheckBox checkBox) {
                        if (combinedDQ.getSubRoadDQ().getSelectedForecasts()
                                .contains(checkBox.getText())) {
                            checkBox.setSelected(true);
                        }
                    }
                    else {
                        TreeItem toggleGroupItem = 
                                (TreeItem) subItem.getChildren().get(TOGGLE_GROUP_I);
                        HBox hBox = (HBox) toggleGroupItem.getValue();
                        for (var node : hBox.getChildren()) {
                            RadioButton timeButton = (RadioButton) node;
                            if (combinedDQ.getSubRoadDQ().getForecastTime()
                                    .equals(timeButton.getText())) {
                                timeButton.setSelected(true);
                            }
                        }
                    }
                }
                if (item.getValue().equals(OBS_VALUES)) {
                    CheckBox obsValueBox = (CheckBox) subItem.getValue();
                    if (combinedDQ.getSubWeatherDQ().getSelectedObsParams()
                            .contains(obsValueBox.getText())) {
                        obsValueBox.setSelected(true);
                    }
                }
                if (item.getValue().equals(PRE_VALUES)) {
                    CheckBox preValueBox = (CheckBox) subItem.getValue();
                    if (combinedDQ.getSubWeatherDQ().getSelectedPreParams()
                            .contains(preValueBox.getText())) {
                        preValueBox.setSelected(true);
                    }
                }                
                if (item.getValue().equals(PER_MONTH_VALUES)) {
                    CheckBox dailyValueBox = (CheckBox) subItem.getValue();
                    if (combinedDQ.getSubWeatherDQ().getSelectedPerMonthParams()
                            .contains(dailyValueBox.getText())) {
                        dailyValueBox.setSelected(true);
                    }
                }
            }
            
        }
    }
}
