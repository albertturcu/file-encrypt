package main;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import security.Symmetric;

public class MainWindow extends BorderPane {

    public MainWindow(Stage primaryStage) {
        // init symmetric control
        Symmetric securityControl = new Symmetric();
        TabPane tabPane = new TabPane();
        Tab encryptTab = new Tab("Encrypt", new SecurityPanel(primaryStage, Symmetric.ENCRYPT_MODE, securityControl));
        encryptTab.setClosable(false);
        Tab decryptTab = new Tab("Decrypt", new SecurityPanel(primaryStage, Symmetric.DECRYPT_MODE, securityControl));
        decryptTab.setClosable(false);

        tabPane.getTabs().addAll(encryptTab, decryptTab);
        setCenter(tabPane);
    }
}