package org.example.commons.services.api;

import java.util.List;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.repositories.api.UserRepository;

public interface UserService {

    UserRepository getUserRepository();

    default List<User> searchUsers(TypedFilter<User> filter) {
        return getUserRepository().filter(filter);
    }

    default void saveUser(User user) {
        getUserRepository().update(user);
    }

    default void grantRoleToUser(UserRole role, User user) {
        user.setRole(role);
        user.getPermissions().clear();
        if(!role.getPermissionsGranted().isEmpty()) {
            user.getPermissions().addAll(role.getPermissionsGranted());
        }
        getUserRepository().update(user);
    }
}
