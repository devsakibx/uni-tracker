package uk.ac.bcu.unitracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;
import uk.ac.bcu.unitracker.service.TrackerService;
import uk.ac.bcu.unitracker.student.StudentController;

import java.nio.file.Path;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AssignmentCsvRepository repo = new AssignmentCsvRepository(Path.of("data", "assignments.csv"));
        TrackerService service = new TrackerService(repo);

        StudentController controller = new StudentController(service);

        stage.setTitle("UniTracker - Student");
        stage.setScene(new Scene(controller.getView(), 900, 520));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
