package uk.ac.bcu.unitracker.domain;

import java.util.Objects;

public class Module {
    private final String code;
    private String name;

    public Module(String code, String name) {
        this.code = Objects.requireNonNull(code);
        this.name = Objects.requireNonNull(name);
    }

    public String getCode() { return code; }
    public String getName() { return name; }

    public void setName(String name) { this.name = Objects.requireNonNull(name); }
}
