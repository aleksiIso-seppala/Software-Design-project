/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Lauri
 */
public class CombinedDQValidityChecker extends DataQueryValidityChecker {
    private CombinedDataQuery query;
    
    CombinedDQValidityChecker(MainView mainView, CombinedDataQuery query) {
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
        
        if (this.query.getSelectedObsParams().isEmpty() 
                & this.query.getSelectedPreParams().isEmpty()
                & this.query.getSelectedPerMonthParams().isEmpty()
                & this.query.getSelectedForecasts().isEmpty()
                & this.query.getSelectedTasks().isEmpty()) {
            return "No data parameters chosen!";
        }
        
        return "Data query is valid.";
    }
}
