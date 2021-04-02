package sample;

import javafx.application.Application;
import javafx.stage.Stage;

// main class for execution
public class Main extends Application {
    @Override
    public void start(Stage window) throws Exception {
        GUI gui = new GUI();
        gui.showWindow(window);
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
}