/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.HashMap;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Lauri Puoskari
 */
public abstract class DataQuery {
    
    private String dataType;
    private String location;
    private String[] timelineStart;
    private String[] timelineEnd;
    
    DataQuery(String dataType) {
        this.dataType = dataType;
        timelineStart = new String[2];
        timelineEnd = new String[2];
    }
    
//    DataQuery makeDataQuery(String dataType) {
//        return switch (dataType) {
//            case "Road data" -> new RoadDataQuery();
//            case "Weather data" -> new WeatherDataQuery();
//            default -> new CombinedDataQuery();
//        };
//    }
    
    abstract void setParams(TreeItem root);
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setTimelineStart(String timelineStartTime, String timelineStartDate) {
        this.timelineStart[0] = timelineStartTime;
        this.timelineStart[1] = timelineStartDate;
    }
    
    public String[] getTimelineStart() {
        return this.timelineStart;
    }
    
    public void setTimelineEnd(String timelineEndTime, String timelineEndDate) {
        this.timelineEnd[0] = timelineEndTime;
        this.timelineEnd[1] = timelineEndDate;
    }
    
    public String[] getTimelineEnd() {
        return this.timelineEnd;
    }
}
