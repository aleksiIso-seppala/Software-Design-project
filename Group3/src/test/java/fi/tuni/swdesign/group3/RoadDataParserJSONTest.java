/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import fi.tuni.swdesign.group3.classes.RoadTrafficData;
import fi.tuni.swdesign.group3.api.RoadDataGetterDigitraffic;
import fi.tuni.swdesign.group3.api.RoadDataParserJSON;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jukka
 */
public class RoadDataParserJSONTest {
    
    public RoadDataParserJSONTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getRoadData method, of class RoadDataParserJSON.
     * @throws IOException
     */
    @Test
    public void testGetRoadData() throws IOException {
        System.out.println("getRoadData");
        String location = "Helsinki";
        String minX = "24";
        String maxX = "25";
        String minY = "60";
        String maxY = "61";
        JsonObject condition = RoadDataGetterDigitraffic.getRoadConditionData(location, minX, maxX, minY, maxY);
        JsonObject tasks = null;
        ArrayList<JsonObject> messageData = null;
        
        RoadTrafficData expResult = new RoadTrafficData("Helsinki", "xx", "yy");
        
        RoadTrafficData result = RoadDataParserJSON.getRoadData(location, minX, maxX, minY, maxY, condition, tasks, messageData);
        assertEquals(expResult.getLocation(), result.getLocation());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
