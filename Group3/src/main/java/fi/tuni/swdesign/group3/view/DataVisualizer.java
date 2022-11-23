/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import fi.tuni.swdesign.group3.*;
/**
 *
 * @author Lauri Puoskari
 */
public abstract class DataVisualizer {
    protected static final int GRID_CELL_WIDTH = 110;
    protected static final int GRID_CELL_HEIGHT = 50;
    protected static final int V_GAP = 10;
    protected static final String NO_DATA_STR = "No data was found.";
    protected MainView mainView;
    
    DataVisualizer(MainView mainView) {
        this.mainView = mainView;
    }
    
    public static DataVisualizer makeDataVisualizer(MainView mainView, RoadData data) {
        if (data instanceof RoadTrafficData) {
            return new RoadDataVisualizer(mainView, (RoadTrafficData) data);
        }
        else /*if (data instanceof RoadWeatherData)*/ {
            return new WeatherDataVisualizer(mainView, (RoadWeatherData) data);
        }
    }
    
    public abstract void visualizeData();
}
