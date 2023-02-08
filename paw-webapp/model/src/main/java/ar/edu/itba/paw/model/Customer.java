package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ReservationStatus;
//import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToOne(fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Reservation createReservation(Restaurant restaurant, int reservationHour, int qPeople, LocalDateTime startedAtTime, LocalDateTime reservationDate) {
        final Reservation reservation = new Reservation(restaurant, this, reservationHour, ReservationStatus.OPEN.ordinal(), qPeople, startedAtTime, reservationDate);
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> getReservationsByStatusList(List<ReservationStatus> statusList) {
        List<Reservation> toRet = new ArrayList<>(reservations);
        toRet.removeIf(r -> !statusList.contains(r.getReservationStatus()));
        return toRet;
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

    public Long getUserId() {
        User user = getUser();
        if(user!=null){
            return user.getId();
        }
        return null;
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
        return new ArrayList<>(reservations);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }


}
