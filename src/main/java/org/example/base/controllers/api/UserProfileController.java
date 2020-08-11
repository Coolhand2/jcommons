package org.example.base.controllers.api;

import org.example.base.entities.User;
import org.example.base.services.UserService;

public interface UserProfileController extends SessionController {

    UserService getUserService();

    default User getUser() {
        return getSessionUser();
    }

    default void saveUser() {
        getUserService().saveUser(getUser());
    }
}