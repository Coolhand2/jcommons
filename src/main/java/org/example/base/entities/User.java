package org.example.base.entities;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.commons.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User extends AbstractEntity<User> implements Comparable<User> {

    public static final long serialVersionUID = 1L;

    public static final User DEFAULT = User.builder().build();

    public static final Comparator<User> defaultSort = Comparator.comparing(User::getUsername);

    @Inject
    private static Logger LOG;

    private static final List<String> EXCLUDED_FIELDS = List.of("preferences", "permissions");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String username = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String pkiDn = "";

    @Builder.Default
    private String verificationKey = "";

    @Builder.Default
    private PhoneNumber phoneNumber = new PhoneNumber();

    @Builder.Default
    private Address address = new Address();

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<PreferenceValue> preferences = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Membership> memberships = new HashSet<>();

    @Enumerated
    @Builder.Default
    private UserType type = UserType.GUEST;

    @Enumerated
    @Builder.Default
    private UserStatus status = UserStatus.UNVERIFIED;

    @ManyToOne
    @Builder.Default
    private UserRole role = new UserRole();

    @Enumerated
    @ElementCollection
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    @Transient
    @Builder.Default
    private boolean editing = false;

    @Override
    public User copy() {
        return this.toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    protected boolean isEqualTo(User that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }

    @Override
    protected String getStringRepresentation() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int compareTo(User that) {
        return defaultSort.compare(this, that);
    }

    public boolean is(UserType type) {
        return type == this.type;
    }

    public boolean is(UserStatus status) {
        return status == this.status;
    }

    public boolean is(UserRole role) {
        return role == this.role;
    }

    public boolean can(Permission permission) {
        return this.permissions.contains(permission);
    }
}
