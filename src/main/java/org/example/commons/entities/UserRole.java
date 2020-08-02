package org.example.commons.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractEntity<UserRole> {

    public static final UserRole DEFAULT = new UserRole();

    private static final List<String> EXCLUDED_FIELDS = List.of("permissionsGranted");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<Permission> permissionsGranted = new ArrayList<>();

    @Override
    public UserRole copy() {
        return toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    protected boolean isEqualTo(UserRole that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }

    @Override
    protected String getStringRepresentation() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
