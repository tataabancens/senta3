package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.scheduling.annotation.Async;

import java.util.Locale;
import java.util.Properties;


public interface MailingService{

    void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation, Locale locale);

    void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation, Locale locale);
}
