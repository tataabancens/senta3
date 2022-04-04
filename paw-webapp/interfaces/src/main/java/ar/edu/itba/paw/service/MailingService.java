package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.util.Properties;


public interface MailingService{

    public void sendEmail(String host, String username, String password, String fromEmailAddress, String toEmailAddress,
                          String subject, String messageText);


}
