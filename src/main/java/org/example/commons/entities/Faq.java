package org.example.commons.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
import org.apache.commons.lang3.builder.ToStringStyle;
import org.example.commons.api.AbstractEntity;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "faqs")
public class Faq extends AbstractEntity<Faq> {

    @GeneratedValue
    @Id
    @Builder.Default
    private long id = 0L;

    @ManyToOne
    @Builder.Default
    private FaqGrouping grouping = FaqGrouping.builder().build();

    @Builder.Default
    private String name = "";

    @Lob
    @Builder.Default
    private String value = "";

    @Override
    public Faq copy() {
        return toBuilder().build();
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    protected boolean isEqualTo(Faq that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    protected String getStringRepresentation() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
