package ar.edu.itba.paw.model;

import java.util.Objects;

public class OrderItem {
    private long reservationId;
    private long dishId;
    private float unitPrice;
    private int quantity;
    private int status;

    public OrderItem(long reservationId, long dishId, float unitPrice, int quantity , int status) {
        this.reservationId = reservationId;
        this.dishId = dishId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getReservationId() {
        return reservationId;
    }

    public long getDishId() {
        return dishId;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return reservationId == orderItem.reservationId && dishId == orderItem.dishId && Float.compare(orderItem.unitPrice, unitPrice) == 0 && quantity == orderItem.quantity && status == orderItem.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, dishId, unitPrice, quantity, status);
    }
}
