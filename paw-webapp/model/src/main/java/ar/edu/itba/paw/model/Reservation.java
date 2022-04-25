package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Reservation {
    private long reservationId;
    private long restaurantId;
    private long customerId;
    //private Timestamp reservationDate;
    private int reservationHour;
    private ReservationStatus reservationStatus;

    public Reservation(long reservationId, long restaurantId, int reservationHour, long customerId, int reservationStatus){
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.reservationHour = reservationHour;
        this.customerId = customerId;
        this.reservationStatus = ReservationStatus.values()[reservationStatus];
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
}
