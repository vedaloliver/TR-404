package sample;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class beatGrid extends beatRow {
    // separate classes for each instrument
    private beatRow Kick;
    private beatRow Snare;
    private beatRow Hat;
    private beatRow Cowbell;
    private beatRow Clap;
    // beatTicker for showing beat position, similar to beatrow class with some differences
    private beatTicker ticker;


    // Master grid for applying above classes to
    private VBox grid;
    // state boolean for playback buttons
    public static boolean playState;


    // Buttons responsible for changing bpm
    private Button buttonUP;
    private Button buttonDown;

    // BPM display label
    private Label bpmLabel;



    public beatGrid() {
        this.Kick = new beatRow("Kick");
        this.Hat = new beatRow("Hat");
        this.Snare = new beatRow("Snare");
        this.Cowbell = new beatRow("Cowbell");
        this.Clap = new beatRow("Clap");
        this.ticker = new beatTicker();
        this.grid = new VBox();
        this.playState = true;
        this.buttonUP = new Button();
        this.buttonDown = new Button();
        this.bpmLabel = new Label(Integer.toString(getClap().getBpm().getBPM()));

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
    public beatTicker getTicker(){
        return this.ticker;
    }

    // creates row entities from beatrow and ticker classes
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

    // Stops loop by breaking while looping mechanic in beatrow
    public void stopTrigger() {
        playState = false;
    }

    // executes above commands and creates a grid
    public VBox execute() {
        init();
        addToVBox();
        return this.grid;
    }
    public void setBPMButtonStyle(Button button,String colour){
        button.setStyle(
                "-fx-background-color:" +
                        "#090a0c, " +
                        colour +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"+
                        "-fx-background-radius: 5,4,3,5;"+
                        "-fx-background-insets: 0,1,1,0;"+
                        "-fx-text-fill: white;"+
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"+
                        "-fx-font-family: Arial;"+
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);"+
                        "-fx-font-size: 3px;"+
                        "-fx-padding: 10 20 10 20;");




        button.setMinWidth(35);
        button.setMaxWidth(35);
        button.setMinHeight(22);
        button.setMaxHeight(22);
    }
    public void setButtonLightUpClick(Button button){
        final String[] string = {""};

        javafx.event.EventHandler handler = new EventHandler() {

            @Override
            public void handle(Event event) {
                setBPMButtonStyle(button,"linear-gradient(#FFFFE0 0%, #e6e600 5%, #191d22 100%), ");
            }
        };

        javafx.event.EventHandler handlerTwo = new EventHandler() {

            @Override
            public void handle(Event event) {
                setBPMButtonStyle(button,"linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), ");
            }
        };
        button.addEventHandler(MouseEvent.MOUSE_PRESSED,handler);
        button.addEventHandler(MouseEvent.MOUSE_RELEASED,handlerTwo);

    }

    // assigns the Button to increase each track by one in unison
    public Button buttonUp() {
        setBPMButtonStyle(buttonUP,"linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), ");
        setButtonLightUpClick(buttonUP);
        buttonUP.setOnAction(e -> {
            getClap().getBpm().bpmPlus();
            getKick().getBpm().bpmPlus();
            getSnare().getBpm().bpmPlus();
            getCowbell().getBpm().bpmPlus();
            getHat().getBpm().bpmPlus();
            getTicker().getBPM().bpmPlus();
            this.bpmLabel.setText(Integer.toString(getClap().getBpm().getBPM()));
        });
        return this.buttonUP;
    }

    // assigns the Button to decrease each track by one in unison
    public Button buttonDown() {
        setBPMButtonStyle(buttonDown,"linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), ");
        setButtonLightUpClick(buttonDown);

        buttonDown.setOnAction(e -> {
            getClap().getBpm().bpmMinus();
            getKick().getBpm().bpmMinus();
            getSnare().getBpm().bpmMinus();
            getCowbell().getBpm().bpmMinus();
            getHat().getBpm().bpmMinus();
            getTicker().getBPM().bpmMinus();

            // while just setting for one instrument it is reflective of tracks also
            this.bpmLabel.setText(Integer.toString(getClap().getBpm().getBPM()));
        });
        return this.buttonDown;
    }

    // returns the label
    public Label getBPMLabel() {
        this.bpmLabel.setAlignment(Pos.CENTER);

        this.bpmLabel.setStyle("-fx-text-fill: #FF0000; -fx-background-color: #000000;");
        Font font = Font.loadFont("file:resources/fonts/digital7.ttf", 55);
        this.bpmLabel.setFont(font);
        this.bpmLabel.setMinWidth(100);
        return this.bpmLabel;

    }

}

