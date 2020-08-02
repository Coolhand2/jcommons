package org.example.commons.services.api;

import org.example.commons.entities.User;
import org.example.commons.utilities.api.MailUtility;

public interface MailService {

    MailUtility getMailUtility();

    default void sendUserVerificationEmail(User user) {
        getMailUtility().sendEmail("system@example.org", user.getEmail(), "Verify Registration!", "Please verify your user registration!");
    }
}
