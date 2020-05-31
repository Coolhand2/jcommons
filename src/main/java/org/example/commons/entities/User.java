package org.example.commons.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.commons.api.AbstractEntity;
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
    private PhoneNumber phoneNumber = PhoneNumber.DEFAULT.copy();

    @Builder.Default
    private Address address = Address.DEFAULT.copy();

    @ManyToMany
    @Builder.Default
    private List<PreferenceValue> preferences = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserType type = UserType.MEMBER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.UNVERIFIED;

    @Transient
    @Builder.Default
    private boolean editing = false;

    @Override
    public User copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(User that) {
        return EqualsBuilder.reflectionEquals(this, that, "preferences");
    }

    @Override
    public int compareTo(User that) {
        return defaultSort.compare(this, that);
    }
}
