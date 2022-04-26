package main;

// javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import security.Symmetric;
import security.SymmetricTest;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        //testing setup
        Symmetric securityControl = new Symmetric();
        SymmetricTest test = new SymmetricTest(securityControl);
        test.encryptEmptyFile();
        test.decryptEmptyFile();
        System.out.println("TESTING COMPLETED");
        System.out.printf("PASSED TESTS: %d", test.countPassed );

        //launching app
        launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Select file for encryption");
        Scene scene = new Scene(new MainWindow(stage));
        stage.setScene(scene);
        stage.show();
    }
}
