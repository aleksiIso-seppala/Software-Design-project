/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

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
public class RoadWeatherDataTest {
    
    public RoadWeatherDataTest() {
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
     * Test of getTemperature method, of class RoadWeatherData.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        RoadWeatherData instance = new RoadWeatherData("Helsinki", "xx", "xx");
        float expResult = 0.0F;
        float result = instance.getTemperature();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setTemperature method, of class RoadWeatherData.
     */
    @Test
    public void testSetTemperature() {
        System.out.println("setTemperature");
        float temperature = 5.5F;
        RoadWeatherData instance = new RoadWeatherData("Helsinki", "xx", "xx");
        instance.setTemperature(5.5F);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertEquals(temperature, instance.getTemperature(), 5.5);
    }

    /**
     * Test of getWind method, of class RoadWeatherData.
     */
    @Test
    public void testGetWind() {
        System.out.println("getWind");
        RoadWeatherData instance = new RoadWeatherData("Helsinki", "xx", "xx");
        float expResult = 0.0F;
        float result = instance.getWind();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setWind method, of class RoadWeatherData.
     */
    @Test
    public void testSetWind() {
        System.out.println("setWind");
        float wind = 6.0F;
        RoadWeatherData instance = new RoadWeatherData("Helsinki", "xx", "xx");
        instance.setWind(6.0F);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertEquals(wind, instance.getWind(), 6.0);
    }
}
