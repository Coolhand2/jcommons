package org.example.base.controllers.api;

import java.util.List;
import org.example.base.entities.User;
import org.example.base.entities.UserStatus;
import org.example.base.entities.filters.UserFilter;
import org.example.base.services.GroupService;
import org.example.base.services.UserService;

public interface UserAdministrationController {
    UserService getUserService();

    GroupService getGroupService();

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
        if (!getUserList().contains(user)) {
            throw new IllegalArgumentException("Cannot edit user not in the search terms!");
        }
        user.setEditing(false);
        getUserService().saveUser(user);
    }

    default void saveUserUpdates(User user) {
        if (!getUserList().contains(user)) {
            throw new IllegalArgumentException("Cannot edit user not in the search terms!");
        }
        getUserService().saveUser(user);
    }

    default void disableUser(User user) {
        if (!getUserList().contains(user)) {
            throw new IllegalArgumentException("Cannot edit user not in the search terms!");
        }
        user.setStatus(UserStatus.DISABLED);
        getGroupService().removeUserFromGroups(user);
        getUserService().saveUser(user);
    }

    default void enableUser(User user) {
        if (!getUserList().contains(user)) {
            throw new IllegalArgumentException("Cannot edit user not in the search terms!");
        }
        user.setStatus(UserStatus.ACTIVE);
        getUserService().saveUser(user);
    }

}
