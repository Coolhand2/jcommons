package org.example.commons.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.Address;
import org.example.commons.entities.Address_;
import org.example.commons.entities.PhoneNumber;
import org.example.commons.entities.PhoneNumber_;
import org.example.commons.entities.User;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.UserType;
import org.example.commons.entities.User_;
import org.example.commons.entities.dtos.UserDataTransfer;
import org.example.commons.entities.filters.UserFilter;
import org.example.commons.repositories.api.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Inject
    private EntityManager em;

    @Override
    public List<SingularAttribute<User, ?>> getProjectionList() {
        return Arrays.asList(
                User_.id, User_.username, User_.email, User_.phoneNumber, User_.address, User_.type, User_.status
        );
    }

    @Override
    public Class<UserDataTransfer> getProjectedClass() {
        return UserDataTransfer.class;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public List<User> filter(final TypedFilter<User> f) {
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
            filter.getType().forEach(t -> predicates.add(builder.equal(root.get(User_.type), t)));
        }
        if (!filter.getStatus().isEmpty()) {
            filter.getStatus().forEach(s -> predicates.add(builder.equal(root.get(User_.status), s)));
        }
        query.select(root);
        if (predicates.size() == 1) {
            query.where(predicates.get(0));
        } else {
            query.where(builder.and(
                    predicates.toArray(new Predicate[0])
            ));
        }
        return getSession().createQuery(query).getResultList();
    }
}
