package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;

import java.util.Locale;


public interface MailingService{

    void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation, Locale locale);

    void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation, Locale locale);
}
