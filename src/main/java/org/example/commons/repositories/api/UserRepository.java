package org.example.commons.repositories.api;

import java.util.List;
import org.example.commons.api.FilteredRepository;
import org.example.commons.api.ProjectedRepository;
import org.example.commons.api.TypedRepository;
import org.example.commons.entities.Address;
import org.example.commons.entities.PhoneNumber;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.UserType;
import org.example.commons.entities.User_;
import org.example.commons.entities.dtos.UserDataTransfer;

public interface UserRepository extends TypedRepository<User, Long>, ProjectedRepository<User, Long, UserDataTransfer>, FilteredRepository<User> {
    default User findByUsername(String name) {
        return findOneByColumn(User_.username, name);
    }

    default User findByEmail(String email) {
        return findOneByColumn(User_.email, email);
    }

    default User findByPkiDn(String pkiDn) {
        return findOneByColumn(User_.pkiDn, pkiDn);
    }

    default User findByVerificationKey(String verificationKey) {
        return findOneByColumn(User_.verificationKey, verificationKey);
    }

    default User findByPhoneNumber(PhoneNumber phoneNumber) {
        return findOneByColumn(User_.phoneNumber, phoneNumber);
    }

    default User findByAddress(Address address) {
        return findOneByColumn(User_.address, address);
    }

    default List<User> findByType(UserType type) {
        return findByColumn(User_.type, type);
    }

    default List<User> findByStatus(UserStatus status) {
        return findByColumn(User_.status, status);
    }

    default List<User> findByRole(UserRole role) {
        return findByColumn(User_.role, role);
    }
}
