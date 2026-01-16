package uk.ac.bcu.unitracker.persistence;

import uk.ac.bcu.unitracker.domain.Assignment;
import uk.ac.bcu.unitracker.domain.enums.AssignmentStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssignmentCsvRepository {

    private static final String HEADER = "id,moduleCode,title,dueDate,status,notes";
    private final Path csvPath;

    public AssignmentCsvRepository(Path csvPath) {
        this.csvPath = csvPath;
    }

    public List<Assignment> loadAll() throws IOException {
        if (!Files.exists(csvPath)) return new ArrayList<>();

        List<String> lines = Files.readAllLines(csvPath, StandardCharsets.UTF_8);
        List<Assignment> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            if (i == 0 && line.equalsIgnoreCase(HEADER)) continue;

            String[] parts = splitCsvLine(line);
            if (parts.length < 6) continue;

            String id = CsvUtil.unescape(parts[0]);
            String moduleCode = CsvUtil.unescape(parts[1]);
            String title = CsvUtil.unescape(parts[2]);
            LocalDate dueDate = LocalDate.parse(CsvUtil.unescape(parts[3]));
            AssignmentStatus status = AssignmentStatus.valueOf(CsvUtil.unescape(parts[4]));
            String notes = CsvUtil.unescape(parts[5]);

            Assignment a = new Assignment(id, moduleCode, title, dueDate);
            a.setStatus(status);
            a.setNotes(notes);
            result.add(a);
        }
        return result;
    }

    public void saveAll(List<Assignment> assignments) throws IOException {
        Files.createDirectories(csvPath.getParent());

        List<String> lines = new ArrayList<>();
        lines.add(HEADER);

        for (Assignment a : assignments) {
            String line = String.join(",",
                    CsvUtil.escape(a.getId()),
                    CsvUtil.escape(a.getModuleCode()),
                    CsvUtil.escape(a.getTitle()),
                    CsvUtil.escape(a.getDueDate().toString()),
                    CsvUtil.escape(a.getStatus().name()),
                    CsvUtil.escape(a.getNotes())
            );
            lines.add(line);
        }

        Files.write(csvPath, lines, StandardCharsets.UTF_8);
    }

    // Minimal CSV splitter supporting quoted fields with "" escaping (enough for our project scope).
    private static String[] splitCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    cur.append(c);
                }
            } else {
                if (c == ',') {
                    fields.add(cur.toString());
                    cur.setLength(0);
                } else if (c == '"') {
                    inQuotes = true;
                } else {
                    cur.append(c);
                }
            }
        }
        fields.add(cur.toString());
        return fields.toArray(new String[0]);
    }
}

