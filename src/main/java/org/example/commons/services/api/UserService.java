package org.example.commons.services.api;

import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.UserType;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.UserRepository;
import org.example.commons.utilities.api.SecurityUtility;

import java.util.List;

public interface UserService {

    UserRepository getUserRepository();

    SecurityUtility getSecurityUtility();

    MailService getMailService();

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
        if(!user.is(UserStatus.UNVERIFIED)) {
            return;
        }
        User verifiedUser = getUserRepository().findByVerificationKey(verificationKey);
        if(!verifiedUser.getPkiDn().equals(user.getPkiDn())) {
            return;
        }
        verifiedUser.setType(UserType.MEMBER);
        verifiedUser.setStatus(UserStatus.ACTIVE);
        getUserRepository().update(verifiedUser);
    }

    default void registerUser(User newUser) {
        String key = getSecurityUtility().generateKey();
        newUser.setVerificationKey(key);
        getUserRepository().create(newUser);
        getMailService().sendUserVerificationEmail(newUser);
    }

    default User getUserById(Long id) {
        return getUserRepository().findById(id);
    }

    default void disableUser(User user) {
        user.setStatus(UserStatus.DISABLED);
        user.getPermissions().clear();
        getUserRepository().update(user);
    }

    default void enableUser(User user) {
        user.setStatus(UserStatus.UNVERIFIED);
        String key = getSecurityUtility().generateKey();
        user.setVerificationKey(key);
        getUserRepository().update(user);
        getMailService().sendUserVerificationEmail(user);
    }
}
