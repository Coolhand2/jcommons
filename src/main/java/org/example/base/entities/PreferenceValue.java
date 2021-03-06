package org.example.base.entities;

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
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.commons.AbstractEntity;

@Builder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preference_values")
public class PreferenceValue extends AbstractEntity<PreferenceValue> {

    public static final long serialVersionUID = 1L;

    public static final PreferenceValue DEFAULT = PreferenceValue.builder().build();

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
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    protected boolean isEqualTo(PreferenceValue that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    protected String getStringRepresentation() {
        return ToStringBuilder.reflectionToString(this);
    }
}
