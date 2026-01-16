package uk.ac.bcu.unitracker.student;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.service.TrackerService;

import java.io.IOException;
import java.time.LocalDate;

public class StudentController {

    private final TrackerService service;
    private final StudentView view = new StudentView();

    public StudentController(TrackerService service) {
        this.service = service;

        view.refreshBtn.setOnAction(e -> refreshTable());
        view.addBtn.setOnAction(e -> onAdd());
        view.setStatusBtn.setOnAction(e -> onSetStatus());
        view.saveNotesBtn.setOnAction(e -> onSaveNotes());
        view.deleteBtn.setOnAction(e -> onDelete());

        view.table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                view.selectedIdField.setText(newV.getId());
                view.statusBox.setValue(newV.getStatus());
                view.notesArea.setText(newV.getNotes());
            }
        });

        refreshTable();
    }

    public Parent getView() {
        return view.getRoot();
    }

    private void refreshTable() {
        view.table.setItems(FXCollections.observableArrayList(service.getAllAssignments()));
    }

    private void onAdd() {
        String moduleCode = view.moduleCodeField.getText().trim();
        String title = view.titleField.getText().trim();
        LocalDate due = view.dueDatePicker.getValue();

        if (moduleCode.isEmpty() || title.isEmpty() || due == null) {
            showError("Please fill Module Code, Title, and Due Date.");
            return;
        }

        try {
            service.addAssignment(moduleCode, title, due);
            view.moduleCodeField.clear();
            view.titleField.clear();
            view.dueDatePicker.setValue(null);
            refreshTable();
        } catch (IOException ex) {
            showError("Save failed: " + ex.getMessage());
        }
    }

    private void onSetStatus() {
        String id = view.selectedIdField.getText().trim();
        AssignmentStatus status = view.statusBox.getValue();
        if (id.isEmpty() || status == null) {
            showError("Select an assignment and status first.");
            return;
        }
        try {
            service.updateStatus(id, status);
            refreshTable();
        } catch (Exception ex) {
            showError("Update failed: " + ex.getMessage());
        }
    }

    private void onSaveNotes() {
        String id = view.selectedIdField.getText().trim();
        if (id.isEmpty()) {
            showError("Select an assignment first.");
            return;
        }
        try {
            service.updateNotes(id, view.notesArea.getText());
            refreshTable();
        } catch (Exception ex) {
            showError("Save notes failed: " + ex.getMessage());
        }
    }

    private void onDelete() {
        String id = view.selectedIdField.getText().trim();
        if (id.isEmpty()) {
            showError("Select an assignment first.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm delete");
        confirm.setHeaderText("Delete assignment " + id + "?");
        confirm.setContentText("This action cannot be undone.");
        var result = confirm.showAndWait();  // returns Optional<ButtonType> [web:217]

        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return; // cancelled [web:220]
        }

        try {
            service.deleteAssignment(id);
            view.selectedIdField.clear();
            view.notesArea.clear();
            view.statusBox.setValue(null);
            refreshTable();
        } catch (Exception ex) {
            showError("Delete failed: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("UniTracker");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
