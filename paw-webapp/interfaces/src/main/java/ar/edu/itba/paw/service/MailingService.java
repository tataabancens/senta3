package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Properties;


public interface MailingService{

    void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation);

    void sendReceiptEmail(Restaurant restaurant , Customer customer);

    void sendOrderEmail(Restaurant restaurant, Customer customer, List<FullOrderItem> orderItems);

    void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation);

    @Async
    void sendEmail(Properties properties, String toEmailAddress,
                   String subject, String messageText);
}
