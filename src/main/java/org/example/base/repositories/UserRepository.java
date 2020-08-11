package org.example.base.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.FilterableRepository;
import org.example.commons.ProjectedRepository;
import org.example.commons.TypedFilter;
import org.example.commons.TypedRepository;
import org.example.base.entities.Address;
import org.example.base.entities.Address_;
import org.example.base.entities.PhoneNumber;
import org.example.base.entities.PhoneNumber_;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.UserStatus;
import org.example.base.entities.UserType;
import org.example.base.entities.User_;
import org.example.base.entities.filters.UserFilter;
import org.example.base.entities.projections.UserProjection;

public interface UserRepository extends TypedRepository<User, Long>, ProjectedRepository<User, Long, UserProjection>, FilterableRepository<User> {

    @Override
    default List<SingularAttribute<User, ?>> getProjectionList() {
        return Arrays.asList(
                User_.id, User_.username, User_.email, User_.phoneNumber, User_.address, User_.type, User_.status, User_.role
        );
    }

    @Override
    default Class<UserProjection> getProjectedClass() {
        return UserProjection.class;
    }

    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    default SingularAttribute<User, Long> getId() {
        return User_.id;
    }

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

    @Override
    default List<User> filter(TypedFilter<User> f) {
        UserFilter filter = (UserFilter) f;

        if (UserFilter.DEFAULT.equals(filter)) {
            return findAll();
        }

        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        if (!filter.getId().isBlank()) {
            predicates.add(builder.like(root.get(User_.id).as(String.class), '%' + filter.getId() + '%'));
        }
        if (!filter.getUsername().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(User_.username)), '%' + filter.getUsername().toLowerCase() + '%'));
        }
        if (!filter.getEmail().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(User_.email)), '%' + filter.getEmail().toLowerCase() + '%'));
        }
        if (!filter.getPkiDn().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(User_.pkiDn)), '%' + filter.getPkiDn().toLowerCase() + '%'));
        }
        if (!filter.getVerificationKey().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get(User_.verificationKey)), '%' + filter.getVerificationKey().toLowerCase() + '%'));
        }
        if (!filter.getPhoneNumber().isBlank()) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get(User_.phoneNumber).get(PhoneNumber_.areaCode)), '%' + filter.getPhoneNumber().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.phoneNumber).get(PhoneNumber_.frontThree)), '%' + filter.getPhoneNumber().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.phoneNumber).get(PhoneNumber_.backFour)), '%' + filter.getPhoneNumber().toLowerCase() + '%')
            ));
        }
        if (!filter.getAddress().isBlank()) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get(User_.address).get(Address_.city)), '%' + filter.getAddress().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.address).get(Address_.country)), '%' + filter.getAddress().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.address).get(Address_.state)), '%' + filter.getAddress().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.address).get(Address_.street1)), '%' + filter.getAddress().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.address).get(Address_.street2)), '%' + filter.getAddress().toLowerCase() + '%'),
                    builder.like(builder.lower(root.get(User_.address).get(Address_.zipcode)), '%' + filter.getAddress().toLowerCase() + '%')
            ));
        }
        if (!filter.getType().isEmpty()) {
            predicates.add(builder.or(
                    filter.getType().stream().map(t -> builder.equal(root.get(User_.type), t)).toArray(Predicate[]::new)
            ));
        }
        if (!filter.getStatus().isEmpty()) {
            predicates.add(builder.or(
                    filter.getStatus().stream().map(st -> builder.equal(root.get(User_.status), st)).toArray(Predicate[]::new)
            ));
        }
        if (!filter.getRole().isEmpty()) {
            predicates.add(builder.or(
                    filter.getRole().stream().map(r -> builder.equal(root.get(User_.role), r)).toArray(Predicate[]::new)
            ));
        }
        query.select(root).where(builder.and(predicates.toArray(Predicate[]::new)));
        return getSession().createQuery(query).getResultList();
    }
}
