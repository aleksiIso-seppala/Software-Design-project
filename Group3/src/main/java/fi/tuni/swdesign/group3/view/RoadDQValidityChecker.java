/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

/**
 * A subclass of DataQueryValidityChecker for checking the validity of the 
 * RoadDataQuery created using the parameters given by the user.
 * @author Lauri Puoskari
 */
public class RoadDQValidityChecker extends DataQueryValidityChecker{
    /**
     * The RoadDataQuery to be checked.
     */
    private RoadDataQuery roadDataQuery;
    
    /**
     * A constructor in which the current instance of the MainView and DataQuery
     * as DataQuery are stored into the extended class and the DataQuery as 
     * RoadDataQuery is stored in this class.
     * @param mainView the current instance of MainView.
     * @param query the DataQuery to be checked.
     */
    RoadDQValidityChecker(MainView mainView, RoadDataQuery query) {
        super(mainView, query);
        this.roadDataQuery = query;
    }

    /**
     * A method for checking the validity of the RoadDataQuery. Implements
     * the abstract method of the base class DataQueryValidityChecker.
     * @return A string communicating the result of the check.
     */
    @Override
    public String checkDataQueryValidity() {
        String locationValidity = super.checkLocationValidity();
        if (!locationValidity.equals(DQ_IS_VALID)) {
            return locationValidity;
        }
        
        String dateTimeValidity = super.checkDateTimeValidity();
        if (!dateTimeValidity.equals(DQ_IS_VALID)) {
            return dateTimeValidity;
        }
        
        if (this.roadDataQuery.getSelectedForecasts().isEmpty() & 
                this.roadDataQuery.getSelectedTasks().isEmpty()) {
            return NO_PARAMS;
        }
        
        return DQ_IS_VALID;
    }
}
