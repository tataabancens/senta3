package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.FullOrderItem;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Restaurant;

import java.util.List;


public interface MailingService{

    void sendConfirmationEmail(Restaurant restaurant , Customer customer);

    void sendReceiptEmail(Restaurant restaurant , Customer customer);

    void sendOrderEmail(Restaurant restaurant, Customer customer, List<FullOrderItem> orderItems);
}
