/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

/**
 *
 * @author Lauri Puoskari
 */
public class DataQueryFactory {
    
    DataQueryFactory() {
    }
    
    public static DataQuery makeDataQuery(String dataType) {
        return switch (dataType) {
            case "Road data" -> new RoadDataQuery(dataType);
            case "Weather data" -> new WeatherDataQuery(dataType);
            case "Combined data" -> new CombinedDataQuery(dataType);
            default -> null;
        };
    }
}
