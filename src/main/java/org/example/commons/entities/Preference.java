package org.example.commons.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.example.commons.api.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preferences")
public class Preference extends AbstractEntity<Preference> {

    private static final Logger LOG = LoggerFactory.getLogger(Preference.class);

    public static final Preference DEFAULT = Preference.builder().build();

    private static final List<String> EXCLUDED_FIELDS = List.of("defaults", "options");

    @Id
    @GeneratedValue
    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String name = "";

    @Enumerated
    @Builder.Default
    private PreferenceType type = PreferenceType.STRING;

    @OneToMany
    @Builder.Default
    private List<PreferenceValue> defaults = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<PreferenceValue> options = new ArrayList<>();

    @Transient
    @Builder.Default
    private boolean editing = false;

    @Override
    public Preference copy() {
        return this.toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    protected boolean isEqualTo(Preference that) {
        return EqualsBuilder.reflectionEquals(this, that, EXCLUDED_FIELDS);
    }

    @Override
    protected String getStringRepresentation() {
        return ReflectionToStringBuilder.toStringExclude(this, EXCLUDED_FIELDS);
    }
}
