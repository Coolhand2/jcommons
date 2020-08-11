package org.example.base.services;

import org.example.base.entities.User;
import org.example.commons.utilities.MailUtility;

public interface MailService {

    MailUtility getMailUtility();

    default void sendUserVerificationEmail(User user) {
        getMailUtility().sendEmail("system@example.org", user.getEmail(), "Verify Registration!", "Please verify your user registration!");
    }
}
