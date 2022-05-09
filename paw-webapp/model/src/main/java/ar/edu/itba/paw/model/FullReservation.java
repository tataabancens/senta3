package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ReservationStatus;

import java.sql.Timestamp;

public class FullReservation {

    private long reservationId;
    private long restaurantId;
    private long customerId;
    private int reservationHour;
    private Timestamp startedAtTime;
    private ReservationStatus reservationStatus;
    private int qPeople;
    private String customerName;
    private String restaurantName;

    public FullReservation(long reservationId, long restaurantId, long customerId,
                           int reservationHour, int reservationStatus, int qPeople, String customerName, String restaurantName, Timestamp startedAtTime) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.reservationHour = reservationHour;
        this.reservationStatus = ReservationStatus.values()[reservationStatus];
        this.qPeople = qPeople;
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.startedAtTime = startedAtTime;
    }

    public Timestamp getStartedAtTime() {
        return startedAtTime;
    }

    public void setStartedAtTime(Timestamp startedAtTime) {
        this.startedAtTime = startedAtTime;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getReservationHour() {
        return reservationHour;
    }

    public void setReservationHour(int reservationHour) {
        this.reservationHour = reservationHour;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public int getqPeople() {
        return qPeople;
    }

    public void setqPeople(int qPeople) {
        this.qPeople = qPeople;
    }
}
