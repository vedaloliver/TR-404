package sample;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

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