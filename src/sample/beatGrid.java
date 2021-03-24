package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class beatGrid extends beatRow {
    private beatRow Kick;
    private beatRow Snare;
    private beatRow Hat;
    private beatRow Cowbell;
    private beatRow Clap;

    private beatTicker ticker;


    // VBOX beatGrid
    private VBox grid;

    // Button which starts Looping
    private Button playButton;

    // Stop button
    private Button stopButton;
    public static boolean playState;

    // Time element
    private Time time;

    // bpm button linking to all
    private Button buttonUP;
    private Button buttonDown;
    // bpm label
    private Label bpmLabel;

    // ticker elements
    private Thread tickerThread;
    private ObservableList<Node> tickStateList;

    // functions as a container object for the individual rows
    public beatGrid() {
        this.Kick = new beatRow("Kick");
        this.Hat = new beatRow("Hat");
        this.Snare = new beatRow("Snare");
        this.Cowbell = new beatRow("Cowbell");
        this.Clap = new beatRow("Clap");

        this.ticker = new beatTicker();

        this.grid = new VBox();
        this.playButton = new Button();
        this.stopButton = new Button();
        this.playState = true;

        // time

        this.time = new Time();

        // bpm
        this.buttonUP = new Button();
        this.buttonDown = new Button();
        this.bpmLabel = new Label(Integer.toString(getClap().getBpm().getBPM()));

        // thread
        this.tickerThread = new Thread();
        this.tickStateList = ticker.getTickerRowLayout().getChildren();
    }

    /// getters////
    public beatRow getKick() {
        return this.Kick;
    }

    public beatRow getHat() {
        return this.Hat;
    }

    public beatRow getSnare() {
        return this.Snare;
    }

    public beatRow getCowbell() {
        return this.Cowbell;
    }

    public beatRow getClap() {
        return this.Clap;
    }

    // creates row entities from beatrow classes
    public void init() {
        Kick.initaliser();
        Hat.initaliser();
        Snare.initaliser();
        Cowbell.initaliser();
        Clap.initaliser();

        ticker.initaliserTicker();
    }

    public void addToVBox() {
        grid.setSpacing(5);
        grid.getChildren().add(Clap.gethButtonLayout());
        grid.getChildren().add(Cowbell.gethButtonLayout());
        grid.getChildren().add(Kick.gethButtonLayout());
        grid.getChildren().add(Hat.gethButtonLayout());
        grid.getChildren().add(Snare.gethButtonLayout());
        grid.getChildren().add(ticker.getTickerRowLayout());
    }


    // creates a check to see if the state is flagged as false(triggered off by off switch
    // if swtiched false(off) pressing play button allows it to start again


    public void setPlayState() {
        playState = true;
    }

    // Turns off the while looping mechanic in beatrow and stops the loop
    // nice as it lets the loop ride until the end
    public void stopTrigger() {
        playState = false;
    }


    // executes above commands and creates a grid
    public VBox execute() {
        init();
        addToVBox();
//        tickChange();
        return this.grid;
    }


    // Since i can only change the bpm of each track at a time i have to set the button on this class
    // assigns the Button to increase each track by one in unison
    public Button buttonUp() {
        buttonUP.setOnAction(e -> {
            getClap().getBpm().bpmPlus();
            getKick().getBpm().bpmPlus();
            getSnare().getBpm().bpmPlus();
            getCowbell().getBpm().bpmPlus();
            getHat().getBpm().bpmPlus();
            this.bpmLabel.setText(Integer.toString(getClap().getBpm().getBPM()));
        });
        return this.buttonUP;
    }

    // assigns the Button to decrease each track by one in unison
    public Button buttonDown() {
        buttonDown.setOnAction(e -> {
            getClap().getBpm().bpmMinus();
            getKick().getBpm().bpmMinus();
            getSnare().getBpm().bpmMinus();
            getCowbell().getBpm().bpmMinus();
            getHat().getBpm().bpmMinus();
            // while just setting for one instrument it is reflective of tracks also
            this.bpmLabel.setText(Integer.toString(getClap().getBpm().getBPM()));
        });
        return this.buttonDown;
    }

    // returns the label
    public Label getBPMLabel() {
        this.bpmLabel.setStyle("-fx-text-fill: #393e46; -fx-border-color:#00adb5; -fx-background-color: #EEEEEE;");
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf", 45);
        this.bpmLabel.setFont(font);
        this.bpmLabel.setMinWidth(130);
        return this.bpmLabel;

    }


    // Tick executor
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
                Thread.sleep((long) (1000 * (60.0 / (getClap().getBpmLong() * 4))));
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

