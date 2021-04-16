package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.scene.text.Font;

public class GUI {
    // Colours of background
    private String backgroundColour;
    // Layout init
    private BorderPane layout;
    // Scene
    private Scene scene;


    //beatgrid init
    private beatGrid grid;
    private VBox gridVbox;

    // Top region Gui containers
    private VBox seekerRow;
    private VBox timeRow;
    private VBox volumeControlRow;
    // seekerbuttons
    private Button playButton;
    private Button stopButton;
    private Button resetButton;

    // time region
    private Time time;

    public GUI(){
        this.backgroundColour = "-fx-background-color: #222831";
        this.layout = new BorderPane();
        this.grid = new beatGrid();
        this.gridVbox = new VBox();
        this.seekerRow = new VBox();
        this.timeRow = new VBox();
        this.volumeControlRow = new VBox();
        this.playButton = new Button();
        this.stopButton = new Button();
        this.resetButton = new Button();
        this.time = new Time();
        this.scene = new Scene(layout);

    }
    ///////// ELEMENT FORMATTING ////////////////////
    public void labelFormat(Label label) {
        label.setAlignment(Pos.CENTER);

        label.setStyle("-fx-text-fill: #FF0000; -fx-background-color: #000000;");
        Font font = Font.loadFont("file:resources/fonts/digital7.ttf",55);
        label.setMinWidth(120);
        label.setFont(font);

    }

    ///////// MAIN BEATGRID UI /////////////////////

    // Returns the main sequencing element
    public VBox beatGridGUI(){
        gridVbox.setStyle(backgroundColour);
        gridVbox.getChildren().add(grid.execute());
        return gridVbox;
    }


    ///////// TOP REGION GUI ( CONTROL PANEL) /////////////////
    /// SEEK REGION ///
    // Changes the button and the sizing for the seek elements
    public void seekButtonStyling(Button button, String colour){
        button.setStyle(
                "-fx-background-color:" +
                        "#090a0c, " +
                        "linear-gradient"+colour +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"+
                        "-fx-background-radius: 5,4,3,5;"+
                        "-fx-background-insets: 0,1,1,0;"+
                        "-fx-text-fill: white;"+
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"+
                        "-fx-font-family: Arial;"+
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);"+
                        "-fx-font-size: 3px;"+
                        "-fx-padding: 10 20 10 20;");

        button.setMinWidth(55);
        button.setMaxWidth(55);
        button.setMinHeight(40);
        button.setMaxHeight(40);


    }
    public ImageView seekButtonImageSet(String filename) {
        Image image = new Image(getClass().getResourceAsStream(filename));
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(30);
        imageview.setFitWidth(30);
        return imageview;
    }
    /// play button ///
    /// executes actions when clicking play button and sets the icons
    public void playButtonSet(){
        seekButtonStyling(playButton,"(#598c34 0%, #629939 20%, #8cbf66 100%)");
        playButton.setGraphic(seekButtonImageSet("play.png"));
            playButton.setOnAction(e -> {
                grid.getClap().startTask();
                grid.getKick().startTask();
                grid.getHat().startTask();
                grid.getSnare().startTask();
                grid.getCowbell().startTask();
                grid.setPlayState();
                time.start();
                playButton.setDisable(true);

                grid.getTicker().startTaskTick();
            });
    }
    /// Stop button ///
    public void stopButtonSet(){
        seekButtonStyling(stopButton,"(#b01030 0%, #c61236 20%, #dc143c 100%)");

        stopButton.setGraphic(seekButtonImageSet("stop.png"));

        stopButton.setOnAction(e ->{
            grid.getClap().cancelTask();
            grid.getKick().cancelTask();
            grid.getHat().cancelTask();
            grid.getSnare().cancelTask();
            grid.getCowbell().cancelTask();
            grid.stopTrigger();
            playButton.setDisable(false);
            //time.reset();
            grid.getTicker().cancelTickTask();
            // need the time to stop
        });
    }
    /// reset button ////
    // not fully functioning as it should
    public void resetButtonSet(){
        seekButtonStyling(resetButton,"(#3454b4 0%, #3a5eca 20%, #4169e1 100%)");

        resetButton.setGraphic(seekButtonImageSet("reset.png"));

        resetButton.setOnAction(e -> {
            // kinda works but there seems to be a little bit of a repeat
            // befor it rrestes
            grid.getClap().reset();
            grid.getKick().reset();
            grid.getHat().reset();
            grid.getSnare().reset();
            grid.getCowbell().reset();
            time.reset();
            grid.getTicker().cancelTickTask();
            grid.getTicker().startTaskTick();
        });
    }
    // adds buttons to a Hbox for the seeker row
    public HBox getSeekerButtonRow(){
        HBox buttonRow = new HBox();
        buttonRow.setSpacing(10);
        buttonRow.setStyle(backgroundColour);
        // setters
        playButtonSet();
        stopButtonSet();
        resetButtonSet();
        // add to hbox
        buttonRow.getChildren().add(new Label("                           "));

        buttonRow.getChildren().add(playButton);
        buttonRow.getChildren().add(stopButton);
        // temporarily removed until functionality is corrected
        //buttonRow.getChildren().add(resetButton);

        return  buttonRow;
    }

    /// Time keeping rows////
    public HBox timeKeepingRows(){
        HBox timeRow = new HBox();
        timeRow.setSpacing(5);

        // styling config
        timeRow.setStyle(backgroundColour);
        // adding elements of time
        // spacer to align
        timeRow.getChildren().add(new Label("                                                         "));
        // clock
        timeRow.getChildren().add(time.timeToLabel());
        // 16 bar step
        timeRow.getChildren().add((getStepIndexLabel()));
        // bpm label setup
        timeRow.getChildren().add(grid.getBPMLabel());
        //  BPM incrementation buttons
        VBox bpmButtons = new VBox();
        bpmButtons.getChildren().add(grid.buttonUp());
        bpmButtons.getChildren().add(grid.buttonDown());
        timeRow.getChildren().add(bpmButtons);

        return timeRow;

    }
    /// Produces a label which indicates the step of the instrument looping process
    // this should not be here (put this in time?)
    public Label getStepIndexLabel(){
        Label label = new Label();
        labelFormat(label);
        // updates the steps to string
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                String numerator = Integer.toString(grid.getClap().getStepIndex()+1);
                label.setText(numerator + "/16");
            }
        }.start();
        return label;
    }
    /// volume control ////
    public VBox volumeControl(){
        volumeControlRow.setStyle("-fx-text-fill: #393e46; -fx-border-color:#00adb5; -fx-background-color: #EEEEEE;");
        volumeControlRow.getChildren().add(grid.getClap().getSound().getVolumeContainer());
        return volumeControlRow;
    }


    // sets elements of top control panel area
    public BorderPane topRegionGUI(){
        // created another borderpane so i am able to align things
        BorderPane alignment = new BorderPane();

        // left alignment (Seekers)
        seekerRow.setStyle(backgroundColour);
        seekerRow.getChildren().add(getSeekerButtonRow());
        alignment.setLeft(seekerRow);

        // center alignment (Time)
        timeRow.getChildren().add(timeKeepingRows());
        alignment.setCenter(timeRow);

        // right alignment (Volume)
        //alignment.setRight(volumeControl());

        //Space
        alignment.setBottom(new Label("                  "));

        return alignment;
    }



    /////// FINALISING ///////
    // sets the entire background layout and the positioning of each element
    public void layouts(){
        // appearances
        layout.setStyle(backgroundColour);
        // element positioning
        layout.setCenter(beatGridGUI());
        layout.setTop(topRegionGUI());
        layout.setPadding(new Insets(20,20,20,20));
    }
    // Class executor for main method
    public void showWindow(Stage window){
        layouts();
        window.setScene(scene);

        window.setTitle("TR-404");
        window.show();

    }

}
