package org.example.commons.utilities.api;

import org.example.commons.repositories.api.ConfigurationRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Set;

public interface MailUtility {

    ConfigurationRepository getConfigurationRepository();

    Properties PROPERTIES = System.getProperties();

    default String getMailHost() {
        return getConfigurationRepository().findByName("mail.smtp.host").getValue();
    }

    default Properties getProperties() {
        if(!PROPERTIES.contains("mail.smtp.host")) {
            PROPERTIES.put("mail.smtp.host", getMailHost());
        }
        return PROPERTIES;
    }

    default Session getMailSession() {
        return Session.getInstance(getProperties(), null);
    }

    default void sendEmail(String from, String to, String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(getMailSession());
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress("system@example.org", "NoReply-System"));
            message.setReplyTo(InternetAddress.parse("no_reply@example.org", false));
            message.setSubject(subject);
            message.setText(text, "UTF-8");
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to, false));
            Transport.send(message);
        } catch (Exception ex) {
        }
    }

    default void sendEmail(String from, Set<String> to, String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(getMailSession());
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.setFrom(new InternetAddress(from, ""));
            message.setReplyTo(InternetAddress.parse(from, false));
            message.setSubject(subject);
            message.setText(text, "UTF-8");
            to.forEach(t -> {
                try {
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(t, false));
                    Transport.send(message);
                } catch (Exception e) {;
                }
            });

        } catch (Exception ex) {
        }
    }
}
