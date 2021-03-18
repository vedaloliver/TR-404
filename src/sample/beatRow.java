package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;


// This class functions to generate physical rows for each instrument
// Appearance, eventhandlers and row creation is performed
public class beatRow {
    // list of buttons
    private ArrayList<Button> list;
    // iniivudal button row
    private HBox hButtonLayout;
    public boolean beatState;
    private HashMap<Integer, Button> map;
    private ArrayList<Boolean> beatStateList;
    public String instrumentName;
    private soundGeneration sound;
    private BPM bpm;

    public int stepIndex;

    public beatRow(String name) {
        this.instrumentName = name;
        this.hButtonLayout = new HBox();
        this.list = new ArrayList<>();
        this.map = new HashMap<>();
        this.beatState = false;
        this.beatStateList = new ArrayList<Boolean>(Arrays.asList(new Boolean[16]));
        Collections.fill(beatStateList, Boolean.FALSE);
        this.sound = new soundGeneration(name+".wav");

        this.bpm = new BPM();


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

    //changes height and width of individual buttons
    public void setSize(Button button, int width, int height) {
        button.setMinWidth(width);
        button.setMinHeight(height);
    }

    //
    // display for the instrument
    public void label(String name, int width) {
        Label label = new Label(name);
        label.setMinWidth(width);
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf",13);
        label.setFont(font);
        label.setTextFill(Color.web("#eeeeee"));
        hButtonLayout.getChildren().add(label);
    }

    // sets the physical design of the entire hbox/row
    public void design(int spacing, String style) {
        hButtonLayout.setSpacing(spacing);
        hButtonLayout.setStyle(style);
    }

    // duplicates squares
    public void squareCreationLoop(int width, int height) {
        // a row of 16 pads
        for (int i = 0; i < 16; i++) {
            Button button = new Button();
            setSize(button, width, height);
            this.list.add(button);

            //indentCreator();
        }
    }

    public ArrayList<Button> getList() {
        return this.list;
    }
    /////////////// TO MAP //////////////////////////

    // assigns an integer for reference
    public HashMap<Integer, Button> toMap(ArrayList<Button> list) {
        int index = 0;

        for (Button i : list) {
            map.put(index, i);
            index++;
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
                    for (Map.Entry<Integer, Button> i : map.entrySet()) {
                        if (i.getValue().equals(button)) {
                            beatStateList.set(i.getKey(), false);
                        }
                    }
                } else {
                    button.setText(t);
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
        label(labelName, 95);
        // iterates and creates the squares
        squareCreationLoop(35, 50);
        // adds it to the map
        add(toMap(getList()), hButtonLayout, filename);
        return hButtonLayout;
    }

    public HBox gethButtonLayout() {
        return this.hButtonLayout;
    }

    //  wraps up the classes processes for use in other domains
    public HBox initaliser() {
        rowCreation(instrumentName, instrumentName + ".wav");
        return this.hButtonLayout;
    }

    //////// Thread Creation and playback functionality/////
    /// gets the step of the row seeking which can provide the fraction of 16 label
    public int getStepIndex(){
        return this.stepIndex;
    }
    public void reset(){
        this.stepIndex = 0;
    }

    public void startTask() {
        Runnable task = () -> runTask();

        Thread backGroundThread = new Thread(task);
        backGroundThread.setDaemon(true);
        backGroundThread.start();
    }
    // separat
    private void runTask() {
        while (beatGrid.playState == true){
        soundGeneration sound = new soundGeneration(instrumentName+".wav");
        this.stepIndex= 0;
        for (Node i : gethButtonLayout().getChildren()) {
            if (i instanceof Button & beatStateList.get(this.stepIndex) == true) {
                try {
                    Platform.runLater(() -> sound.play());

                    Thread.sleep((long) (1000 * (60.0 / (this.bpm.getBPM() * 4))));
                    this.stepIndex++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

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


