package main;

import javax.crypto.spec.SecretKeySpec;

import java.security.KeyStore;

import java.io.File;

import org.bouncycastle.util.encoders.Hex;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

import java.security.SecureRandom;

// javafx
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// util
import util.FileUtil;
import util.KeyStoreUtil;
// security
import security.Symmetric;

public class App extends Application {
    // pass for key value in store ( needs to be provided by user )
    static String secretKeyPW = "";
    // pass for store
    char[] storePW = "pizza".toCharArray();

    public static void main(String[] args) throws Exception {
        // Symetric symetric = new Symetric();
        // SelectFile gui = new SelectFile();

        // System.out.println("Encrypt... ");
        // symetric.encrypt(keyBytes);

        // System.out.println("Decrypt... ");
        // symetric.decrypt(keyBytes);
        // KeyStore store = KeyStoreUtil.createKeyStore(symetric.storePW);
        // KeyStoreUtil.generateAndAddKey(store, symetric.SecretKeyPW);
        // SecretKeySpec keyBytes = KeyStoreUtil.retrieveFromKeyStore(store);

        launch(App.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Select file for encryption");

        // create password field
        Label secretKeyPWFieldLabel = new Label("Secret Key Password");
        PasswordField secretKeyPWField = new PasswordField();

        // create a tile pane
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.TOP_CENTER);

        // add textfield
        tilePane.getChildren().add(secretKeyPWFieldLabel);
        tilePane.getChildren().add(secretKeyPWField);

        // create decrypt/encrypt buttons
        FileSelectButton buttonEncrypt = new FileSelectButton("./", 0);
        buttonEncrypt.setPrefSize(120, 30);
        buttonEncrypt.setDisable(true);
        buttonEncrypt.setAlignment(Pos.CENTER);

        buttonEncrypt.setOnAction(action -> {
            System.out.println(secretKeyPWField.getText());
        });

        FileSelectButton buttonDecrypt = new FileSelectButton("./", 1);
        buttonDecrypt.setPrefSize(120, 30);
        buttonDecrypt.setDisable(true);
        buttonEncrypt.setAlignment(Pos.CENTER);

        // on key press
        // enable encrypt/decrypt buttons
        secretKeyPWField.setOnKeyPressed(e -> {
            if (e.getText().length() > 0) {
                secretKeyPW = secretKeyPW + e.getText();
            } else {
                secretKeyPW = secretKeyPW.substring(0, secretKeyPW.length() - 1);
            }

            if (secretKeyPW.length() > 0) {
                buttonEncrypt.setDisable(false);
                buttonDecrypt.setDisable(false);
            } else if (secretKeyPW.length() == 0) {
                buttonEncrypt.setDisable(true);
                buttonDecrypt.setDisable(true);
            }
        });

        // add encrypt/decrypt buttons on hBox
        HBox hBox = new HBox();
        hBox.getChildren().add(buttonEncrypt);
        hBox.getChildren().add(buttonDecrypt);
        hBox.setAlignment(Pos.TOP_CENTER);

        // put everything together on scene
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(tilePane);
        vBox.getChildren().add(hBox);

        Scene scene = new Scene(vBox, 250, 250);
        stage.setScene(scene);
        stage.show();
    }
}

// when enter is pressed
// action event
// EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
// public void handle(ActionEvent e) {
// secretKeyPW = secretKeyPWField.getText();
// System.out.println(secretKeyPW);
// }
// };
