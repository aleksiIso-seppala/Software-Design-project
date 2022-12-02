/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import java.util.HashMap;
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
public class RoadTrafficDataTest {
    
    public RoadTrafficDataTest() {
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
     * Test of getNumberOfTrafficMessages method, of class RoadTrafficData.
     */
    @Test
    public void testGetNumberOfTrafficMessages() {
        System.out.println("getNumberOfTrafficMessages");
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        int expResult = 0;
        int result = instance.getNumberOfTrafficMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setNumberOfTrafficMessages method, of class RoadTrafficData.
     */
    @Test
    public void testSetNumberOfTrafficMessages() {
        System.out.println("setNumberOfTrafficMessages");
        int numberOfTrafficMessages = 15;
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        instance.setNumberOfTrafficMessages(numberOfTrafficMessages);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertEquals(numberOfTrafficMessages, instance.getNumberOfTrafficMessages());
    }

    /**
     * Test of getTemperature method, of class RoadTrafficData.
     */
    @Test
    public void testGetTemperature() {
        System.out.println("getTemperature");
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        String expResult = null;
        String result = instance.getTemperature();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setTemperature method, of class RoadTrafficData.
     */
    @Test
    public void testSetTemperature() {
        System.out.println("setTemperature");
        String temperature = "5";
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        instance.setTemperature("5");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertEquals(temperature, instance.getTemperature());
    }

    /**
     * Test of getOverAllCondition method, of class RoadTrafficData.
     */
    @Test
    public void testGetOverAllCondition() {
        System.out.println("getOverAllCondition");
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        String expResult = null;
        String result = instance.getOverAllCondition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setOverAllCondition method, of class RoadTrafficData.
     */
    @Test
    public void testSetOverAllCondition() {
        System.out.println("setOverAllCondition");
        String overAllCondition = "veriveribad";
        RoadTrafficData instance = new RoadTrafficData("Helsinki", "xx", "yy");
        instance.setOverAllCondition(overAllCondition);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        assertEquals(overAllCondition, instance.getOverAllCondition());
    }
}
