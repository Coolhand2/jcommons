package org.example.commons.entities;

import javax.persistence.Entity;
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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "faq_groupings")
public class FaqGrouping extends AbstractEntity<FaqGrouping> {
    @GeneratedValue
    @Id
    @Builder.Default
    private long id = 0L;

    @Builder.Default
    private String name = "";

    @Override
    public FaqGrouping copy() {
        return toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    protected boolean isEqualTo(FaqGrouping that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    protected String getStringRepresentation() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
