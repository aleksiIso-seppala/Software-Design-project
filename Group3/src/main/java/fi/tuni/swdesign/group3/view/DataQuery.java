/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;


/**
 *
 * @author Lauri Puoskari
 */
public abstract class DataQuery {
    
    protected String dataType;
    protected String location;
    protected String[] timelineStart;
    protected String[] timelineEnd;
    
    DataQuery(String dataType) {
        this.dataType = dataType;
        timelineStart = new String[2];
        timelineEnd = new String[2];
    }
    
    public String getDataType() {
        return this.dataType;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getLocation() {
        return this.location;
    }
    
    public void setTimelineStart(String[] timelineStart) {
        this.timelineStart = timelineStart;
    }
    
    public String[] getTimelineStart() {
        return this.timelineStart;
    }
    
    public void setTimelineEnd(String[] timelineEnd) {
        this.timelineEnd = timelineEnd;
    }
    
    public String[] getTimelineEnd() {
        return this.timelineEnd;
    }
    
    public abstract void testPrint();
}
