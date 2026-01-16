import org.junit.jupiter.api.Test;
import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.persistence.AssignmentCsvRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssignmentCsvRepositoryTest {

    @Test
    void saveThenLoad_roundTrip() throws Exception {
        Path temp = Files.createTempFile("assignments", ".csv");

        Assignment a = new Assignment("A1", "CI553", "OOP Coursework", LocalDate.of(2026, 2, 1));
        a.setNotes("Needs, commas and \"quotes\"");

        AssignmentCsvRepository repo = new AssignmentCsvRepository(temp);
        repo.saveAll(List.of(a));

        List<Assignment> loaded = repo.loadAll();
        assertEquals(1, loaded.size());
        assertEquals("A1", loaded.get(0).getId());
        assertEquals("CI553", loaded.get(0).getModuleCode());
        assertEquals("Needs, commas and \"quotes\"", loaded.get(0).getNotes());
    }
}
