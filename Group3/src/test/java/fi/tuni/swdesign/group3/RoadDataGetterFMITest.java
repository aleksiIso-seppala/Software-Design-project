/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.swdesign.group3;

import fi.tuni.swdesign.group3.api.RoadDataGetterFMI;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class RoadDataGetterFMITest {
    
    public RoadDataGetterFMITest() {
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
     * Test of getDOMDocument method, of class RoadDataGetterFMI.
     * @throws Exception
     */
    @Test
    public void testGetDOMDocument() throws Exception {
        System.out.println("getDOMDocument");
        String queryName = "fmi::observations::weather::simple";
        String minX = "23";
        String maxX = "24";
        String minY = "61";
        String maxY = "62";
        
        LocalDateTime dat = LocalDateTime.now(ZoneId.of("Europe/Helsinki")).minusDays(1);
        LocalDateTime future = dat.plusHours(12);
        
        String lat = "";
        String lon = "";
        String startTime = dat.toString().split("\\.", 2)[0]+"Z";
        String endTime = future.toString().split("\\.", 2)[0]+"Z";
        String parameters = "t2m,ws_10min,n_man,TA_PT1H_AVG,TA_PT1H_MAX,TA_PT1H_MIN";
        
        org.w3c.dom.Document result = RoadDataGetterFMI.getDOMDocument(queryName, minX, maxX, minY, maxY, lat, lon, startTime, endTime, parameters);
        assertTrue("#document".equals(result.getNodeName()));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
