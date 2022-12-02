/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import fi.tuni.swdesign.group3.classes.RoadWeatherData;
import fi.tuni.swdesign.group3.api.RoadDataParserXML;
import fi.tuni.swdesign.group3.api.RoadDataGetterFMI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author jukka
 */
public class RoadDataParserXMLTest {
    LocalDateTime dt;
    org.w3c.dom.Document observationDOM;
    RoadWeatherData data;
    
    public RoadDataParserXMLTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
        LocalDateTime dat = LocalDateTime.now(ZoneId.of("Europe/Helsinki")).minusDays(1);
        LocalDateTime future = dat.plusHours(12);
        this.dt = dat;
        org.w3c.dom.Document DOM = RoadDataGetterFMI.getDOMDocument("fmi::observations::weather::simple",
                "23", "61", "24", "62", "", "", dat.toString().split("\\.", 2)[0]+"Z",
                future.toString().split("\\.", 2)[0]+"Z", "t2m,ws_10min,n_man,TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN");
        this.observationDOM = DOM;
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getDOMParsedDocument method, of class RoadDataParserXML.
     * @throws Exception
     */
    @org.junit.Test
    public void testGetDOMParsedDocument() throws Exception {
        System.out.println("getDOMParsedDocument");
        Document w3cDocument = this.observationDOM;
        String location = "Helsinki";
        String minX = "23";
        String maxX = "24";
        String minY = "61";
        String maxY = "62";
        String lat = "";
        String lon = "";
        String startTime = this.dt.toString().split("\\.", 2)[0]+"Z";
        RoadWeatherData expResult = new RoadWeatherData("Helsinki", "xx", this.dt.toString().split("\\.", 2)[0]+"Z");
        RoadWeatherData result = RoadDataParserXML.getDOMParsedDocument(w3cDocument, location, minX, maxX, minY, maxY, lat, lon, startTime);
        
        this.data = result;
        
        assertEquals(expResult.getLocation(), result.getLocation());
        assertEquals(expResult.getTime(), result.getTime());
        
    }

    /**
     * Test of setRoadWeatherData method, of class RoadDataParserXML.
     */
    @org.junit.Test
    public void testSetRoadWeatherData() {
        System.out.println("setRoadWeatherData");
        RoadWeatherData roadWeatherData = new RoadWeatherData("Helsinki", "xx", "xx");
        roadWeatherData.setTemperature(Float.parseFloat("-2.2"));
        
        String param = "temperature";
        String value = "-200.0";
        
        assertFalse(roadWeatherData.getTemperature() == Float.parseFloat(value));
        
        boolean expResult = true;
        RoadWeatherData result = RoadDataParserXML.setRoadWeatherData(roadWeatherData, param, value);
        assertEquals(expResult, result.getTemperature() == Float.parseFloat(value));
        
    }
}
