package org.example.commons.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "membership_roles")
public class MembershipRole extends AbstractEntity<MembershipRole> {

    private static final List<String> EXCLUDED_FIELDS = List.of("permissions");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @ElementCollection
    @Enumerated
    @Builder.Default
    private Set<MembershipPermission> permissions = new HashSet<>();

    @Override
    public MembershipRole copy() {
        return toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    protected boolean isEqualTo(MembershipRole that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }

    @Override
    protected String getStringRepresentation() {
        return ReflectionToStringBuilder.toStringExclude(this, EXCLUDED_FIELDS);
    }
}
