package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class MailingServiceImpl implements MailingService{
    private final String FROMADDRESS="noreply@sentate.com";
    private final String USERNAME="sentate.paw";
    private final String PASSWORD="xblgoodfhlnunfmq";

    public void sendConfirmationEmail(Restaurant restaurant , Customer customer , Reservation reservation){
        Properties properties=setProperties();
        String subject="Confirmacion de reserva";
        String stringBuilder = "tu reserva fue confirmada\n" + "tu codigo de reserva es: "+reservation.getReservationId()+'\n' +
                "si necesitas contactar al restaurant, este es su email: " + restaurant.getMail() + '\n';
        sendEmail(properties,customer.getMail(),subject, stringBuilder);
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

    private void sendEmail(Properties properties, String toEmailAddress,
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
