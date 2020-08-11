package org.example.base.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.commons.AbstractEntity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership extends AbstractEntity<Membership> {

    public static final Membership DEFAULT = Membership.builder().build();

    @EmbeddedId
    private MembershipKey id;

    @Enumerated
    @ElementCollection
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    @Builder.Default
    private LocalDate joined = LocalDate.now();

    @Transient
    @Builder.Default
    private boolean editing = false;

    @Override
    public Membership copy() {
        return this.toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    protected boolean isEqualTo(Membership that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    protected String getStringRepresentation() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static BiPredicate<Membership, Permission> has = (membership, permission) -> membership.permissions.contains(permission);

    public boolean has(Permission permission) {
        return Membership.has.test(this, permission);
    }

}

