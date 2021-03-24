package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.util.*;


// This class functions to generate physical rows for each instrument
// Appearance, eventhandlers and row creation is performed
public class beatRow {
    // list of buttons
    private ArrayList<Node> list;
    //indent count
    private int count;
    // iniivudal button row
    private HBox hButtonLayout;
    public boolean beatState;
    private HashMap<Integer, Button> map;
    private ArrayList<Boolean> beatStateList;
    public String instrumentName;
    private soundGeneration sound;
    private BPM bpm;
    // drop down box for instrument changing
    private ComboBox dropDown;
    private String filename;

    // Thread stopper
    private Thread thread;

    public int stepIndex;

    public beatRow(String name) {
        this.instrumentName = name;
        this.hButtonLayout = new HBox();
        // indent count
        this.count = 0;
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
        this.beatState = false;
        this.beatStateList = new ArrayList<Boolean>(Arrays.asList(new Boolean[16]));
        Collections.fill(beatStateList, Boolean.FALSE);
        this.filename = "C:\\Users\\ojwar\\Desktop\\Code_files\\tickerTestTwo\\sounds"+"\\"+instrumentName+"\\"+instrumentName+" 1";

        this.sound = new soundGeneration(this.filename+".wav");

        this.bpm = new BPM();

        this.dropDown = new ComboBox();

        this.thread = new Thread();


        this.stepIndex = 0;
    }

    public beatRow() {
    }
    public soundGeneration getSound(){
        return this.sound;
    }
    public BPM getBpm(){
        return this.bpm;
    }
    public long getBpmLong(){
        long bpmLong = (long) this.bpm.getBPM();
        return bpmLong;
    }

    // display for the instrument
    public Label label(int width) {
        // label creation and name
        Label label = new Label(instrumentName);
        // label width to create indentation
        label.setMinWidth(width);
        // Font and colour setting
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf",13);
        label.setFont(font);
        label.setTextFill(Color.web("#eeeeee"));

        return label;

    }
    public ComboBox instrumentSelectionBox(String name){
        /// combobox which is for assigning the instrument sound
        ArrayList<String> instruments = new ArrayList<>();
        int index = 5;
        for (int i=1;i<=index;i++){
            instruments.add(name+" "+Integer.toString(i));
        }
        this.dropDown = new ComboBox(FXCollections.observableArrayList(instruments));
        this.dropDown.setVisibleRowCount(3);
        this.dropDown.setStyle("-fx-pref-width: 2;");

        return  this.dropDown;

    }

    // Adds elements of label region to a vbox (name and drop down)
    public void labelRegion(String name, int width){
        VBox vbox = new VBox();

        vbox.getChildren().add(label(width));
        vbox.getChildren().add(instrumentSelectionBox(name));
        hButtonLayout.getChildren().add(vbox);
    }

    // sets the physical design of the entire hbox/row
    public void design(int spacing, String style) {
        hButtonLayout.setSpacing(spacing);
        hButtonLayout.setStyle(style);
    }


    public void setStyleOn(Button button){
        button.setStyle(
                "-fx-background-color:" +
                        "#090a0c, " +
                        "linear-gradient(#FFFFE0 0%, #e6e600 5%, #191d22 100%), " +
                        "radial-gradient(center 50% -5%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"+
                        "-fx-background-radius: 5,4,3,5;"+
                        "-fx-background-insets: 0,1,1,0;"+
                        "-fx-text-fill: white;"+
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"+
                        "-fx-font-family: Arial;"+
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);"+
                        "-fx-font-size: 12px;"+
                        "-fx-padding: 10 20 10 20;");
    }
    public void setStyleOff(Button button){
        button.setStyle(
                "-fx-background-color:" +
                        "#090a0c, " +
                        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), " +
                        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));"+
                        "-fx-background-radius: 5,4,3,5;"+
                        "-fx-background-insets: 0,1,1,0;"+
                        "-fx-text-fill: white;"+
                        "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );"+
                        "-fx-font-family: Arial;"+
                        "-fx-text-fill: linear-gradient(white, #d0d0d0);"+
                        "-fx-font-size: 3px;"+
                        "-fx-padding: 10 20 10 20;");

    }
    // duplicates squares
    public void squareCreationLoop() {
        // a row of 16 pads
        for (int i = 0; i < 16; i++) {
            Button button = new Button();
            setStyleOff(button);
            button.setMinHeight(50);
            button.setMinWidth(35);
            button.setMaxHeight(50);
            button.setMaxWidth(35);
            this.list.add(button);

        }
    }

    public ArrayList<Node> getList() {
        return this.list;
    }
    /////////////// TO MAP //////////////////////////

    // assigns an integer for reference
    public HashMap<Integer, Button> toMap(ArrayList<Node> list) {
        int index = 0;

        for (Node i : list) {
            if (i instanceof Button) {
                map.put(index, (Button)i);
                index++;
            }
        }
        return map;
    }

    public ArrayList<Boolean> getBeatStateList() {
        return this.beatStateList;
    }


    /////////////// EVENT HANDLING //////////////////////////

    // If clicked, it assigns a boolean value to list of booleans corresponding to it's index
    // if these booleans are on, it will play when the playback Class passes over it
    public EventHandler beatButtonOn(Button button) {
        EventHandler handler = new EventHandler() {
            String t = "On";
            String f = " ";

            @Override
            public void handle(Event event) {
                if (button.getText().equals(t)) {
                    button.setText(f);
                    setStyleOff(button);
                    for (Map.Entry<Integer, Button> i : map.entrySet()) {
                        if (i.getValue().equals(button)) {
                            beatStateList.set(i.getKey(), false);
                        }
                    }
                } else {
                    button.setText(t);
                    setStyleOn(button);
                    for (Map.Entry<Integer, Button> i : map.entrySet()) {
                        if (i.getValue().equals(button)) {
                            beatStateList.set(i.getKey(), true);
                        }
                    }
                }
            }
        };
        return handler;
    }

    // adds events to each button
    public void add(HashMap<Integer, Button> Pads, HBox layout, String filename) {
        for (Button i : Pads.values()) {
            // The button makes the corresponding sound when it's clicked
            i.addEventHandler(MouseEvent.MOUSE_CLICKED, sound.soundMaker(filename));
            // the button state turns on and subsequently affects the list of booleans
            i.addEventHandler(MouseEvent.MOUSE_CLICKED, beatButtonOn(i));

            // adds to the row layout
            layout.getChildren().add(i);
        }
    }


    // finalises the row by creating it based on physical attributes
    public HBox rowCreation(String labelName, String filename) {
        // spacing and colour background
        design(10, "-fx-background-color: #222831");
        // Label name and width (fone size/colour should be integrated)
        labelRegion(labelName,  95);
        // iterates and creates the squares
        squareCreationLoop();
        // adds it to the map
        add(toMap(getList()), hButtonLayout, filename);
        return hButtonLayout;
    }

    public HBox gethButtonLayout() {
        return this.hButtonLayout;
    }

    //  wraps up the classes processes for use in other domains
    public HBox initaliser() {

        rowCreation(instrumentName, filename + ".wav");
        changeInstrument();

        return this.hButtonLayout;
    }



    // so this does work somehow but it isnt actually changing the soudns
    public HBox changeInstrument(){
        EventHandler<ActionEvent> event=
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        changeInstrumentLogic();
                    }
                };

        dropDown.setOnAction(event);
        return hButtonLayout;
    }
    public void changeInstrumentLogic(){
        // needs to stop the thread before applying new elements
        thread.stop();

        // used for changing the file name
        String instrumentNameInList = ((String) dropDown.getValue());
//        this.dropDown.set
        // filename
        filename = "C:\\Users\\ojwar\\Desktop\\Code_files\\tickerTestTwo\\sounds"+"\\"+instrumentName+"\\"+instrumentNameInList;

        // Clones a new arraylist which contains the old beatstates
        ArrayList<Boolean> copy = new ArrayList<>(beatStateList);
        // creates a new list of buttons which will contain the new sound
        list = new ArrayList<Node>();
        // clears the last buttons lists
        hButtonLayout.getChildren().clear();
        // creation loop as normal
        rowCreation(instrumentName, filename+".wav");
        beatStateList = copy;

        // used to retain the beatstates when changing instruent
        for (Map.Entry<Integer, Button> i : map.entrySet()) {
            if (beatStateList.get(i.getKey()) == true){
                i.getValue().setText("On");
            }
        }
        soundGeneration newSound = new soundGeneration(filename+".wav");
        sound = newSound;
        startTask();

    }
    //////// Thread Creation and playback functionality/////
    /// gets the step of the row seeking which can provide the fraction of 16 label
    public int getStepIndex(){
        return this.stepIndex;
    }
    public void reset(){
        this.stepIndex = 0;
    }
    // starts the task playing
    public void startTask() {
            Runnable task = () -> runTask();

            thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
    // stops the task playing
    public void cancelTask(){
        thread.stop();
    }
    // task details
    private void runTask() {
            // if that index in the boolean list indicates the button state is on
            while (beatGrid.playState == true) {
                soundGeneration sound = new soundGeneration(filename + ".wav");
                this.stepIndex = 0;
                for (Node i : gethButtonLayout().getChildren()) {
                    if (i instanceof Button & beatStateList.get(this.stepIndex) == true) {
                        try {
                            Platform.runLater(() -> sound.play());
                            // sleeping function is modified by the bpm counter
                            Thread.sleep((long) (1000 * (60.0 / (this.bpm.getBPM() * 4))));
                            // step index is used to move along each beat
                            this.stepIndex++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    // if false, then just move over the step
                        // still is going to have logic as the sleep function is needed to pass over to have timely flow
                    } else if (i instanceof Button & beatStateList.get(this.stepIndex) == false) {
                        try {
                            Thread.sleep((long) (1000 * (60.0 / (this.bpm.getBPM() * 4))));
                            this.stepIndex++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }



