package org.example.commons.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="memberships")
@IdClass(Membership.MembershipKey.class)
public class Membership extends AbstractEntity<Membership> {

    public static final Membership DEFAULT = Membership.builder().build();

    @Id
    @Builder.Default
    @ManyToOne
    private User user = User.builder().build();

    @Id
    @Builder.Default
    @ManyToOne
    private Group group = Group.builder().build();
    
    @Builder.Default
    @Enumerated
    private MembershipType type = MembershipType.READ_ONLY;

    @Builder.Default
    @Transient
    private boolean editing = false;

    @Override
    public Membership copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(Membership that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    public class MembershipKey implements Serializable {
        private Long user = 0L;
        private Long group = 0L;
    }
}
