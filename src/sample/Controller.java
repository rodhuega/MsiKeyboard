package sample;

import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Process p = Runtime.getRuntime().exec("msiklm test");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line=reader.readLine();
            if(line.charAt(0)=='N') {
                throw new IllegalArgumentException("Teclado no compatible");
            }
        }catch(IOException | InterruptedException | IllegalArgumentException e) {
            System.out.println("Teclado no compatible");
        }
    }

    public void makeChanges(){
        try {
            Process p = Runtime.getRuntime().exec("msiklm red");
            //p.waitFor();
        }catch(IOException e) {}
    }
}
