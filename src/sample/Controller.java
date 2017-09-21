package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for the Gui
 */
public class Controller implements Initializable, Serializable{

    //Actual profile
    private Profile defaultP;

    //Brightness Menu
    @FXML
    private ToggleGroup ToggleBrightnessMode;
    @FXML
    private RadioButton bOff;
    @FXML
    private RadioButton bLow;
    @FXML
    private RadioButton bMedium;
    @FXML
    private RadioButton bHigh;

    //Mode menu
    @FXML
    private ToggleGroup ToggleSelectionMode;
    @FXML
    private RadioButton modeNormal;
    @FXML
    private RadioButton modeGaming;
    @FXML
    private RadioButton modeBreathe;
    @FXML
    private RadioButton modeMusic;
    @FXML
    private RadioButton modeWave;


    @FXML
    private ComboBox numberOfColors;

    @FXML
    private int n;

    //Color Menu
    @FXML
    private VBox selectColor;
    @FXML
    private ToggleGroup ToggleSelectionColor = new ToggleGroup();
    @FXML
    private RadioButton Color1;
    @FXML
    private RadioButton Color2;
    @FXML
    private RadioButton Color3;

    //Preview
    @FXML
    private HBox previewZone;
    @FXML
    private Rectangle rColor1;
    @FXML
    private Rectangle rColor2;
    @FXML
    private Rectangle rColor3;
    @FXML
    private ColorPicker colorSelector;

    //Save custom profile
    @FXML
    private TextField customProfileName;

    /**
     * Initialize some values and check if the app has been started before or not.
     * Also, is able to check if you have a compatible keyboard
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //test if you are able to run the app
            String chmodCommand="sudo chmod 777 ./autostart.sh";
            exeCommand(chmodCommand);

            Process p = Runtime.getRuntime().exec("msiklm test");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=reader.readLine();
            if(line.contains("No")) {//Check if you have a compatible keyboard
                throw new IllegalArgumentException("Teclado no compatible");
            }

            File f1 = new File("default.msik");
            if(f1.exists()) {//check if the files already exists and load his default profile
                loadProfile(f1);
            }else {//if the file doesn't exist, makes a default profile with default values
                defaultP=new Profile();
                nColors();
                colorSelector.setValue(Color.BLACK);
                getColor();
            }
        }catch(IOException | InterruptedException | IllegalArgumentException e) {//If the keyboard is not compatible, you will have to close the app
            AlertBox.display("Error", "No compatible keyboard",false, new Hyperlink[0]);
        }
    }

    /**
     * Make the changes of the gui to the keyboard when you press the Apply Button
     */
    public void makeChanges(){
        String command="msiklm ";
        File f1 = new File(defaultP.getName());
        saveProfile(f1);//save the actual Gui to the profile
        //get the color values
        if(defaultP.getnColor()==1)//only one color
            command+= defaultP.getColors().get(0);
        else if(defaultP.getnColor()==2)//two colors
            command+= defaultP.getColors().get(0)+","+defaultP.getColors().get(1);
        else  //three colors
            command+= defaultP.getColors().get(0)+","+defaultP.getColors().get(1)+","+defaultP.getColors().get(2);
        //set the mode and brightness to the command
        command+= " "+ defaultP.StringBrightness()+ " "+ defaultP.StringMode();
        //Run the command
        exeCommand(command);
    }

    /**
     * Get how many colors I have selected in the Gui and make that rectangles and toggles appears for these colors
     */
    @FXML
    public void nColors(){
        //get how many colors, and set this value to the profile
        n = Integer.parseInt(numberOfColors.getValue().toString());
        defaultP.setnColor(n);
        //Clear de layout before insert the new toggles and rectangles
        selectColor.getChildren().clear();
        previewZone.getChildren().clear();
        previewZone.setAlignment(Pos.CENTER);
        //Color1
        Color1 = new RadioButton();
        Color1.setText("Color 1");
        Color1.setToggleGroup(ToggleSelectionColor);
        Color1.setSelected(true);
        selectColor.getChildren().add(Color1);

        rColor1 = new Rectangle();
        rColor1.setWidth(75);
        rColor1.setHeight(75);
        previewZone.getChildren().add(rColor1);

        //Color2
        if(n>=2) {
            Color2 = new RadioButton();
            Color2.setText("Color 2");
            Color2.setToggleGroup(ToggleSelectionColor);
            selectColor.getChildren().add(Color2);

            rColor2 = new Rectangle();
            rColor2.setWidth(75);
            rColor2.setHeight(75);
            previewZone.getChildren().add(rColor2);
        }
        //Color3
        if(n==3) {
            Color3 = new RadioButton();
            Color3.setText("Color 3");
            Color3.setToggleGroup(ToggleSelectionColor);
            selectColor.getChildren().add(Color3);

            rColor3 = new Rectangle();
            rColor3.setWidth(75);
            rColor3.setHeight(75);
            previewZone.getChildren().add(rColor3);
        }
    }



    /**
     * Get the constant of the keyboard mode with a String
     * @param s String, mode in String
     * @return Constant of the mode keyboard
     */
    public int mode(String s) {
        if(s.equals("normal")) { return Profile.NORMAL;}
        else if(s.equals("gaming")) { return Profile.GAMING;}
        else if(s.equals("breathe")) { return Profile.BREATHE;}
        else if(s.equals("music sync")) { return Profile.DEMO;}
        else { return Profile.WAVE;}
    }

    /**
     * Get the constant of the Brightness with a String
     * @param s String, brigness mode in String
     * @return Constant of the Brightness mode
     */
    public int brightness(String s) {
        if(s.equals("off")) { return Profile.OFF;}
        else if(s.equals("low")) { return Profile.LOW;}
        else if(s.equals("medium")) { return Profile.MEDIUM;}
        else { return Profile.HIGH;}
    }

    /**
     * change the color of the selected rectangle
     */
    @FXML
    public void getColor() {
        //Get which rectangle is selected
        RadioButton selectedColor =(RadioButton)ToggleSelectionColor.getSelectedToggle();
        String numberColor = selectedColor.getText();
        //Check which rectangle is the selected one and changes his color
        if(numberColor.equals("Color 1")) {
            rColor1.setFill(colorSelector.getValue());
        }
        if(numberColor.equals("Color 2")) {
            rColor2.setFill(colorSelector.getValue());
        }
        if(numberColor.equals("Color 3")) {
            rColor3.setFill(colorSelector.getValue());
        }
    }

    /**
     * Saves the actual setting to a file
     * @param f1 File, the file where the settings will be stored
     */
    public void saveProfile(File f1){
        try {
            //Set the atributes to the actual profile.

            //get the gui values and set the keyboard mode
            RadioButton selectedModeButton = (RadioButton) ToggleSelectionMode.getSelectedToggle();
            String textMode = selectedModeButton.getText().toLowerCase();
            int modeSelected =mode(textMode);
            defaultP.setMode(modeSelected);
            //get the gui values and set the brightness mode to the profile
            RadioButton selectedBrightnessButton = (RadioButton) ToggleBrightnessMode.getSelectedToggle();
            String textBrightness = selectedBrightnessButton.getText().toLowerCase();
            int BrightnessSelected= brightness(textBrightness);
            defaultP.setBrightness(BrightnessSelected);

            //get the gui colors and save to the profile
            defaultP.setColors(new ArrayList<>());//
            String rColor1RGB = rColor1.getFill().toString().substring(0,8);
            //1st Color
            defaultP.getColors().add(rColor1RGB);
            //2nd Color,
            if(n>=2) {
                String rColor2RGB = rColor2.getFill().toString().substring(0,8);;
                defaultP.getColors().add(rColor2RGB);
            }
            //3rd Color
            if(n==3) {
                String rColor3RGB = rColor3.getFill().toString().substring(0,8);
                defaultP.getColors().add(rColor3RGB);
            }
            defaultP.setName("default.msik");

            //Save the profile to a File
            FileOutputStream f2 = new FileOutputStream(f1);
            ObjectOutputStream f3 = new ObjectOutputStream(f2);
            f3.writeObject(defaultP);
            f3.close();
        }catch(IOException e) {}
    }

    /**
     * Loads a new profile to the gui.
     * @param f1 File, the file where the new profile is.
     */
    public void loadProfile(File f1){
        try {
            //Load data from the File to the profile
            FileInputStream f2 = new FileInputStream(f1);
            ObjectInputStream f3 = new ObjectInputStream(f2);
            defaultP = (Profile) f3.readObject();
            f3.close();
            //Load values to the GuI
            numberOfColors.setValue(defaultP.getnColor());

            nColors();//number of color
            selectedMode();//mode
            selectedBrightness();//brightness mode
            //Diferent colors
            String colorPuesto1 = defaultP.getColors().get(0);
            rColor1.setFill(Paint.valueOf(colorPuesto1+"ff"));
            if(n>=2) {
                String colorPuesto2 = defaultP.getColors().get(1);
                rColor2.setFill(Paint.valueOf(colorPuesto2+"ff"));
            }
            if(n==3) {
                String colorPuesto3 = defaultP.getColors().get(2);
                rColor3.setFill(Paint.valueOf(colorPuesto3+"ff"));
            }

        }catch (IOException |ClassCastException |ClassNotFoundException e) {}
    }

    /**
     * Select the Mode that you have for the keyboard
     */
    public void selectedMode() {
        //Unselect the ToogleGroup
        ToggleSelectionMode.getSelectedToggle().setSelected(false);
        int modeInt = defaultP.getMode();
        //Select the right one
        if(modeInt==Profile.WAVE) {
            modeWave.setSelected(true);
            ToggleSelectionMode.selectToggle(modeWave);
        }else if(modeInt==Profile.GAMING) {
            modeGaming.setSelected(true);
            ToggleSelectionMode.selectToggle(modeGaming);
        }else if(modeInt==Profile.BREATHE) {
            modeBreathe.setSelected(true);
            ToggleSelectionMode.selectToggle(modeBreathe);
        }else if(modeInt==Profile.DEMO) {
            modeBreathe.setSelected(true);
            ToggleSelectionMode.selectToggle(modeMusic);
        }else {
            modeNormal.setSelected(true);
            ToggleSelectionMode.selectToggle(modeNormal);
        }
    }

    /**
     *Select the Brightness mode that you have for the keyboard.
     */
    public void selectedBrightness() {
        //Unselect the birghtness mode
        ToggleBrightnessMode.getSelectedToggle().setSelected(false);
        //Select the right brighness mode
        int modeInt = defaultP.getBrightness();
        if(modeInt==Profile.OFF) {
            bOff.setSelected(true);
            ToggleBrightnessMode.selectToggle(bOff);
        }else if(modeInt==Profile.LOW) {
            bLow.setSelected(true);
            ToggleBrightnessMode.selectToggle(bLow);
        }else if(modeInt==Profile.MEDIUM) {
            bMedium.setSelected(true);
            ToggleBrightnessMode.selectToggle(bMedium);
        }else {
            bHigh.setSelected(true);
            ToggleBrightnessMode.selectToggle(bHigh);
        }
    }

    /**
     * Save a Custom profile
     */
    public void saveCustomProfile() {
        String newProfileName = customProfileName.getText();
        customProfileName.clear();
        if (!newProfileName.equals("")){
            File f1 = new File(newProfileName + ".msik");
            saveProfile(f1);
        }
    }

    /**
     * Load a Custom profile with the FileChooser javafx class.
     */
    public void loadCustomProfile(){
        //Create the FileChooser
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("msik Files", "*.msik"));
        File selectedFile = fc.showOpenDialog(null);
        //choose the file
        if (selectedFile != null) {
            loadProfile(selectedFile);
        }
    }

    /**
     * Help and About alert windows with some information
     */
    public void aboutAndHelpWindow(){
        Hyperlink[] links = new Hyperlink[1];
        links[0] = new Hyperlink("https://github.com/rodhuega");
        String message = "App that allows you to change the color of your MSI laptop Keyboard.\n" +
                "This Gui is based on the MSIKLM tool made by Gibtnix user at Github.\n" +
                "App made by rodhuega user at Github.\n" +
                "-Music Sync mode does not respect the colors.";
        AlertBox.display("About", message,false,links);
    }

    ////////////////////////////////////
    //NEN
    /**
     * Enable the keyboard settings when you start your computer.
     */
    public void enableAutostart() {
        String enableCommand="./autostart.sh ";
        enableCommand += defaultP.getCommand();
        exeCommand(enableCommand);
    }

    /**
     * Diable the keyboard settings when you start your computer
     */
    @FXML
    public void disableAutostart(){
        String disableCommand="./autostart.sh --disable";
        exeCommand(disableCommand);
    }

    /**
     * Run a command
     * @param exeThisCommand
     */
    @FXML
    public void exeCommand(String exeThisCommand) {
        try {
            //Run the command
            Process p = Runtime.getRuntime().exec(exeThisCommand);
        }catch(IOException e) {}
    }




}
