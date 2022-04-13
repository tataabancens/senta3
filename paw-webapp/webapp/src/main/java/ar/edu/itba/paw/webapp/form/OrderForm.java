package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.OrderItem;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class OrderForm {
    private OrderItem orderItem;


    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
