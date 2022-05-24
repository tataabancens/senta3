package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.webapp.form.CustomValidator.OrderConstraint;

public class OrderForm {
    @OrderConstraint
    private OrderItem orderItem;


    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
