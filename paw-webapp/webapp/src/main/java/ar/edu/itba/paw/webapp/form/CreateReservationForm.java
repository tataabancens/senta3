package ar.edu.itba.paw.webapp.form;

public class CreateReservationForm {

    private long restaurantId;

    private int hour;
    private int qPeople;

    private String reservationDate;

    public CreateReservationForm() {

    }

//    private LocalDateTime startedAtTime;


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