package uk.ac.bcu.unitracker.service;

import org.junit.jupiter.api.Test;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TrackerServiceExceptionTest {

    @Test
    void updateStatus_unknownId_throwsException() throws Exception {
        Path temp = Files.createTempFile("assignments", ".csv");
        TrackerService service = new TrackerService(new AssignmentCsvRepository(temp));

        assertThrows(NoSuchElementException.class,
                () -> service.updateStatus("A999", AssignmentStatus.SUBMITTED));
    }
}
