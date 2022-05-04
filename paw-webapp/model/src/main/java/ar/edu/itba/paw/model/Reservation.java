package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Reservation {
    private long reservationId;
    private long restaurantId;
    private long customerId;
    private Timestamp startedAtTime;
    private int reservationHour;
    private ReservationStatus reservationStatus;
    private int qPeople;
    private boolean reservationDiscount;

    public Reservation(long reservationId, long restaurantId, int reservationHour, long customerId, int reservationStatus, int qPeople, boolean reservationDiscount, Timestamp startedAtTime){
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.reservationHour = reservationHour;
        this.customerId = customerId;
        this.reservationStatus = ReservationStatus.values()[reservationStatus];
        this.qPeople = qPeople;
        this.reservationDiscount = reservationDiscount;
        this.startedAtTime = startedAtTime;
    }

    public Timestamp getStartedAtTime() {
        return startedAtTime;
    }

    public void setStartedAtTime(Timestamp startedAtTime) {
        this.startedAtTime = startedAtTime;
    }

    public boolean isReservationDiscount() {
        return reservationDiscount;
    }

    public void setReservationDiscount(boolean reservationDiscount) {
        this.reservationDiscount = reservationDiscount;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getReservationHour() {
        return reservationHour;
    }

    public void setReservationHour(int reservationHour) {
        this.reservationHour = reservationHour;
    }

    public int getqPeople() {
        return qPeople;
    }

    public void setqPeople(int qPeople) {
        this.qPeople = qPeople;
    }
}
