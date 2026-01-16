package uk.ac.bcu.unitracker.domain;

import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Assignment {
    private final String id;
    private final String moduleCode;
    private String title;
    private LocalDate dueDate;
    private AssignmentStatus status;
    private String notes;

    public Assignment(String id, String moduleCode, String title, LocalDate dueDate) {
        this.id = Objects.requireNonNull(id);
        this.moduleCode = Objects.requireNonNull(moduleCode);
        this.title = Objects.requireNonNull(title);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.status = AssignmentStatus.NOT_STARTED;
        this.notes = "";
    }

    public String getId() { return id; }
    public String getModuleCode() { return moduleCode; }
    public String getTitle() { return title; }
    public LocalDate getDueDate() { return dueDate; }
    public AssignmentStatus getStatus() { return status; }
    public String getNotes() { return notes; }

    public void setTitle(String title) { this.title = Objects.requireNonNull(title); }
    public void setDueDate(LocalDate dueDate) { this.dueDate = Objects.requireNonNull(dueDate); }
    public void setStatus(AssignmentStatus status) { this.status = Objects.requireNonNull(status); }
    public void setNotes(String notes) { this.notes = Objects.requireNonNull(notes); }
}
