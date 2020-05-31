package org.example.commons.entities;

import javax.persistence.Entity;
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
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preference_values")
public class PreferenceValue extends AbstractEntity<PreferenceValue> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Preference preference;

    private String value;

    @Override
    public PreferenceValue copy() {
        return toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(PreferenceValue that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
