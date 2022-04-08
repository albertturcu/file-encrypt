package main;

// javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(App.class, args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Select file for encryption");
        Scene scene = new Scene(new MainWindow(stage));
        stage.setScene(scene);
        stage.show();
    }
}
