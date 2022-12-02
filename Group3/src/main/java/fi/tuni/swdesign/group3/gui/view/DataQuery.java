package fi.tuni.swdesign.group3.gui.view;

/**
 * An abstract base class for storing the parameters given by the user into an
 * object.
 * @author Lauri Puoskari
 */
public abstract class DataQuery {
    
    /**
     * A string representing the datatype the query represents.
     */
    public String dataType;
    /**
     * The location parameter as a string.
     */
    protected String location;
    /**
     * The start of the timeline as a string array.
     */
    protected String[] timelineStart;
    /**
     * The end of the timeline as a string array.
     */
    protected String[] timelineEnd;
    
    /**
     * A constructor in which the data type of the query is stored and the 
     * string arrays are initialized.
     * @param dataType the data type of the query.
     */
    DataQuery(String dataType) {
        this.dataType = dataType;
        timelineStart = new String[2];
        timelineEnd = new String[2];
    }
    
    /**
     * A getter-method for the data type of the query.
     * @return a string representing the type of data.
     */
    public String getDataType() {
        return this.dataType;
    }
    
    /**
     * A setter-method for the location parameter of the query.
     * @param location the location paramter of the query.
     */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * A getter-method for the location paramter of the query.
     * @return the location parameter of the query.
     */
    public String getLocation() {
        return this.location;
    }
    
    /**
     * A setter-method for the start of the timeline of the query.
     * @param timelineStart a string array in which the first element is the 
     * time and second is the date of the start of the timeline.
     */
    public void setTimelineStart(String[] timelineStart) {
        this.timelineStart = timelineStart;
    }
    
    /**
     * A getter-method for the start of the timeline of the query.
     * @return a string array in which the first element is the 
     * time and second is the date of the start of the timeline.
     */
    public String[] getTimelineStart() {
        return this.timelineStart;
    }
    
    /**
     * A setter-method for the end of the timeline of the query.
     * @param timelineEnd a string array in which the first element is the 
     * time and second is the date of the end of the timeline.
     */
    public void setTimelineEnd(String[] timelineEnd) {
        this.timelineEnd = timelineEnd;
    }
    
    /**
     * A qetter-method for the end of the timeline of the query.
     * @return a string array in which the first element is the 
     * time and second is the date of the end of the timeline.
     */
    public String[] getTimelineEnd() {
        return this.timelineEnd;
    }
}