package fi.tuni.swdesign.group3;
import fi.tuni.swdesign.group3.gui.view.MainView;

/**
 * One main to run them all, one main to find them, 
 * One main to bring them all and in to RoadCast bind them.
 * @author Amanda, Aleksi, Jukka, Lauri
 */
public class Main {

    /**
     * main class to run it all
     * @param args just to satisfy javagods
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        MainView mainView = new MainView();
        mainView.initGUI();
    }
}
