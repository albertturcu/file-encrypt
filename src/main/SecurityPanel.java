package main;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import security.Symmetric;

public class SecurityPanel extends VBox {
    protected Stage encryptStage;
    protected TextField pathTextField;
    protected Button selectButton;
    protected Byte securityMode;
    protected Symmetric securityControl;

    public SecurityPanel(Stage encryptStage, Byte securityMode, Symmetric securityControl) {
        super();
        this.encryptStage = encryptStage;
        this.securityMode = securityMode;
        this.securityControl = securityControl;
        createPathTextField();
        createSelectButton();
        createWindow();
    }

    private void createPathTextField() {
        pathTextField = new TextField();
        pathTextField.setPrefWidth(250);
        pathTextField.setPromptText("File path...");
    }

    private void createSelectButton() {
        selectButton = new Button("Select...");
        selectButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select file");
            fileChooser.setInitialDirectory(new File("./"));

            try {
                String newPath = fileChooser.showOpenDialog(encryptStage).getAbsolutePath();
                pathTextField.setText(newPath);
                pathTextField.positionCaret(pathTextField.getLength());
            } catch (NullPointerException e) {
                System.out.println("Canceled");
            }
        });
    }

    private void createWindow() {
        HBox path = new HBox();
        path.getChildren().addAll(pathTextField, selectButton);
        path.setSpacing(30);

        Button symmetric = new Button(this.securityMode == Symmetric.ENCRYPT_MODE ? "Encrypt" : "Decrypt");
        symmetric.setPrefSize(120, 30);
        // symmetric.setDisable(true);
        symmetric.setOnAction(
                event -> new PasswordWindow(pathTextField.getText(), this.securityMode, this.securityControl));

        getChildren().addAll(path, symmetric);
        setSpacing(30);
        setPadding(new Insets(30));
    }
}
