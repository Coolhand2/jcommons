package org.example.commons.services.api;

import java.util.List;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.ConfigurationRepository;
import org.example.commons.repositories.api.UserRepository;
import org.example.commons.utilities.api.SecurityUtility;
import org.example.commons.utilities.api.MailUtility;

public interface UserService {

    UserRepository getUserRepository();

    SecurityUtility getSecurityUtility();

    MailUtility getMailUtility();

    default List<User> searchUsers(UserFilter filter) {
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

    default void verifyUser(User user, String verificationKey) {
        if(!user.isUnverified()) {
            return;
        }
        User verifiedUser = getUserRepository().findByVerificationKey(verificationKey);
        if(!verifiedUser.getPkiDn().equals(user.getPkiDn())) {
            return;
        }
        verifiedUser.setStatus(UserStatus.ACTIVE);
        getUserRepository().update(verifiedUser);
    }

    default void registerUser(User newUser) {
        String key = getSecurityUtility().generateKey();
        newUser.setVerificationKey(key);
        getUserRepository().create(newUser);
        getMailUtility().sendEmail("system@example.org", newUser.getEmail(), "Verify Registration!", "Please verify your user registration!");
    }
}
