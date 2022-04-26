package ar.edu.itba.paw.model;

public enum Roles {
    RESTAURANT("ROLE_RESTAURANT"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private String description;

    Roles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}