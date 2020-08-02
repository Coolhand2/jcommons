package org.example.commons.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groups")
public class Group extends AbstractEntity<Group> {

    public static final Group DEFAULT = Group.builder().build();

    private static final List<String> EXCLUDED_FIELDS = List.of("memberships");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String description = "";

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MembershipRequest> membershipRequests = new HashSet<>();

    @Builder.Default
    @Transient
    private boolean editing = false;

    @Override
    public Group copy() {
        return this.toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    protected boolean isEqualTo(Group that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }

    @Override
    protected String getStringRepresentation() {
        return ReflectionToStringBuilder.toStringExclude(this, EXCLUDED_FIELDS);
    }

    public void addMember(User user) {
        Membership membership = Membership.builder().group(this).user(user).build();
        this.memberships.add(membership);
    }
}
