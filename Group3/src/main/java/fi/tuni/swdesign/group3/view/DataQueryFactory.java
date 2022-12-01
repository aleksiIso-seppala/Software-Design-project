package fi.tuni.swdesign.group3.view;

/**
 * A factory class for creating different types of DataQuerys.
 * @author Lauri Puoskari
 */
public class DataQueryFactory {
    /**
     * A constant string representing road data.
     */
    private static final String ROAD_DATA = "Road data";
    /**
     * A constant string representing weather data.
     */
    private static final String WEATHER_DATA = "Weather data";
    /**
     * A constant string representing combined data.
     */
    private static final String COMBINED_DATA = "Combined data";
    
    /**
     * An empty constructor.
     */
    DataQueryFactory() {
    }
    
    /**
     * A factory-method for creating different types of DataQuerys.
     * @param dataType The type of the DataQuery to be created.
     * @return an instance of the chosen type of DataQuery. Null if dataType
     * not implemented.
     */
    public static DataQuery makeDataQuery(String dataType) {
        return switch (dataType) {
            case ROAD_DATA -> new RoadDataQuery(dataType);
            case WEATHER_DATA -> new WeatherDataQuery(dataType);
            case COMBINED_DATA -> new CombinedDataQuery(dataType);
            default -> null;
        };
    }
}
