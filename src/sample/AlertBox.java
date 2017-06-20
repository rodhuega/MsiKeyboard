package sample;


import javafx.application.Platform;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * AlertBoxes for the App
 */
public class AlertBox {

    /**
     * Makes a javaFx AlertBox
     * @param title String, title for the AlertBox
     * @param message String, message for the alertBox
     * @param forceMode boolean, true->app will be closed, false->app won't be closed
     */
    public static void display(String title, String message,boolean forceMode, Hyperlink[] link) {
        Stage windowAlert = new Stage();
        windowAlert.setTitle(title);
        windowAlert.getIcons().add(new Image("/res/logo.png"));
        windowAlert.initModality(Modality.APPLICATION_MODAL);
        Label messageLabel = new Label(message);
        Button closeButton = new Button("Close");
        windowAlert.setOnCloseRequest(e->{
            e.consume();
            windowAlert.close();
            if(forceMode) {
               Platform.exit();
            }
        });
        closeButton.setOnAction(e-> {
            windowAlert.close();
            if(forceMode) {
               Platform.exit();
            }
        });
        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(messageLabel,closeButton);
        layout1.setPadding(new Insets(10,10,10,10));
        if(link.length!=0) {
            layout1.getChildren().add(link[0]);
        }
        Scene scene1 = new Scene(layout1);
        windowAlert.setMinWidth(100);
        windowAlert.setMinHeight(100);
        windowAlert.setScene(scene1);
        windowAlert.showAndWait();
    }

}
