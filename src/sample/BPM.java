package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class BPM {
    protected int BPM;

    public BPM() {
        this.BPM = 125;
    }

    public int bpmPlus() {
        this.BPM++;
        return this.BPM;
    }

    public int bpmMinus() {
        this.BPM--;
        return this.BPM;
    }

    public int getBPM() {
        return this.BPM;
    }
}


