package com.loohp.limbo.plugins.scoreboard;

public class Objective {

    private final String name;
    private final ObjectiveType type;
    private String value;

    public Objective(String name, ObjectiveType type) {
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ObjectiveType getType() {
        return type;
    }

    public enum ObjectiveType {
        INTEGER(0),
        HEARTS(1);

        private final int val;

        ObjectiveType(int val) {
            this.val = val;
        }

        public int getValue() {
            return val;
        }
    }
}
