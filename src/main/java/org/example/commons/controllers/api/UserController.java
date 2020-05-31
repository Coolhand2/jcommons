package org.example.commons.controllers.api;

import java.util.List;
import java.util.concurrent.ExecutorService;
import org.example.commons.entities.User;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.services.api.UserService;

public interface UserController {
    UserService getUserService();

    ExecutorService getExecutorService();

    List<User> getUserList();

    void setUserList(List<User> userList);

    boolean getSearchingFlag();

    void setSearchingFlag(boolean flag);

    boolean getSavingFlag();

    void setSavingFlag(boolean flag);

    UserFilter getUserFilter();

    default void fetchUserList() {
        setSearchingFlag(true);
        getExecutorService().submit(() -> {
            setUserList(getUserService().searchUsers(getUserFilter()));
            setSearchingFlag(false);
        });
    }

    default void beginEditingUser(User user) {
        user.setEditing(true);
    }

    default void finishEditingUser(User user) {
        user.setEditing(false);
        setSavingFlag(true);
        getExecutorService().submit(() -> {
            getUserService().saveUser(user);
            setSavingFlag(false);
        });
    }

    default void saveUserUpdates(User user) {
        setSavingFlag(true);
        getExecutorService().submit(() -> {
            getUserService().saveUser(user);
            setSavingFlag(false);
        });
    }

    default void disableUser(User user) {
        user.setStatus(UserStatus.DISABLED);
        setSavingFlag(true);
        getExecutorService().submit(() -> {
            getUserService().saveUser(user);
            setSavingFlag(false);
        });
    }

    default void enableUser(User user) {
        user.setStatus(UserStatus.ACTIVE);
        setSavingFlag(true);
        getExecutorService().submit(() -> {
            getUserService().saveUser(user);
            setSavingFlag(false);
        });
    }

}
