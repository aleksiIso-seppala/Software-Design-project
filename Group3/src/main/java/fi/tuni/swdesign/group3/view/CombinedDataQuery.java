package fi.tuni.swdesign.group3.view;


/**
 * A class for storing the CombinedData parameters given by the user into an
 * object.
 * @author Lauri Puoskari
 */
public class CombinedDataQuery extends DataQuery {

    /**
     * RoadDataQuery containing all the parameters regarding RoadData.
     */
    private RoadDataQuery subRoadDQ;
    /**
     * WeatherDataQuery containing all the parameters regarding WeatherData.
     */
    private WeatherDataQuery subWeatherDQ;
    
    /**
     * A constructor in which the data type of the query is stored into the 
     * base class.
     * @param dataType the data type of the DataQuery.
     */
    CombinedDataQuery(String dataType) {
        super(dataType);
    }
    
    /**
     * A getter-method for the RoadDataQuery containing all the parameters 
     * regarding RoadData.
     * @return RoadDataQuery containing all the parameters regarding RoadData.
     */
    public RoadDataQuery getSubRoadDQ() {
        return subRoadDQ;
    }

    /**
     * A setter-method for the RoadDataQuery containing all the parameters 
     * regarding RoadData.
     * @param subRoadDQ RoadDataQuery containing all the parameters regarding 
     * RoadData.
     */
    public void setSubRoadDQ(RoadDataQuery subRoadDQ) {
        this.subRoadDQ = subRoadDQ;
    }

    /**
     * A getter-method for the WeatherDataQuery containing all the parameters 
     * regarding WeatherData.
     * @return WeatherDataQuery containing all the parameters regarding 
     * WeatherData.
     */
    public WeatherDataQuery getSubWeatherDQ() {
        return subWeatherDQ;
    }

    /**
     * A setter-method for the WeatherDataQuery containing all the parameters 
     * regarding WeatherData.
     * @param subWeatherDQ WeatherDataQuery containing all the parameters 
     * regarding WeatherData.
     */
    public void setSubWeatherDQ(WeatherDataQuery subWeatherDQ) {
        this.subWeatherDQ = subWeatherDQ;
    }
}
