package org.example.commons.entities;

import java.util.ArrayList;
import java.util.List;
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
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractEntity<UserRole> {

    private static final List<String> EXCLUDED_FIELDS = List.of("permissionsGranted");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<UserPermission> permissionsGranted = new ArrayList<>();

    @Override
    public UserRole copy() {
        return toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(UserRole that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }
}
