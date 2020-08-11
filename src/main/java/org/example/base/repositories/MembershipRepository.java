package org.example.base.repositories;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import org.example.base.entities.MembershipKey_;
import org.example.commons.TypedRepository;
import org.example.base.entities.Group;
import org.example.base.entities.Membership;
import org.example.base.entities.MembershipKey;
import org.example.base.entities.GroupRole;
import org.example.base.entities.Membership_;
import org.example.base.entities.User;

public interface MembershipRepository extends TypedRepository<Membership, MembershipKey> {

    @Override
    default SingularAttribute<Membership, MembershipKey> getId() {
        return Membership_.id;
    }

    @Override
    default Class<Membership> getEntityClass() {
        return Membership.class;
    }

    default List<Membership> findByGroup(Group group) {
        return findByColumn((root) -> root.get(Membership_.id).get(MembershipKey_.group), group);
    }

    default List<Membership> findByUser(User user) {
        return findByColumn((root) -> root.get(Membership_.id).get(MembershipKey_.user), user);
    }

    default Membership findByUserAndGroup(User user, Group group) {
        return findOneBy((root, builder) -> new Predicate[]{
                builder.equal(root.get(Membership_.id).get(MembershipKey_.group), group),
                builder.equal(root.get(Membership_.id).get(MembershipKey_.user), user)
        });
    }
}
