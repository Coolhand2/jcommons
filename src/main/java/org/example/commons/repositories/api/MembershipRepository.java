package org.example.commons.repositories.api;

import java.util.List;
import org.example.commons.api.TypedRepository;
import org.example.commons.entities.Membership;
import org.example.commons.entities.MembershipKey;
import org.example.commons.entities.Membership_;
import org.example.commons.entities.User;

public interface MembershipRepository extends TypedRepository<Membership, MembershipKey> {
    default List<Membership> findByUser(User user) {
        return findByColumn(Membership_.user, user);
    }
}
