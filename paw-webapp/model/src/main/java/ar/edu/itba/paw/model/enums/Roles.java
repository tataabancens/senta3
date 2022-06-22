package ar.edu.itba.paw.model.enums;

public enum Roles {
    RESTAURANT("ROLE_RESTAURANT"),
    ANONYMOUS("ROLE_ANONYMOUS"),
    CUSTOMER("ROLE_CUSTOMER"),
    WAITER("ROLE_WAITER"),
    KITCHEN("ROLE_KITCHEN");

    private String description;

    Roles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
