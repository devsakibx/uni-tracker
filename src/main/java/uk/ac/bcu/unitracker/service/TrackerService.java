package uk.ac.bcu.unitracker.service;

import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TrackerService {

    private final AssignmentCsvRepository repo;
    private final List<Assignment> assignments;

    public TrackerService(AssignmentCsvRepository repo) throws IOException {
        this.repo = Objects.requireNonNull(repo);
        this.assignments = new ArrayList<>(repo.loadAll());
    }

    public List<Assignment> getAllAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    public Assignment addAssignment(String moduleCode, String title, LocalDate dueDate) throws IOException {
        String id = nextId();
        Assignment a = new Assignment(id, moduleCode, title, dueDate);
        assignments.add(a);
        repo.saveAll(assignments);
        return a;
    }

    public void updateStatus(String assignmentId, AssignmentStatus status) throws IOException {
        Assignment a = findByIdOrThrow(assignmentId);
        a.setStatus(status);
        repo.saveAll(assignments);
    }

    public void updateNotes(String assignmentId, String notes) throws IOException {
        Assignment a = findByIdOrThrow(assignmentId);
        a.setNotes(notes);
        repo.saveAll(assignments);
    }

    public void deleteAssignment(String assignmentId) throws IOException {
        Assignment a = findByIdOrThrow(assignmentId);
        assignments.remove(a);
        repo.saveAll(assignments);
    }

    private Assignment findByIdOrThrow(String id) {
        return assignments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Assignment not found: " + id));
    }

    private String nextId() {
        int max = 0;
        for (Assignment a : assignments) {
            String s = a.getId();
            if (s != null && s.startsWith("A")) {
                try {
                    max = Math.max(max, Integer.parseInt(s.substring(1)));
                } catch (NumberFormatException ignored) { }
            }
        }
        return "A" + (max + 1);
    }
}
