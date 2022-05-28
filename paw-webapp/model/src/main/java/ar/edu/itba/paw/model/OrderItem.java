package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.OrderItemStatus;

import javax.persistence.*;
import java.util.Objects;
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_id_seq")
    @SequenceGenerator(sequenceName = "orderitem_id_seq", name = "orderitem_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservationid", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dishid", nullable = false)
    private Dish dish;

    @Column(nullable = false)
    private float unitPrice;

    @Column(nullable = false)
    private int quantity;

    @Enumerated
    @Column(nullable = false)
    private OrderItemStatus status;


    public OrderItem() {
        // Just for hibernate and forms
    }

    public OrderItem(Reservation reservation, Dish dish, float unitPrice, int quantity, OrderItemStatus status) {
        this.reservation = reservation;
        this.dish = dish;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem that = (OrderItem) o;
        return id == that.id && Float.compare(that.unitPrice, unitPrice) == 0 && quantity == that.quantity && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unitPrice, quantity, status);
    }
}
