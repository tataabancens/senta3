package ar.edu.itba.paw.model;

import java.sql.Timestamp;


public class Order {
    private int orderId;
    private int orderNumber;
    private int customerId;
    private float amount;

    public int getOrderId() {
        return orderId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public float getAmount() {
        return amount;
    }

    public Order(int orderId, int orderNumber, int customerId, float amount) {
        super();
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.amount = amount;
    }
}
