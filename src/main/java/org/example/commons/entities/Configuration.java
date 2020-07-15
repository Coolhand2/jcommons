package org.example.commons.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.example.commons.api.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "configurations")
public class Configuration extends AbstractEntity<Configuration> {

    @GeneratedValue
    @Id
    private long id;

    private String name;

    private String value;

    @Override
    public Configuration copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(Configuration that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
