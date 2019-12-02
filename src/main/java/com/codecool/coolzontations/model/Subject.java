package com.codecool.coolzontations.model;

public enum Subject {

    PYTHON("PYTHON"), JAVASCRIPT("JAVASCRIPT"), REACT("REACT"), JAVA("JAVA"), CSHARP("CSHARP"), SOFTSKILLS("SOFTSKILLS");

    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    Subject(String stringValue) {
        this.stringValue = stringValue;
    }

    public static Subject getSubjectByName(String name) {
        for (Subject subject : Subject.values()) {
            if (subject.getStringValue().equals(name)) {
                return subject;
            }
        }
        return null;
    }
}
