package uk.ac.bcu.unitracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("UniTracker");
        stage.setScene(new Scene(new Label("UniTracker starting..."), 320, 140));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
