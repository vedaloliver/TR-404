package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.io.File;

// wraps up the functionality to play sound
// currently only processing .wav files

public class soundGeneration extends beatRow {
    // filename
    private String fileName;
    // media conversion
    private Media mediaObj;


    //volume


    private Slider volume;
    public HBox volumeContainer;

    public soundGeneration(String filename) {
        super();
        this.fileName = filename;
        this.mediaObj = new Media(new File(fileName).toURI().toString());

        this.volume = new Slider();
        this.volumeContainer = new HBox();
    }

    public soundGeneration() {

    }

    // returns filename
    public String getName() {
        return this.fileName;
    }

    // plays sound
    public MediaPlayer play() {
        MediaPlayer hit = new MediaPlayer(this.mediaObj);
        hit.play();
        return hit;
    }

    // Eventhandler for mouseclick events
    public EventHandler soundMaker(String filename) {
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(Event event) {
                soundGeneration soundGeneration = new soundGeneration(filename);
                soundGeneration.play();

            }
        };
        return handler;
    }

    public void volumeSliderControl() {
        volume.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volume.isValueChanging()) {
                    play().setVolume(volume.getValue() / 100.0);
                }
            }
        });
    }
    public HBox getVolumeContainer(){
        volumeSliderControl();
        volumeContainer.getChildren().add(volume);
        return volumeContainer;
        }
    }




