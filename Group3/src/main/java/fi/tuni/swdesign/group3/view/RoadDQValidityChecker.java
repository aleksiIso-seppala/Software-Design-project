/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * @author Lauri
 */
public class RoadDQValidityChecker extends DataQueryValidityChecker{
    private RoadDataQuery query;
    
    RoadDQValidityChecker(MainView mainView, RoadDataQuery query) {
        super(mainView, query);
        this.query = query;
    }

    @Override
    public String checkDataQueryValidity() {
        String locationValidity = super.checkLocationValidity();
        if (!locationValidity.equals("Data query is valid.")) {
            return locationValidity;
        }
        
        String dateTimeValidity = super.checkDateTimeValidity();
        if (!dateTimeValidity.equals("Data query is valid.")) {
            return dateTimeValidity;
        }
        
        if (this.query.getSelectedForecasts().isEmpty() & this.query.getSelectedTasks().isEmpty()) {
            return "No data parameters chosen!";
        }
        
        return "Data query is valid.";
    }
}
