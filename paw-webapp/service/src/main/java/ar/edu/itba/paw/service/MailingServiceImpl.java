package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Properties;

@Service
public class MailingServiceImpl implements MailingService{

    @Value("${mail.from}")
    private String FROMADDRESS;
    @Value("${mail.username}")
    private String USERNAME;
    @Value("${mail.password}")
    private String PASSWORD;

    @Autowired
    private MessageSource messageSource;
//    @Value("${Mail.message}")
//    private String message;

    @Async
    @Override
    public void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation, Locale locale) {
        Properties properties=setProperties();
        sendCustomerConfirmation(restaurant,customer,reservation,properties, locale);
        sendRestaurantConfirmation(restaurant,customer,reservation,properties, locale);
    }
    private void sendCustomerConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties props, Locale locale) {
        String subject = messageSource.getMessage("Mail.subject.customer", new Object[]{reservation.getSecurityCode(), restaurant.getMail()}, locale);
        String stringBuilder = customerConfirmationTemplate(restaurant, reservation, locale);
        sendEmail(props,customer.getMail(),subject, stringBuilder);
    }

    private String customerConfirmationTemplate(Restaurant restaurant, Reservation reservation, Locale locale) {
        return messageSource.getMessage("Mail.message.customer", new Object[]{reservation.getSecurityCode(), restaurant.getMail()}, locale);
    }

    private void sendRestaurantConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties properties, Locale locale) {
        String subject = messageSource.getMessage("Mail.subject.restaurant", new Object[]{reservation.getSecurityCode(), restaurant.getMail()}, locale);
        String message= restaurantConfirmationTemplate(customer, reservation, locale);
        sendEmail(properties,restaurant.getMail(),subject,message);
    }

    private String restaurantConfirmationTemplate(Customer customer , Reservation reservation, Locale locale) {
        return messageSource.getMessage("Mail.message.restaurant", new Object[]{customer.getCustomerName(), reservation.getReservationOnlyDate(), reservation.getReservationHour()}, locale);
    }

    @Async
    @Override
    public void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation, Locale locale) {
        Properties properties=setProperties();
        sendCustomerCancellation(restaurant,customer,reservation,properties, locale);
        sendRestaurantCancellation(restaurant,customer,reservation,properties, locale);
    }

    private void sendCustomerCancellation(Restaurant restaurant,Customer customer,Reservation reservation, Properties properties, Locale locale) {
        String subject = messageSource.getMessage("Mail.subject.customer.cancel", new Object[]{reservation.getSecurityCode(), restaurant.getMail()}, locale);
        String message=customerCancellationTemplate(restaurant, reservation, locale);
        sendEmail(properties,customer.getMail(),subject,message);
    }

    private String customerCancellationTemplate(Restaurant restaurant, Reservation reservation, Locale locale) {
        return messageSource.getMessage("Mail.message.customer.cancel", new Object[]{reservation.getReservationOnlyDate(), reservation.getReservationHour(), restaurant.getRestaurantName()}, locale);
    }

    private void sendRestaurantCancellation(Restaurant restaurant,Customer customer,Reservation reservation,Properties properties, Locale locale) {
        String subject = messageSource.getMessage("Mail.subject.restaurant.cancel", new Object[]{reservation.getSecurityCode(), restaurant.getMail()}, locale);
        String message=restaurantCancellationTemplate(customer, reservation, locale);
        sendEmail(properties, restaurant.getMail(),subject,message);
    }

    private String restaurantCancellationTemplate(Customer customer, Reservation reservation, Locale locale) {
        return messageSource.getMessage("Mail.message.restaurant.cancel", new Object[]{reservation.getReservationOnlyDate(), reservation.getReservationHour(), customer.getCustomerName()}, locale);
    }

    public void sendEmail(Properties properties, String toEmailAddress,
                          String subject, String messageContent) {
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME,
                                PASSWORD);
                    }
                });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROMADDRESS));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAddress));

            msg.setSubject(subject);
            msg.setContent(messageContent, "text/html");
            // msg.setText(messageText);
            Transport.send(msg);
        } catch (Exception ex) {
//            LOGGER.error("Email failed ", ex);
        };
    }


    private Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        return properties;
    }

}


