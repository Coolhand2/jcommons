package org.example.commons.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
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

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String description = "";

    @OneToMany
    @Builder.Default
    private List<Membership> memberships = new ArrayList();

    @Builder.Default
    @Transient
    private boolean editing = false;

    @Override
    public Group copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(Group that) {
        return EqualsBuilder.reflectionEquals(this, that, "memberships");
    }
}
