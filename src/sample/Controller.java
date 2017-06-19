package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, Serializable{

    //Actual profile
    private Profile defaultP;

    @FXML
    private ToggleGroup ToggleBrightnessMode;
    @FXML
    private ToggleGroup ToggleSelectionMode;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Process p = Runtime.getRuntime().exec("msiklm test");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=reader.readLine();
            //if(line.contains("No")) {
            //     throw new IllegalArgumentException("Teclado no compatible");
            // }

                File f1 = new File("default.msik");
                if(f1.exists()) {
                    loadProfile(f1);
                    System.out.print(defaultP.toString());
                }else {
                    defaultP=new Profile();
                }
                nColors();
                colorSelector.setValue(Color.BLACK);
                getColor();



        }catch(IOException | InterruptedException | IllegalArgumentException e) {
            AlertBox.display("Error", "No compatible keyboard");
        }
    }

    public void makeChanges(){
        try {
            String command="msiklm ";
            RadioButton selectedModeButton = (RadioButton) ToggleSelectionMode.getSelectedToggle();
            String textMode = selectedModeButton.getText().toLowerCase();
            int modeSelected =mode(textMode);
            defaultP.setMode(modeSelected);
            RadioButton selectedBrightnessButton = (RadioButton) ToggleBrightnessMode.getSelectedToggle();
            String textBrightness = selectedBrightnessButton.getText().toLowerCase();
            int BrightnessSelected= brightness(textBrightness);
            defaultP.setBrightness(BrightnessSelected);
            String rColor1RGB = rColor1.getFill().toString().substring(0,8);

            if(defaultP.getColors().size()==0) {
                defaultP.getColors().add(rColor1RGB);
            }else {
                defaultP.getColors().set(0,rColor1RGB);
            }


            command += rColor1RGB;
            if(n!=1) {
                command+=",";
            }else {
                command+=" ";
            }
            if(n>=2) {
                String rColor2RGB = rColor2.getFill().toString().substring(0,8);;
                command += rColor2RGB;
                if(defaultP.getColors().size()<2) {
                    defaultP.getColors().add(rColor2RGB);
                }else {
                    defaultP.getColors().set(1,rColor2RGB);
                }

                if(n==2) {
                    command+=" ";
                }else {
                    command+=",";
                }
            }
            if(n==3) {
                String rColor3RGB = rColor3.getFill().toString().substring(0,8);
                if(defaultP.getColors().size()<3) {
                    defaultP.getColors().add(rColor3RGB);
                }else {
                    defaultP.getColors().set(2,rColor3RGB);
                }
                command += rColor3RGB+" ";
            }
            command+=textBrightness+ " "+ textMode;
            System.out.println(command);
            saveProfile();

            Process p = Runtime.getRuntime().exec(command);
        }catch(IOException e) {}
    }

    @FXML
    public void nColors(){
        n = Integer.parseInt(numberOfColors.getValue().toString());
        defaultP.setnColor(n);
        selectColor.getChildren().clear();
        previewZone.getChildren().clear();
        previewZone.setAlignment(Pos.CENTER);

        Color1 = new RadioButton();
        Color1.setText("Color 1");
        Color1.setToggleGroup(ToggleSelectionColor);
        Color1.setSelected(true);
        selectColor.getChildren().add(Color1);
        rColor1 = new Rectangle();
        rColor1.setWidth(75);
        rColor1.setHeight(75);
        previewZone.getChildren().add(rColor1);


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



    //Obtener modo Seleccionado
    public int mode(String s) {
        if(s.equals("Normal")) { return Profile.NORMAL;}
        else if(s.equals("Gaming")) { return Profile.GAMING;}
        else if(s.equals("Breathe")) { return Profile.BREATHE;}
        else if(s.equals("Demo")) { return Profile.DEMO;}
        else { return Profile.WAVE;}
    }

    //Obtener modo brillo
    public int brightness(String s) {
        if(s.equals("Off")) { return Profile.OFF;}
        else if(s.equals("Low")) { return Profile.LOW;}
        else if(s.equals("Medium")) { return Profile.MEDIUM;}
        else { return Profile.HIGH;}
    }

    @FXML
    public void getColor() {
        RadioButton selectedColor =(RadioButton)ToggleSelectionColor.getSelectedToggle();
        String numberColor = selectedColor.getText();
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

    public void saveProfile(){
        try {
            File f1 = new File(defaultP.getName());
            FileOutputStream f2 = new FileOutputStream(f1);
            ObjectOutputStream f3 = new ObjectOutputStream(f2);
            f3.writeObject(defaultP);
            f3.close();
        }catch(IOException e) {

        }
    }

    public void loadProfile(File f1){
        try {
            FileInputStream f2 = new FileInputStream(f1);
            ObjectInputStream f3 = new ObjectInputStream(f2);
            defaultP = (Profile) f3.readObject();
            f3.close();
        }catch (IOException |ClassCastException |ClassNotFoundException e) {}
    }




}
