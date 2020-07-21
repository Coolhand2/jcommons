package org.example.commons.controllers.api;

import javax.faces.context.FacesContext;
import org.example.commons.entities.User;

public interface SessionController {

    String USER_SESSION_KEY = "USER";

    default User getSessionUser() {
        return (User) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .getOrDefault(USER_SESSION_KEY, User.builder().build());
    }
}
