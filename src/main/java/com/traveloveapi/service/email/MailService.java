package com.traveloveapi.service.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@ConfigurationProperties(prefix = "mail")
@Data
public class MailService {
    private String host;
    private String port;
    private String username;
    private String password;

    public void sendEmail(String address,String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(address)});

            message.setFrom(new InternetAddress(username));
            message.setSubject("Verify code");
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
