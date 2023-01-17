package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.enums.ReservationStatus;

public class ReservationPatchForm {

    private Integer hour;
    private Integer qPeople;

    private ReservationStatus reservationStatus;

    private Boolean discount;

    private String reservationDate;

    private Integer table;

    private Boolean hand;


    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getqPeople() {
        return qPeople;
    }

    public void setqPeople(Integer qPeople) {
        this.qPeople = qPeople;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Boolean isDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Boolean isHand() {
        return hand;
    }

    public void setHand(Boolean hand) {
        this.hand = hand;
    }
}
