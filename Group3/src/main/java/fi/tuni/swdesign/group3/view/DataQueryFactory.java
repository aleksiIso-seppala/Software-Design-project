/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

/**
 *
 * @author Lauri
 */
public class DataQueryFactory {
    
    DataQueryFactory() {
    }
    
    DataQuery makeDataQuery(String dataType) {
        return switch (dataType) {
            case "Road data" -> new RoadDataQuery(dataType);
            case "Weather data" -> new WeatherDataQuery(dataType);
            default -> new CombinedDataQuery(dataType);
        };
    }
}
