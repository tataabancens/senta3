package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailingServiceImpl implements MailingService{
    private final String FROMADDRESS="noreply@sentate.com";
    private final String USERNAME="sentate.paw";
    private final String PASSWORD="xblgoodfhlnunfmq";

    @Override
    public void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation){
        Properties properties=setProperties();
        sendCustomerConfirmation(restaurant,customer,reservation,properties);
        sendRestaurantConfirmation(restaurant,customer,reservation,properties);
    }
    private void sendCustomerConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties props){
        String subject="Confirmacion de reserva";
        String stringBuilder = customerConfirmationTemplate(restaurant, reservation);
        sendEmail(props,customer.getMail(),subject, stringBuilder);
    }

    private String customerConfirmationTemplate(Restaurant restaurant, Reservation reservation){
        return "<div style=\"padding:5px; background-color: #fff4e4; \">" +
                "<p style=\"font-size: 1.9rem; color: #ff441f; font-weight:bold; font-style:italic\">Senta3</p>" +
                "</div>" +
                "<div style=\"text-align: center\">" +
                "<p style=\"font-size: 1.9rem; font-weight:bold\">Tu reserva fue confirmada!</p>" +
                "<p style=\"font-size: 1.4rem\">Tu codigo de reserva es: " + reservation.getSecurityCode() + "</p>" +
                "<p style=\"font-size: 1.1rem\">Si necesitas contactar al restaurant, este es su email:" + restaurant.getMail() + "</p>" +
                "</div>";
    }

    private void sendRestaurantConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties properties){
        String subject="Un cliente realizo una reserva";
        String message= restaurantConfirmationTemplate(customer, reservation);
        sendEmail(properties,restaurant.getMail(),subject,message);
    }

    private String restaurantConfirmationTemplate(Customer customer , Reservation reservation){
        return "<div style=\"padding:5px; background-color: #fff4e4; \">" +
                "<p style=\"font-size: 1.9rem; color: #ff441f; font-weight:bold; font-style:italic\">Senta3</p>" +
                "</div>" +
                "<div style=\"text-align: center\">" +
                "<p style=\"font-size: 1.9rem; font-weight:bold\">El cliente: " + customer.getCustomerName() + "</p>" +
                "<p style=\"font-size: 1.4rem\">realizo una reserva para las: " + reservation.getReservationHour() +  "</p>" +
                "</div>";
    }

    @Override
    public void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation){
        Properties properties=setProperties();
        sendCustomerCancellation(restaurant,customer,reservation,properties);
        sendRestaurantCancellation(restaurant,customer,reservation,properties);
    }

    private void sendCustomerCancellation(Restaurant restaurant,Customer customer,Reservation reservation, Properties properties){
        String subject="Se cancelo tu reserva";
        String message=customerCancellationTemplate(restaurant, reservation);
        sendEmail(properties,customer.getMail(),subject,message);
    }

    private String customerCancellationTemplate(Restaurant restaurant, Reservation reservation){
        return "<div style=\"padding:5px; background-color: #fff4e4; \">" +
                "<p style=\"font-size: 1.9rem; color: #ff441f; font-weight:bold; font-style:italic\">Senta3</p>" +
                "</div>" +
                "<div style=\"text-align: center\">" +
                "<p style=\"font-size: 1.9rem; font-weight:bold\">La siguiente reserva fue cancelada</p>" +
                "<p style=\"font-size: 1.4rem\">Fecha: " + reservation.getReservationHour() +  "</p>" +
                "<p style=\"font-size: 1.4rem\">Restaurante: " + restaurant.getRestaurantName() + "</p>" +
                "</div>";
    }

    private void sendRestaurantCancellation(Restaurant restaurant,Customer customer,Reservation reservation,Properties properties){
        String subject="Una reserva fue cancelada";
        String message=restaurantCancellationTemplate(customer, reservation);
        sendEmail(properties, restaurant.getMail(),subject,message);
    }

    private String restaurantCancellationTemplate(Customer customer, Reservation reservation){
        return "<div style=\"padding:5px; background-color: #fff4e4; \">" +
                "<p style=\"font-size: 1.9rem; color: #ff441f; font-weight:bold; font-style:italic\">Senta3</p>" +
                "</div>" +
                "<div style=\"text-align: center\">" +
                "<p style=\"font-size: 1.9rem; font-weight:bold\">La siguiente reserva fue cancelada</p>" +
                "<p style=\"font-size: 1.4rem\">Cliente: " + customer.getCustomerName() + "</p>" +
                "<p style=\"font-size: 1.4rem\">Fecha: " + reservation.getReservationHour() +  "</p>" +
                "</div>";
    }

    public void sendEmail(Properties properties, String toEmailAddress,
                          String subject, String messageContent) {
        new Thread(() -> {

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
            ex.printStackTrace();
        }}).start();
    }


    private Properties setProperties(){
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


