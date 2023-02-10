package ar.edu.itba.paw.model.enums;

public enum ReservationStatus {
    OPEN("OPEN"),
    SEATED("SEATED"),
    CHECK_ORDERED("CHECK_ORDERED"),
    FINISHED("FINISHED"),
    CANCELED("CANCELED");

    private String name;

    ReservationStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
