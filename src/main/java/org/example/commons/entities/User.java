package org.example.commons.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
import org.example.commons.api.AbstractEntity;
import org.hibernate.annotations.ManyToAny;
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

    public static Comparator<User> defaultSort = Comparator.comparing(User::getUsername);

    private static final Logger LOG = LoggerFactory.getLogger(User.class);

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
    @JoinColumn(name = "user_id", nullable = true)
    @Builder.Default
    private Set<PreferenceValue> preferences = new HashSet<>();

    @Enumerated
    @Builder.Default
    private UserType type = UserType.GUEST;

    @Enumerated
    @Builder.Default
    private UserStatus status = UserStatus.UNVERIFIED;

    @Builder.Default
    @ManyToOne
    private UserRole role = new UserRole();

    @ElementCollection
    @Enumerated
    @Builder.Default
    private Set<UserPermission> permissions = new HashSet<>();

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

    public boolean isGuest() {
        return UserType.GUEST == type;
    }

    public boolean isMember() {
        return UserType.MEMBER == type;
    }

    public boolean isUnverified() {
        return UserStatus.UNVERIFIED == status;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE == status;
    }

    public boolean isDisabled() {
        return UserStatus.DISABLED == status;
    }

    public boolean canEditUser() {
        return permissions.contains(UserPermission.EDIT_USER);
    }

    public boolean canEditGroup() {
        return permissions.contains(UserPermission.EDIT_GROUP);
    }

    public boolean canEditRoles() {
        return permissions.contains(UserPermission.EDIT_ROLES);
    }

    public boolean canEditConfiguration() {
        return permissions.contains(UserPermission.EDIT_CONFIGURATION);
    }
}
