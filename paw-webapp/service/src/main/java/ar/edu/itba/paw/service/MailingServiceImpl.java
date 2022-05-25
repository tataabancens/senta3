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
        String stringBuilder = "tu reserva fue confirmada\n" + "tu codigo de reserva es: "+reservation.getId()+'\n' +
                "si necesitas contactar al restaurant, este es su email: " + restaurant.getMail() + '\n';
        sendEmail(props,customer.getMail(),subject, stringBuilder);
    }

    private void sendRestaurantConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties properties){
        String subject="Un cliente realizo una reserva";
        String message="El cliente: "+customer.getCustomerName()+" realizo una reserva para las: "+
                reservation.getReservationHour() +'\n';
        sendEmail(properties,restaurant.getMail(),subject,message);
    }

    @Override
    public void sendCancellationEmail(Restaurant restaurant,Customer customer,Reservation reservation){
        Properties properties=setProperties();
        sendCustomerCancellation(restaurant,customer,reservation,properties);
        sendRestaurantCancellation(restaurant,customer,reservation,properties);
    }

    private void sendCustomerCancellation(Restaurant restaurant,Customer customer,Reservation reservation, Properties properties){
        String subject="Se cancelo tu reserva";
        String message="La siguiente reserva fue cancelada:\n"+"fecha: "+reservation.getReservationHour()+'\n'
                +"restaurante: "+restaurant.getRestaurantName()+'\n';
        sendEmail(properties,customer.getMail(),subject,message);
    }

    private void sendRestaurantCancellation(Restaurant restaurant,Customer customer,Reservation reservation,Properties properties){
        String subject="Una reserva fue cancelada";
        String message="La siguiente reserva fue cancelada:\n"
                +"cliente: "+customer.getCustomerName()+'\n'
                +"fecha: "+reservation.getReservationHour() +'\n';
        sendEmail(properties, restaurant.getMail(),subject,message);
    }

    public void sendEmail(Properties properties, String toEmailAddress,
                          String subject, String messageText) {
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
            msg.setText(messageText);
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
