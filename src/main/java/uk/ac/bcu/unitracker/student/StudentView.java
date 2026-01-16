package uk.ac.bcu.unitracker.student;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;

public class StudentView {

    private final BorderPane root = new BorderPane();

    final TableView<Assignment> table = new TableView<>();

    final TextField moduleCodeField = new TextField();
    final TextField titleField = new TextField();
    final DatePicker dueDatePicker = new DatePicker();

    final ComboBox<AssignmentStatus> statusBox = new ComboBox<>();
    final TextField selectedIdField = new TextField();
    final TextArea notesArea = new TextArea();

    final Button addBtn = new Button("Add");
    final Button refreshBtn = new Button("Refresh");
    final Button setStatusBtn = new Button("Set Status");
    final Button saveNotesBtn = new Button("Save Notes");
    final Button deleteBtn = new Button("Delete");

    public StudentView() {
        root.setPadding(new Insets(10));

        TableColumn<Assignment, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Assignment, String> modCol = new TableColumn<>("Module");
        modCol.setCellValueFactory(new PropertyValueFactory<>("moduleCode"));

        TableColumn<Assignment, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Assignment, Object> dueCol = new TableColumn<>("Due");
        dueCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Assignment, Object> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, modCol, titleCol, dueCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane addPane = new GridPane();
        addPane.setHgap(8);
        addPane.setVgap(8);
        addPane.setPadding(new Insets(0, 0, 10, 0));

        addPane.add(new Label("Module Code:"), 0, 0);
        addPane.add(moduleCodeField, 1, 0);

        addPane.add(new Label("Title:"), 2, 0);
        addPane.add(titleField, 3, 0);

        addPane.add(new Label("Due Date:"), 4, 0);
        addPane.add(dueDatePicker, 5, 0);

        HBox addBtns = new HBox(8, addBtn, refreshBtn);
        addPane.add(addBtns, 6, 0);

        selectedIdField.setEditable(false);
        statusBox.getItems().setAll(AssignmentStatus.values());

        GridPane right = new GridPane();
        right.setHgap(8);
        right.setVgap(8);
        right.setPadding(new Insets(0, 0, 0, 10));

        right.add(new Label("Selected ID:"), 0, 0);
        right.add(selectedIdField, 1, 0);

        right.add(new Label("Status:"), 0, 1);
        right.add(statusBox, 1, 1);
        right.add(setStatusBtn, 1, 2);

        right.add(new Label("Notes:"), 0, 3);
        notesArea.setPrefRowCount(8);
        right.add(notesArea, 1, 3);
        right.add(saveNotesBtn, 1, 4);

        right.add(deleteBtn, 1, 5);

        root.setTop(addPane);
        root.setCenter(table);
        root.setRight(right);
    }

    public Parent getRoot() {
        return root;
    }
}
