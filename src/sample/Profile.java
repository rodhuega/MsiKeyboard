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

    /**
     * Constructor of Profile
     * @param name String
     * @param mode int
     * @param brightness int
     * @param nColor int,[1,3]
     * @param colors ArrayList<String></String>
     */
    public Profile(String name, int mode, int brightness, int nColor, ArrayList<String> colors) {
        this.name = name;
        this.mode = mode;
        this.brightness = brightness;
        this.nColor = nColor;
        this.colors = colors;
    }

    /**
     * Default Constructor of profile
     * name: default.msik, mode NORMAL, brightness HIGH, nColor 1, and new ArrayList
     */
    public Profile() {
        this("default.msik", NORMAL, HIGH, 1, new ArrayList<String>());
    }

    /**
     * get the name of the profile
     * @return name String
     */
    public String getName() {
        return name;
    }

    /**
     * set the profile name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the mode of the profile
     * @return mode int
     */
    public int getMode() {
        return mode;
    }

    /**
     * set the keyboard mode
     * @param mode int
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * get the brightness of the profile
     * @return brightness int
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * set the brightness mode
     * @param brightness int
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     * get the number of colors of the profile
     * @return nColor int
     */
    public int getnColor() {
        return nColor;
    }

    /**
     * set how many colors the profile has
     * @param nColor int
     */
    public void setnColor(int nColor) {
        this.nColor = nColor;
    }

    /**
     * get the ArrayList of Colors with Strings that have their hex value of the colors
     * @return ArrayList<String>
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * set the ArrayList of Colors with Strings that have their hex value of the colors
     * @param colors ArrayList<String>
     */
    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Override toString that return name, mode, brighness and nColor
     * @return s String
     */
    @Override
    public String toString() {
        String s = "";
        s+="Name: "+ name + " mode:"+ mode + " brightness: "+ brightness+" nColor: "+ nColor;
        return s;
    }

    /**
     * Translate mode to a String
     * @return String, mode in a String
     */
    public String StringMode() {
        if(mode==0) {
            return "normal";
        }else if(mode==1) {
            return "gaming";
        }else if(mode==2) {
            return "breathe";
        }else if(mode==3) {
            return "demo";
        }else {
            return "wave";
        }
    }

    /**
     * Translate brightness to a String
     * @return String, brightness in a String
     */
    public String StringBrightness(){
        if(brightness==5) {
            return "off";
        }else if(brightness==6) {
            return "low";
        }else if(brightness==7) {
            return "medium";
        }else {
            return "high";
        }
    }
}
