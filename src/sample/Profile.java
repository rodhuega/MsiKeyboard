package sample;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rodhuega on 6/19/17.
 */
public class Profile implements Serializable {
    //Mode Constants
    public static final int NORMAL = 0;
    public static final int GAMING = 1;
    public static final int BREATHE = 2;
    public static final int DEMO = 3;
    public static final int WAVE = 4;
    //Brightness  constants
    public static final int OFF = 5;
    public static final int LOW = 6;
    public static final int MEDIUM = 7;
    public static final int HIGH = 8;

    //Atributes
    private String name;
    private int mode;
    private int brightness;
    private int nColor;
    private ArrayList<String> colors;

    public Profile(String name, int mode, int brightness, int nColor, ArrayList<String> colors) {
        this.name = name;
        this.mode = mode;
        this.brightness = brightness;
        this.nColor = nColor;
        this.colors = colors;
    }

    public Profile() {
        this("default.msik", NORMAL, HIGH, 1, new ArrayList<String>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getnColor() {
        return nColor;
    }

    public void setnColor(int nColor) {
        this.nColor = nColor;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        String s = "";
        s+="Name: "+ name + " mode:"+ mode + " brightness: "+ brightness+" nColor: "+ nColor;
        return s;
    }
}
