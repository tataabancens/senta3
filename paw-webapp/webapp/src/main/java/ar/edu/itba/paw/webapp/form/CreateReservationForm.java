package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Reservation;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class CreateReservationForm {

    private long restaurantId;

    private int hour;
    private int qPeople;

    public CreateReservationForm() {

    }

//    private LocalDateTime startedAtTime;
    private String reservationDate;

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getqPeople() {
        return qPeople;
    }

    public void setqPeople(int qPeople) {
        this.qPeople = qPeople;
    }
}