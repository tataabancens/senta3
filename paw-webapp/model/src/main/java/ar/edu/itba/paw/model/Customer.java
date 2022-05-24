package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_customerid_seq")
    @SequenceGenerator(sequenceName = "customer_customerid_seq", name = "customer_customerid_seq", allocationSize = 1)
    @Column(name = "customerid")
    private long id;

    @Column(length = 50, nullable = false)
    private String customerName;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String mail;

    @Column(nullable = false)
    private int points;

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    /* default */ Customer() {
        // Just for hibernate
    }


    public Customer(String customerName, String phone, String mail, int points) {
        super();
        this.customerName = customerName;
        this.phone = phone;
        this.mail = mail;
        this.points = points;
    }

    public Customer(String customerName, String phone, String mail, long userId, int points) {
        super();
        this.customerName = customerName;
        this.phone = phone;
        this.mail = mail;
        this.points = points;
//        this.userId = userId;
    }

    public Customer(long customerId, String customerName, String phone, String mail, int points) {
        this.id = customerId;
        this.customerName = customerName;
        this.phone = phone;
        this.mail = mail;
        this.points = points;
    }

    public Reservation createReservation(Restaurant restaurant, Customer customer, int reservationHour, int qPeople, Timestamp startedAtTime) {
        final Reservation reservation = new Reservation(restaurant, customer, reservationHour, ReservationStatus.MAYBE_RESERVATION.ordinal(), qPeople, startedAtTime);
        reservations.add(reservation);
        return reservation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getUserId() {
        return getUser().getId();
    }

//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
