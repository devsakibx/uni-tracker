package uk.ac.bcu.unitracker.admin;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;

public class AdminView {

    private final BorderPane root = new BorderPane();

    final TableView<Assignment> table = new TableView<>();

    final TextField filterModuleField = new TextField();
    final ComboBox<AssignmentStatus> filterStatusBox = new ComboBox<>();
    final Button applyFilterBtn = new Button("Apply Filter");
    final Button clearFilterBtn = new Button("Clear Filter");
    final Button refreshBtn = new Button("Refresh");

    public AdminView() {
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

        filterStatusBox.getItems().add(null);
        filterStatusBox.getItems().addAll(AssignmentStatus.values());
        filterStatusBox.setPromptText("Any");

        GridPane top = new GridPane();
        top.setHgap(8);
        top.setVgap(8);
        top.setPadding(new Insets(0, 0, 10, 0));

        top.add(new Label("Filter Module:"), 0, 0);
        top.add(filterModuleField, 1, 0);

        top.add(new Label("Filter Status:"), 2, 0);
        top.add(filterStatusBox, 3, 0);

        top.add(applyFilterBtn, 4, 0);
        top.add(clearFilterBtn, 5, 0);
        top.add(refreshBtn, 6, 0);

        root.setTop(top);
        root.setCenter(table);
    }

    public Parent getRoot() {
        return root;
    }
}
