package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
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
        String stringBuilder = "tu reserva fue confirmada\n" + "tu codigo de reserva es: "+reservation.getReservationId()+'\n' +
                "si necesitas contactar al restaurant, este es su email: " + restaurant.getMail() + '\n';
        sendEmail(props,customer.getMail(),subject, stringBuilder);
    }

    private void sendRestaurantConfirmation(Restaurant restaurant , Customer customer , Reservation reservation, Properties properties){
        String subject="Un cliente realizo una reserva";
        String message="El cliente: "+customer.getCustomerName()+"(id: "+customer.getCustomerId()+") realizo una reserva para el: "+
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

    @Override
    public void sendReceiptEmail(Restaurant restaurant, Customer customer) {
        Properties properties=setProperties();
        String subject="Un cliente quiere pedir la cuenta";
        String messageText="el cliente: "+customer.getCustomerName()+"(id: "+customer.getCustomerId()+
                ") quiere la cuenta";
        sendEmail(properties,restaurant.getMail(),subject,messageText);
    }

    @Override
    public void sendOrderEmail(Restaurant restaurant, Customer customer, List<FullOrderItem> orderItems) {
        Properties properties=setProperties();
        String subject="Un cliente hizo un pedido";
        StringBuilder stringBuilder=
                new StringBuilder("El cliente: "+customer.getCustomerName()+"(id: "+customer.getCustomerId()+") pidio estos items:\n");
        for(FullOrderItem item : orderItems){
            stringBuilder.append(item.getDishName()).append(" x ").append(item.getQuantity()).append('\n');
        }
        sendEmail(properties,restaurant.getMail(),subject,stringBuilder.toString());
    }

    @Async
    @Override
    public void sendEmail(Properties properties, String toEmailAddress,
                          String subject, String messageText) {

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
        }
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
