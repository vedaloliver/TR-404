package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class beatTicker {


    private ArrayList<Button> tickList;
    private HBox tickerRowLayout;

    public beatTicker(){
        this.tickList = new ArrayList<Button>();
        this.tickerRowLayout= new HBox();
    }

    // display for the instrument
    public void label(int width) {
        Label label = new Label();
        label.setMinWidth(width);
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf",13);
        label.setFont(font);
        label.setTextFill(Color.web("#eeeeee"));
        tickerRowLayout.getChildren().add(label);
    }

    public void setSize(Label button, int width, int height) {
        button.setMinWidth(width);
        button.setMinHeight(height);
    }

    public void design(int spacing, String style) {
        tickerRowLayout.setSpacing(spacing);
        tickerRowLayout.setStyle(style);
    }

    // Creates a number identifier for each beat
    public void beatMarkCreationLoop(int width, int height) {
        int beatNo = 1;
        for (int i = 0; i < 16; i++) {
            //Button button = new Button();
            Label label = new Label();
            setSize(label, width, height);
//            label.setText("    " + Integer.toString(i));
            label.setText("  ■");

            label.setStyle("-fx-text-fill: #EEEEEE;-fx-font-weight: bold;-fx-font-size: 15px;");
            this.tickerRowLayout.getChildren().add(label);

            //indentCreator();
        }
    }
    // finalises the row by creating it based on physical attributes
    public HBox rowCreation() {
        // spacing and colour background
        design(10, "-fx-background-color: #222831");
        // Label name and width (fone size/colour should be integrated)
        label(95);
        // iterates and creates the squares
        beatMarkCreationLoop(35, 50);

        return tickerRowLayout;
    }

    public HBox getTickerRowLayout(){
        return this.tickerRowLayout;
    }
    public void initaliserTicker(){
        rowCreation();
    }

    /// this works when you just have one
    /// so probably it would be good to have this on an animation timer or a loop???
    public void tickOn(int index){
        tickerRowLayout.getChildren().get(index).setStyle("-fx-text-fill: #00ff00 ;-fx-font-weight: bold;-fx-font-size: 15px;");
    }


}
