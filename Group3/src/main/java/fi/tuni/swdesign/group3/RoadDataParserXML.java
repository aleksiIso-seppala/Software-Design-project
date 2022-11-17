/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.group3;

import com.google.gson.JsonObject;
import static fi.tuni.swdesign.group3.RoadDataParser.readFirstCondition;
import static fi.tuni.swdesign.group3.RoadDataParser.readMaintenanceTasks;
import static fi.tuni.swdesign.group3.RoadDataParser.readTrafficMessages;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author amand
 */
public class RoadDataParserXML {
    /*public static RoadWeatherData getWeatherData(String location, String minX, String maxX,
            String minY, String maxY, JsonObject condition, JsonObject tasks, ArrayList<JsonObject> messageData) throws IOException{
        
        var roadData = readFirstCondition(location, minX, maxX, minY, maxY, condition);
        
        if(tasks != null){
            var maintenanceTasks = readMaintenanceTasks(tasks);
            roadData.setMaintenanceTasks(maintenanceTasks);
        }
        if(messageData != null){
            int messages = readTrafficMessages(messageData);
            roadData.setNumberOfTrafficMessages(messages);            
        }

        
        return roadData;
    }*/
}
