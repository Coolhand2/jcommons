package org.example.base.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commons.AbstractEntity;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "help_groupings")
public class HelpGrouping extends AbstractEntity<HelpGrouping> {

    @GeneratedValue
    @Id
    @Builder.Default
    private long id = 0L;

    @Builder.Default
    private String name = "";

    @Override
    public HelpGrouping copy() {
        return null;
    }

    @Override
    protected int getHashCode() {
        return 0;
    }

    @Override
    protected boolean isEqualTo(HelpGrouping that) {
        return false;
    }

    @Override
    protected String getStringRepresentation() {
        return null;
    }
}
