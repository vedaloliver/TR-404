package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.scene.text.Font;

public class GUI {
    // Colours
    private String backgroundColour;
    // Layout init
    private BorderPane layout;

    //beatgrid init
    private beatGrid grid;
    private VBox gridVbox;

    // Top region Gui
    private VBox seekerRow;
    private VBox timeRow;
    private VBox volumeControlRow;
    private Button playButton;
    private Button stopButton;
    private Button resetButton;
    private BPM bpm;
    private Button bpmUP;
    private Button bpmDown;
    private Time time;

    // Scene
    private Scene scene;


    public GUI(){
        // Colours
        this.backgroundColour = "-fx-background-color: #222831";
        // inits
        this.layout = new BorderPane();
        // beatgrid
        this.grid = new beatGrid();
        this.gridVbox = new VBox();
        // top panel
        this.seekerRow = new VBox();
        this.timeRow = new VBox();
        this.volumeControlRow = new VBox();
        this.playButton = new Button();
        this.bpm = new BPM();

        this.stopButton = new Button();
        this.resetButton = new Button();

        this.time = new Time();
        //scene
        this.scene = new Scene(layout);

    }
    ///////// ELEMENT FORMATTING ////////////////////
    public void labelFormat(Label label) {
        label.setStyle("-fx-text-fill: #393e46; -fx-border-color:#00adb5; -fx-background-color: #EEEEEE;");
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf",45);
        label.setMinWidth(160);
        label.setFont(font);


    }
    // Changes the button and the sizing for the seek elements
    public ImageView seekButtonImageSet(String filename){
        Image image = new Image(getClass().getResourceAsStream(filename));
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(30);
        imageview.setFitWidth(30);
        return imageview;


    }
    ///////// MAIN BEATGRID UI /////////////////////

    // Returns the main sequencing element
    public VBox beatGridGUI(){
        gridVbox.setStyle(backgroundColour);
        gridVbox.getChildren().add(grid.execute());
        return gridVbox;
    }


    ///////// TOP REGION GUI ( CONTROL PANEL) /////////////////

    /// play button ///
    /// check if it is off
    public void playButtonBooleanCheck(){
        if (grid.playState == false){
            grid.setPlayState();
        }
    }
    /// executes actions when clicking play button and sets the icons

    public void playButtonSet(){
        playButton.setGraphic(seekButtonImageSet("play.png"));
        playButton.setOnAction(e->{
            grid.getClap().startTask();
            grid.getKick().startTask();
            grid.getHat().startTask();
            grid.getSnare().startTask();
            grid.getCowbell().startTask();
            playButtonBooleanCheck();
            time.start();

            grid.startTaskTick();

        });
    }
    /// Stop button ///
    /// come back to it
    // this stops the beats but it doesnt stop the time ticker
    public void stopButtonSet(){
        stopButton.setGraphic(seekButtonImageSet("stop.png"));

        stopButton.setOnAction(e ->{
            grid.stopTrigger();
        });
    }
    /// reset button ////
    public void resetButtonSet(){
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
        buttonRow.getChildren().add(playButton);
        buttonRow.getChildren().add(stopButton);
        buttonRow.getChildren().add(resetButton);

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
        timeRow.getChildren().add(new Label("      "));
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
        alignment.setRight(volumeControl());

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
