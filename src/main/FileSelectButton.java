package main;

import java.io.File;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.security.KeyStore;

import security.Symmetric;

// util
import util.FileUtil;
import util.KeyStoreUtil;

public class FileSelectButton extends Button {
    public FileSelectButton(String dir, Integer buttonType) {
        Symmetric symetric = new Symmetric();

        // key store management
        // create key store or returns existing keystoreQ

        System.out.println(buttonType);
        switch (buttonType) {
            case 0:
                setText("Encrypt file");
                break;
            case 1:
                setText("Decrypt file");
                break;
            default:
                setText("Select file");
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(dir));
        setOnAction(e -> {
            File file = fileChooser.showOpenDialog(new Stage());
            String fileName = file.getAbsolutePath();
            System.out.println(fileName);
            // return fileName;
            // switch (buttonType) {
            // case 0:
            // // symetric.encrypt(fileName);
            // break;
            // case 1:
            // // symetric.decrypt(fileName);
            // break;
            // default:
            // setText("Select file");
            // }

        });
    }

}