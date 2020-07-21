package org.example.commons.controllers.api;

import org.example.commons.entities.User;
import org.example.commons.services.api.UserService;

public interface UserProfileController extends SessionController {

    UserService getUserService();

    default User getUser() {
        return getSessionUser();
    }

    default void saveUser() {
        getUserService().saveUser(getUser());
    }
}