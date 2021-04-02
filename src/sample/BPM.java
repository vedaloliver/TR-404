package sample;


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


