package sample;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;

/**
 * Created by rodhuega on 6/19/17.
 */
public class AlertBox {

    public static void display(String title, String message) {
        Stage windowAlert = new Stage();
        windowAlert.setTitle(title);
        windowAlert.initModality(Modality.APPLICATION_MODAL);
        Label messageLabel = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e-> {
            windowAlert.close();
            //Platform.exit();
        });
        VBox layout1 = new VBox(10);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(messageLabel,closeButton);
        Scene scene1 = new Scene(layout1);
        windowAlert.setMinWidth(100);
        windowAlert.setScene(scene1);
        windowAlert.showAndWait();


    }

}
