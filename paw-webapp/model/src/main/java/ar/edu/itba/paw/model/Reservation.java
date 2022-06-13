package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime startedAtTime;

    @Column(nullable = false)
    private LocalDateTime reservationDate;

    @Enumerated
    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @Column(nullable = false)
    private int qPeople;

    @Column(nullable = false)
    private boolean reservationDiscount;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private int tableNumber;

    @Column(nullable = false, length = 6, columnDefinition = "varchar(6) default 'A'")
    private String securityCode = String.valueOf('A');

    @Column(nullable = false)
    private boolean hand;

    @Column(nullable = false)
    private boolean isToday;

    /* default */ Reservation() {
        // Just for hibernate
    }

    public Reservation(Restaurant restaurant, Customer customer, int reservationHour, int reservationStatus, int qPeople, LocalDateTime startedAtTime, LocalDateTime reservationDate) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.reservationHour = reservationHour;
        this.reservationStatus = ReservationStatus.values()[reservationStatus];
        this.qPeople = qPeople;
        this.startedAtTime = startedAtTime;
        this.reservationDate = reservationDate;
    }
    /* pasados al dao
    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public List<OrderItem> getOrderItemsByStatusList(List<OrderItemStatus> statusList) {
        List<OrderItem> toRet = new ArrayList<>(orderItems);
        toRet.removeIf(or -> !statusList.contains(or.getStatus()));
        return toRet;
    }
     */

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderItem createOrderItem(Dish dish, int quantity) {
        OrderItem orderItem = new OrderItem(this, dish, dish.getPrice(), quantity, OrderItemStatus.SELECTED);
        orderItems.add(orderItem);
        return orderItem;
    }

    public LocalDateTime getStartedAtTime() {
        return startedAtTime;
    }

    public void setStartedAtTime(LocalDateTime startedAtTime) {
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

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationOnlyDate(){ //aparece que nadie lo usa pero si lo usan unos jsp
        String date = this.reservationDate.getDayOfMonth() +
                "/" +
                this.reservationDate.getMonthValue() +
                "/" +
                this.reservationDate.getYear();
        return date;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
//    public List<OrderItem> getOrderItems() {
//        return orderItems;

    public boolean isHand() {
        return hand;
    }

    public void setHand(boolean hand) {
        this.hand = hand;
    }

    public void setIsToday(boolean b) {
        this.isToday=b;
    }

    public boolean getIsToday() {
        return this.isToday;
    }
//    }
}
