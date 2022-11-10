/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.util.HashMap;

/**
 *
 * @author Lauri Puoskari
 */
public abstract class DataQuery {
    private String location;
    private String timelineStart;
    private String timelineEnd;
    
    DataQuery(String location, String timelineStart, String timelineEnd) {
        this.location = location;
        this.timelineStart = timelineStart;
        this.timelineEnd = timelineEnd;
    }
}
