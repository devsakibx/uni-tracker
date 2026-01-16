package uk.ac.bcu.unitracker.persistence;

public final class CsvUtil {
    private CsvUtil() {}

    public static String escape(String value) {
        if (value == null) return "";
        boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return mustQuote ? ("\"" + escaped + "\"") : escaped;
    }

    public static String unescape(String value) {
        if (value == null) return "";
        String v = value.trim();
        if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
            v = v.substring(1, v.length() - 1);
            v = v.replace("\"\"", "\"");
        }
        return v;
    }
}
