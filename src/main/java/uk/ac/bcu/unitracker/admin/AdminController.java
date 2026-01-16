package uk.ac.bcu.unitracker.admin;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.persistence.CsvUtil;
import uk.ac.bcu.unitracker.service.TrackerService;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class AdminController {

    private final TrackerService service;
    private final AdminView view = new AdminView();

    public AdminController(TrackerService service) {
        this.service = service;

        view.refreshBtn.setOnAction(e -> refresh());
        view.applyFilterBtn.setOnAction(e -> applyFilter());
        view.clearFilterBtn.setOnAction(e -> {
            view.filterModuleField.clear();
            view.filterStatusBox.setValue(null);
            refresh();
        });
        view.exportBtn.setOnAction(e -> exportCsv());

        refresh();
    }

    public Parent getView() {
        return view.getRoot();
    }

    private void refresh() {
        view.table.setItems(FXCollections.observableArrayList(service.getAllAssignments()));
    }

    private void applyFilter() {
        String moduleFilter = view.filterModuleField.getText() == null ? "" : view.filterModuleField.getText().trim();
        AssignmentStatus statusFilter = view.filterStatusBox.getValue();

        List<Assignment> filtered = service.getAllAssignments().stream()
                .filter(a -> moduleFilter.isEmpty()
                        || a.getModuleCode().toLowerCase(Locale.ROOT).contains(moduleFilter.toLowerCase(Locale.ROOT)))
                .filter(a -> statusFilter == null || a.getStatus() == statusFilter)
                .collect(Collectors.toList());

        view.table.setItems(FXCollections.observableArrayList(filtered));
    }

    private void exportCsv() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Export assignments CSV");
        chooser.setInitialFileName("assignments_export.csv");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));

        File file = chooser.showSaveDialog(view.getRoot().getScene().getWindow());
        if (file == null) return; // user cancelled [web:215]

        try {
            List<Assignment> rows = view.table.getItems();
            String header = "id,moduleCode,title,dueDate,status,notes";
            String body = rows.stream()
                    .map(a -> String.join(",",
                            CsvUtil.escape(a.getId()),
                            CsvUtil.escape(a.getModuleCode()),
                            CsvUtil.escape(a.getTitle()),
                            CsvUtil.escape(a.getDueDate().toString()),
                            CsvUtil.escape(a.getStatus().name()),
                            CsvUtil.escape(a.getNotes())
                    ))
                    .collect(Collectors.joining("\n"));

            String out = header + "\n" + body + "\n";
            Files.writeString(file.toPath(), out, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            var alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setHeaderText("Export failed");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
