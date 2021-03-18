package sample;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.TimeUnit;

public class Time {
    private Long startTime;
    private long time;
    // for starting the clock
    private boolean timeStartState;
    // if is above 1 then it doesnt start it again
    private int timeStartStateCount;
    // for pausing the clock
    private boolean timePauseState;


    private long consoleTimeDenominator;
    public Integer fractionTime;
    private long fractionstartTime;


    public Time() {
        this.startTime = System.currentTimeMillis();
        this.time = 0;
        this.timeStartState = false;
        this.timeStartStateCount = 0;
        this.timePauseState = false;

        /// fractions
        this.consoleTimeDenominator = 0;
        this.fractionTime = 0;
        this.fractionstartTime = 0L;

    }

    // formatting for time symbols
    public void labelFormat(Label label) {
        label.setStyle("-fx-text-fill: #393e46; -fx-border-color:#00adb5; -fx-background-color: #EEEEEE;");
        Font font = Font.loadFont("file:resources/fonts/JX-8P_Font.ttf",45);
        label.setMinWidth(150);
        label.setFont(font);
    }

    // outputs time to a returnable value which can be used in calculations
    public long timeConsole() {
        long timePaused = 0;
        timePaused = time;

        if (timeStartState == true) {
            new AnimationTimer() {
                @Override

                public void handle(long now) {
                    double elapsedMillis = System.currentTimeMillis() - startTime;
                    long elapsedSecs = (long) (elapsedMillis / 1000);
                    time = elapsedSecs;
                }
            }.start();
        }
        return time;
    }

    public Label timeToLabel() {
        Label label = new Label();
        labelFormat(label);
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                int p1 = (int) timeConsole() % 60;
                int p2 = (int) timeConsole() / 60;
                int p3 = p2 % 60;
                //System.out.print( p2 + ":" + p3 + ":" + p1);

                if (p1 < 10) {
                    label.setText(Integer.toString(p3) + ":0" + Integer.toString(p1));
                } else if (p1 >= 10) {
                    label.setText(Integer.toString(p3) + ":" + Integer.toString(p1));

                }
            }
        }.start();
        return label;
    }


    // outputs the time to a label for use on console
    public Integer timeToFraction() {
        if (timeStartState == true) {

            new AnimationTimer() {
                @Override
                public void handle(long now) {

                    // time elapsed since program execution
                    double elapsedMillis = (System.currentTimeMillis() - fractionstartTime);
                    // converts to long
                    // multiplies the value to privide a fraction
                    long numerator = (long) ((elapsedMillis / 1000) * 8.33);

                    // value below denominates the time it takes to travel 4 beats at 125 beats per minute
                    double denominator = 1.92;
                    // converts below to show as a fraction
                    long denominatorToBeat = (long) Math.round(denominator * 8.3);

                    // if the elapsed time raises over 16
                    // resets numerator
                    if (numerator > denominatorToBeat) {
                        fractionstartTime = System.currentTimeMillis();
                        elapsedMillis = (System.currentTimeMillis() - startTime);

                    }
                    // converts from long to to allow for hashmap matching against button position
                    fractionTime = (int) numerator;
                }
            }.start();
        }
        return fractionTime;
    }
    ///// Buttons Event handling ///////

    // starts the clock
    public void start() {
        if (timeStartStateCount < 1) {
            startTime = System.currentTimeMillis();
            fractionstartTime = System.currentTimeMillis();
            // if false the timers are set to 0, true starts the timers
            timeStartState = true;
            // means the program can only be started once. wihtout this, its essentailly a reset button
            timeStartStateCount++;
        }
    }

    /// pauses the timer and should return the value it's on
    /// NOT FINISHED COME BACK TO IT
    public Button pause() {
        Button button = new Button();
        button.setMinWidth(35);
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(Event event) {
                timePauseState = true;

            }
        };
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        return button;
    }

    /// resets the time
    public void reset() {
        startTime = System.currentTimeMillis();
        fractionstartTime = System.currentTimeMillis();
    }
}







