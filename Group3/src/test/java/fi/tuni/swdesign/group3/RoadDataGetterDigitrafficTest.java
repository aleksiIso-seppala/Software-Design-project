/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
public class RoadDataGetterDigitrafficTest {
    
    public RoadDataGetterDigitrafficTest() {
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
     * Test of getRoadConditionData method, of class RoadDataGetterDigitraffic.
     */
    @Test
    public void testGetRoadConditionData() throws Exception {
        System.out.println("getRoadConditionData");
        String location = "Helsinki";
        String minX = "24";
        String maxX = "25";
        String minY = "61";
        String maxY = "62";
        JsonObject result = RoadDataGetterDigitraffic.getRoadConditionData(location, minX, maxX, minY, maxY);
        assertTrue(result.size() > 0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }   
}
