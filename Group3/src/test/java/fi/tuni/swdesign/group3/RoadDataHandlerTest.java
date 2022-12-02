/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import fi.tuni.swdesign.group3.view.DataQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
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
public class RoadDataHandlerTest {
    
    public RoadDataHandlerTest() {
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
     * Test of fetchRoadData method, of class RoadDataHandler.
     */
    @Test
    public void testFetchRoadData_String() {
        System.out.println("fetchRoadData");
        String location = "Helsinki";
        RoadDataHandler instance = new RoadDataHandler();
        RoadTrafficData expResult = new RoadTrafficData("Helsinki", "xx", "yy");
        RoadTrafficData result = instance.fetchRoadData(location);
        assertEquals(expResult.getLocation(), result.getLocation());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of fetchWeatherDataFuture method, of class RoadDataHandler.
     */
    @Test
    public void testFetchWeatherDataFuture() {
        System.out.println("fetchWeatherDataFuture");
        String location = "Helsinki";
        
        LocalDateTime dat = LocalDateTime.now(ZoneId.of("Europe/Helsinki")).plusDays(1);
        LocalDateTime future = dat.plusHours(12);
        
        String time = dat.toString().split("\\.", 2)[0]+"Z";
        String futureTime = future.toString().split("\\.", 2)[0]+"Z";
        
        RoadDataHandler instance = new RoadDataHandler();
        
        RoadWeatherData expResult = new RoadWeatherData("Helsinki", "xx", "yy");
        RoadWeatherData result = instance.fetchWeatherDataFuture(location, time, futureTime);
        
        assertEquals(expResult.getLocation(), result.getLocation());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of fetchWeatherDataPast method, of class RoadDataHandler.
     */
    @Test
    public void testFetchWeatherDataPast() {
        System.out.println("fetchWeatherDataPast");
        String location = "Helsinki";
        
        LocalDateTime dat = LocalDateTime.now(ZoneId.of("Europe/Helsinki")).minusDays(1);
        LocalDateTime future = dat.plusHours(12);
        
        
        String time = dat.toString().split("\\.", 2)[0]+"Z";
        String futureTime = future.toString().split("\\.", 2)[0]+"Z";
        
        RoadDataHandler instance = new RoadDataHandler();
        RoadWeatherData expResult = new RoadWeatherData("Helsinki", "xx", "yy");
        
        RoadWeatherData result = instance.fetchWeatherDataPast(location, time, futureTime);
        
        assertEquals(expResult.getLocation(), result.getLocation());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHardCodedLocations method, of class RoadDataHandler.
     */
    @Test
    public void testGetHardCodedLocations() {
        System.out.println("getHardCodedLocations");
        RoadDataHandler instance = new RoadDataHandler();

        
        HashMap<String, ArrayList<String>> result = instance.getHardCodedLocations();
        
        assertTrue(result.containsKey("Helsinki"));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMaintenanceTaskNames method, of class RoadDataHandler.
     */
    @Test
    public void testGetMaintenanceTaskNames() {
        System.out.println("getMaintenanceTaskNames");
        RoadDataHandler instance = new RoadDataHandler();
        ArrayList<String> result = instance.getMaintenanceTaskNames();
        assertTrue(result.contains("Brushing"));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
