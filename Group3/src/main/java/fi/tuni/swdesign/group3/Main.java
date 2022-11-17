package fi.tuni.swdesign.group3;
import fi.tuni.swdesign.group3.view.*;
/**
 *
 * Placeholder for main
 */
public class Main {
    public static void main(String args[]) throws Exception{
        RoadDataHandler handler = new RoadDataHandler();
        System.out.println(handler.fetchRoadData("Rovaniemi").getTemperature());
        System.out.println(handler.fetchWeatherData("Rovaniemi", "2022-11-16T10:00:00Z", "2022-11-16T22:00:00Z").getTemperature());
        MainView mainView = new MainView();
        mainView.initGUI();
    }
}
