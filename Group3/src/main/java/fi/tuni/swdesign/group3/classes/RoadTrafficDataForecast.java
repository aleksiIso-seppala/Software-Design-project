/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.classes;

/**
 * A subclass describing forecasts of roadtraffic
 * @author jukka
 */
public class RoadTrafficDataForecast extends RoadTrafficData{
    private String precipitation;
    private String friction;
    private String overAllcondition;
    private String visibility;
    private String windCondition;
    private boolean winterSlipperines;
    
    public RoadTrafficDataForecast(String location, String coordinates, String time){
        super(location, coordinates, time);
        
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getFriction() {
        return friction;
    }

    public void setFriction(String friction) {
        this.friction = friction;
    }

    public String getOverAllcondition() {
        return overAllcondition;
    }

    public void setOverAllcondition(String overAllcondition) {
        this.overAllcondition = overAllcondition;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isWinterSlipperines() {
        return winterSlipperines;
    }

    public void setWinterSlipperines(boolean winterSlipperines) {
        this.winterSlipperines = winterSlipperines;
    }
    
    public String getWindCondition() {
        return windCondition;
    }

    public void setWindCondition(String windCondition) {
        this.windCondition = windCondition;
    }
    
}
