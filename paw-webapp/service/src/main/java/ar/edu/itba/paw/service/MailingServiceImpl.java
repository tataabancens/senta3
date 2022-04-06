package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@Service
public class MailingServiceImpl implements MailingService{
    private final String FROMADDRESS="sentate.paw@gmail.com";
    private final String USERNAME="sentate.paw";
    private final String PASSWORD="xblgoodfhlnunfmq";

    public void sendConfirmationEmail(Restaurant restaurant , Customer customer){
        Properties properties=setProperties();
        String subject="reservation data";
        String messageText="your reservation is confirmed, if you need to contact the restaurant, this is their email: "
                +restaurant.getMail();
        sendEmail(properties,customer.getMail(),subject,messageText);
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
            System.out.println("sending mail to " + Arrays.toString(msg.getAllRecipients()));

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
