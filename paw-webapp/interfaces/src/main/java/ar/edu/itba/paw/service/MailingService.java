package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.util.List;


public interface MailingService{

    void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation);

    void sendReceiptEmail(Restaurant restaurant , Customer customer);

    void sendOrderEmail(Restaurant restaurant, Customer customer, List<FullOrderItem> orderItems);
}
