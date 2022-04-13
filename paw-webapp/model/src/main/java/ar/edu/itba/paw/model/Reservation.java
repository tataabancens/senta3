package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Reservation {
    private long reservationId;
    private long restaurantId;
    private long customerId;
    private Timestamp reservationDate;
    private ReservationStatus reservationStatus;

    public Reservation(long reservationId, long restaurantId, Timestamp reservationDate, long customerId, int reservationStatus){
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.reservationDate = reservationDate;
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

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
