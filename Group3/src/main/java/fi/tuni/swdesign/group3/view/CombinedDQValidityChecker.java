/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3.view;

/**
 * A subclass of DataQueryValidityChecker for checking the validity of the 
 * CombinedDataQuery created using the parameters given by the user.
 * @author Lauri Puoskari
 */
public class CombinedDQValidityChecker extends DataQueryValidityChecker {
    /**
     * The CombinedDataQuery to be checked.
     */
    private CombinedDataQuery combDataQuery;
    
    /**
     * A constructor in which the current instance of the MainView and DataQuery
     * as DataQuery are stored into the extended class and the DataQuery as 
     * CombinedDataQuery is stored in this class.
     * @param mainView the current instance of MainView.
     * @param query the DataQuery to be checked.
     */
    CombinedDQValidityChecker(MainView mainView, CombinedDataQuery query) {
        super(mainView, query);
        this.combDataQuery = query;
    }

    /**
     * A method for checking the validity of the CombinedDataQuery. Implements
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
        
        if (this.combDataQuery.getSubWeatherDQ().getSelectedObsParams().isEmpty() 
                & this.combDataQuery.getSubWeatherDQ().getSelectedPreParams().isEmpty()
                & this.combDataQuery.getSubWeatherDQ().getSelectedPerMonthParams().isEmpty()
                & this.combDataQuery.getSubRoadDQ().getSelectedForecasts().isEmpty()
                & this.combDataQuery.getSubRoadDQ().getSelectedTasks().isEmpty()) {
            return NO_PARAMS;
        }
//        RoadDQValidityChecker subRoadDQVC = 
//                (RoadDQValidityChecker) DataQueryValidityChecker
//                        .makeDataQueryValidityChecker(mainView, 
//                                combDataQuery.getSubRoadDQ());
//        WeatherDQValidityChecker subWeatherDQVC = 
//                (WeatherDQValidityChecker) DataQueryValidityChecker
//                        .makeDataQueryValidityChecker(mainView, 
//                                combDataQuery.getSubWeatherDQ());
//        
        
        return DQ_IS_VALID;
    }
}
