package org.example.commons.controllers.api;

import java.util.List;
import java.util.concurrent.ExecutorService;
import org.example.commons.entities.User;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.services.api.UserService;

public interface UserController {
    UserService getUserService();

    List<User> getUserList();

    void setUserList(List<User> userList);

    UserFilter getUserFilter();

    default void fetchUserList() {
        setUserList(getUserService().searchUsers(getUserFilter()));
    }

    default void beginEditingUser(User user) {
        if (!getUserList().contains(user)) {
            throw new IllegalArgumentException("Cannot edit user not in the search terms!");
        }
        user.setEditing(true);
    }

    default void finishEditingUser(User user) {
        user.setEditing(false);
        getUserService().saveUser(user);
    }

    default void saveUserUpdates(User user) {
        getUserService().saveUser(user);
    }

    default void disableUser(User user) {
        user.setStatus(UserStatus.DISABLED);
        getUserService().saveUser(user);
    }

    default void enableUser(User user) {
        user.setStatus(UserStatus.ACTIVE);
        getUserService().saveUser(user);
    }

}
