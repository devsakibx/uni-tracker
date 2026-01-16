package uk.ac.bcu.unitracker.domain;

import java.util.Objects;

public class Student {
    private final String studentId;
    private String fullName;
    private String email;

    public Student(String studentId, String fullName, String email) {
        this.studentId = Objects.requireNonNull(studentId);
        this.fullName = Objects.requireNonNull(fullName);
        this.email = Objects.requireNonNull(email);
    }

    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }

    public void setFullName(String fullName) { this.fullName = Objects.requireNonNull(fullName); }
    public void setEmail(String email) { this.email = Objects.requireNonNull(email); }
}
