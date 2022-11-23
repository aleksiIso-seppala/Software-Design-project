/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

/**
 *
 * @author jukka
 * An abstract class of RoadData. Extended by different under-modules.
 */
public abstract class RoadData {
    protected String location;
    protected String coordinates;
    protected String time;
    
    /*
    * Default constructor for a RoadData
    * @param location The location of the data as in place/town/city
    * @param coordinates The coordinates of the location
    */
    public RoadData(String location, String coordinates, String time){
        this.location = location;
        this.coordinates = coordinates;
        this.time = time;
    }
    
    /**
     * Override method for toString()
     * @return the object stringified
     */
    @Override
    public String toString(){
        return this.location+" - "+this.coordinates;
    }
    
    /*
    * Setter for location
    */
    public void setLocation(String location){
        this.location = location;
    }
    
    /*
    * Setter for coordinates
    */
    public void setCoordinates(String coordinates){
        this.coordinates = coordinates;
    }
    
    /*
    * Getter for location
    * @return the location as a string
    */
    public String getLocation(){
        return this.location;
    }
    
    /*
    * Getter for coordinates
    * @return the coordinates as a string
    */
    public String getCoordinates(){
        return this.coordinates;
    }
    
    /**
     * Getter for time
     * @return time as a string
     */
    public String getTime(){
        return this.time;
    }
    
    /**
     * Setter for time
     * @param time as a string
     */
    public void setTime(String time){
        this.time = time;
    }
}
