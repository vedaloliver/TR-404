package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class beatTicker {

    // layout
    private HBox tickerRowLayout;
    private BPM bpm;

    // Ticker elements
    /// Separate thread
    private Thread tickerThread;
    // provides the state of the tick
    private ObservableList<Node> tickStateList;

    public beatTicker(){
        this.tickerRowLayout= new HBox();
        this.bpm = new BPM();

        this.tickerThread = new Thread();
        this.tickStateList = tickerRowLayout.getChildren();
    }

    public BPM getBPM(){
        return this.bpm;
    }

    // Each node is a Label, with the 'ticker' being a letter
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

    // Loops creation to 16 beats
    public void beatMarkCreationLoop(int width, int height) {
        int beatNo = 1;
        for (int i = 0; i < 16; i++) {
            Label label = new Label();
            setSize(label, width, height);
            label.setText("  ■");

            label.setStyle("-fx-text-fill: #EEEEEE;-fx-font-weight: bold;-fx-font-size: 15px;");
            this.tickerRowLayout.getChildren().add(label);

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


    // Thread Creation and execution
    //  executor
    public void startTaskTick() {
        Runnable task = () -> runTaskTick();
        tickerThread = new Thread(task);
        tickerThread.setDaemon(true);
        tickerThread.start();
    }

    // Handles changing the tick state
    private void runTaskTick() {
        // the list of the tick nodes
        while(true){
            // int =1 as the first node is the "label" region
            for (int i=1;i<tickStateList.size();i++){
                try {
                    int finalI = i;
                    // turns the tick "on"
                    if (i == 1 ||i == 5||i == 9||i == 13){
                        Platform.runLater(() -> tickStateList.get(finalI).setStyle("-fx-text-fill: #FF0000 ;-fx-font-weight: bold;-fx-font-size: 15px;"));

                    }else {
                        Platform.runLater(() -> tickStateList.get(finalI).setStyle("-fx-text-fill: #00ff00 ;-fx-font-weight: bold;-fx-font-size: 15px;"));
                    }
                    //
                    Thread.sleep((long) (1000 * (60.0 / (this.bpm.getBPM() * 4))));
                    Platform.runLater(() -> tickStateList.get(finalI).setStyle("-fx-text-fill: #EEEEEE ;-fx-font-weight: bold;-fx-font-size: 15px;"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // ends the ticker
    public void cancelTickTask(){
        tickerThread.stop();
        for (int  i = 0;i<tickStateList.size();i++){
            tickStateList.get(i).setStyle("-fx-text-fill: #EEEEEE ;-fx-font-weight: bold;-fx-font-size: 15px;");
        }

    }



    }



