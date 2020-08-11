package org.example.base.repositories;

import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.UserRole_;
import org.example.commons.TypedRepository;
import org.example.base.entities.UserRole;

public interface UserRoleRepository extends TypedRepository<UserRole, Long> {

    @Override
    default Class<UserRole> getEntityClass() {
        return UserRole.class;
    }

    @Override
    default SingularAttribute<UserRole, Long> getId() {
        return UserRole_.id;
    }

}
