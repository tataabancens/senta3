package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ReservationStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_reservationid_seq")
    @SequenceGenerator(sequenceName = "reservation_reservationid_seq", name = "reservation_reservationid_seq", allocationSize = 1)
    @Column(name = "reservationid")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurantid", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private int reservationHour;

    @Column(nullable = false)
    private Timestamp startedAtTime;

    @Enumerated
    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private int qPeople;

    @Column(nullable = false)
    private boolean reservationDiscount;

    /* default */ Reservation() {
        // Just for hibernate
    }

    public Reservation(Restaurant restaurant, Customer customer, int reservationHour, int reservationStatus, int qPeople, Timestamp startedAtTime) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.reservationHour = reservationHour;
        this.reservationStatus = ReservationStatus.values()[reservationStatus];
        this.qPeople = qPeople;
        this.startedAtTime = startedAtTime;
    }

    public Timestamp getStartedAtTime() {
        return startedAtTime;
    }

    public void setStartedAtTime(Timestamp startedAtTime) {
        this.startedAtTime = startedAtTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public boolean isReservationDiscount() {
        return reservationDiscount;
    }

    public void setReservationDiscount(boolean reservationDiscount) {
        this.reservationDiscount = reservationDiscount;
    }
}
