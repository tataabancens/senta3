package ar.edu.itba.paw.model;

import java.util.Objects;

public class FullOrderItem {

    private long orderItemId;
    private long reservationId;
    private long dishId;
    private float unitPrice;
    private int quantity;
    private OrderItemStatus status;
    private String dishName;

    public FullOrderItem(long orderItemId, long reservationId, long dishId, float unitPrice, int quantity , int status, String dishName) {
        this.orderItemId = orderItemId;
        this.reservationId = reservationId;
        this.dishId = dishId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = OrderItemStatus.values()[status];
        this.dishName = dishName;
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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }
}
