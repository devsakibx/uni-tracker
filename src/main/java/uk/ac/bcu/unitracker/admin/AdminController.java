package uk.ac.bcu.unitracker.admin;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.service.TrackerService;

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
}
