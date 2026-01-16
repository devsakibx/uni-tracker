package uk.ac.bcu.unitracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        VBox root = new VBox(10, studentBtn, adminBtn);
        root.setStyle("-fx-padding: 20;");

        stage.setTitle("UniTracker - Start");
        stage.setScene(new Scene(root, 320, 140));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
