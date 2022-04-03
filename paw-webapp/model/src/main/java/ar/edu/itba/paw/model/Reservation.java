package ar.edu.itba.paw.model;

import java.sql.Timestamp;

public class Reservation {
    private long reservationId;
    private long restaurantId;
    private Timestamp reservationDate;

    public Reservation(long reservationId, long restaurantId, Timestamp reservationDate){
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.reservationDate = reservationDate;
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

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }
}
