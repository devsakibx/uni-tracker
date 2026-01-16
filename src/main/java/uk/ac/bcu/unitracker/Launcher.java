package uk.ac.bcu.unitracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.ac.bcu.unitracker.admin.AdminController;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;
import uk.ac.bcu.unitracker.service.TrackerService;
import uk.ac.bcu.unitracker.student.StudentController;

import java.nio.file.Path;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AssignmentCsvRepository repo = new AssignmentCsvRepository(Path.of("data", "assignments.csv"));
        TrackerService service = new TrackerService(repo);

        Button studentBtn = new Button("Open Student Client");
        Button adminBtn = new Button("Open Admin/Tutor Client");
        Button aboutBtn = new Button("About");

        studentBtn.setOnAction(e -> {
            Stage s = new Stage();
            StudentController controller = new StudentController(service);
            s.setTitle("UniTracker - Student");
            s.setScene(new Scene(controller.getView(), 900, 520));
            s.show();
        });

        adminBtn.setOnAction(e -> {
            Stage s = new Stage();
            AdminController controller = new AdminController(service);
            s.setTitle("UniTracker - Admin/Tutor");
            s.setScene(new Scene(controller.getView(), 900, 520));
            s.show();
        });

        aboutBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About UniTracker");
            alert.setHeaderText("UniTracker");
            alert.setContentText(
                    "Version: 1.0.0\n" +
                            "Storage: data/assignments.csv\n" +
                            "Clients: Student + Admin/Tutor"
            );
            alert.showAndWait(); // shows informational dialog [web:217]
        });

        VBox root = new VBox(10,
                new Label("Choose a client:"),
                studentBtn,
                adminBtn,
                aboutBtn
        );
        root.setStyle("-fx-padding: 20;");

        stage.setTitle("UniTracker - Start");
        stage.setScene(new Scene(root, 320, 190));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
