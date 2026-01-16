package uk.ac.bcu.unitracker.service;

import org.junit.jupiter.api.Test;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackerServiceTest {

    @Test
    void addAssignment_persistsAndIncrementsId() throws Exception {
        Path temp = Files.createTempFile("assignments", ".csv");

        TrackerService service = new TrackerService(new AssignmentCsvRepository(temp));
        service.addAssignment("CI553", "CW1", LocalDate.of(2026, 2, 1));
        service.addAssignment("CI553", "CW2", LocalDate.of(2026, 3, 1));

        assertEquals(2, service.getAllAssignments().size());
        assertEquals("A2", service.getAllAssignments().get(1).getId());
    }

    @Test
    void updateStatus_changesStatus() throws Exception {
        Path temp = Files.createTempFile("assignments", ".csv");

        TrackerService service = new TrackerService(new AssignmentCsvRepository(temp));
        var a = service.addAssignment("CI553", "CW1", LocalDate.of(2026, 2, 1));
        service.updateStatus(a.getId(), AssignmentStatus.SUBMITTED);

        assertEquals(AssignmentStatus.SUBMITTED, service.getAllAssignments().get(0).getStatus());
    }
}
