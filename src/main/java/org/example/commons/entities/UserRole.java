package org.example.commons.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractEntity<UserRole> {

    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private List<UserPermission> permissionsGranted = new ArrayList<>();

    @Override
    public UserRole copy() {
        return toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(UserRole that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
