package ar.edu.itba.paw.model;

public enum ReservationStatus {
    OPEN("OPEN"),
    SEATED("SEATED"),
    CHECK_ORDERED("CHECK_ORDERED"),
    FINISHED("FINISHED"),
    CANCELED("CANCELED"),
    MAYBE_RESERVATION("MAYBE_RESERVATION"),
    REMOVED("REMOVED");

    private String name;

    ReservationStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
