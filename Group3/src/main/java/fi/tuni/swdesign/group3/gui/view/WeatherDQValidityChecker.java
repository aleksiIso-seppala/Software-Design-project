package fi.tuni.swdesign.group3.gui.view;

/**
 * A subclass of DataQueryValidityChecker for checking the validity of the 
 * WeatherDataQuery created using the parameters given by the user.
 * @author Lauri Puoskari
 */
public class WeatherDQValidityChecker extends DataQueryValidityChecker{
    /**
     * The WeatherDataQuery to be checked.
     */
     private WeatherDataQuery weatherDataQuery;
    
     /**
     * A constructor in which the current instance of the MainView and DataQuery
     * as DataQuery are stored into the extended class and the DataQuery as 
     * WeatherDataQuery is stored in this class.
     * @param mainView the current instance of MainView.
     * @param query the DataQuery to be checked.
     */
    WeatherDQValidityChecker(MainView mainView, WeatherDataQuery query) {
        super(mainView, query);
        this.weatherDataQuery = query;
    }

    /**
     * A method for checking the validity of the WeatherDataQuery. Implements
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
        
        // Checking no parameters were selected in the CheckBoxTree.
        if (this.weatherDataQuery.getSelectedObsParams().isEmpty()
                & this.weatherDataQuery.getSelectedPreParams().isEmpty()
                & this.weatherDataQuery.getSelectedPerMonthParams().isEmpty()) {
            return NO_PARAMS;
        }
        
        return DQ_IS_VALID;
    }
}
