package org.example.commons.entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.example.commons.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder(toBuilder=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PhoneNumber extends AbstractEntity<PhoneNumber> {

    private static final Logger LOG = LoggerFactory.getLogger(PhoneNumber.class);

    public static final PhoneNumber DEFAULT = PhoneNumber.builder().build();

    @Builder.Default
    private String areaCode = "";

    @Builder.Default
    private String frontThree = "";

    @Builder.Default
    private String backFour = "";

    @Builder.Default
    @Transient
    private boolean editing = false;

    @Override
    public PhoneNumber clone() {
        return this.toBuilder().build();
    }

    @Override
    public int compareTo(PhoneNumber that) {
        return CompareToBuilder.reflectionCompare(this, that);
    }


    @Override
    protected boolean isEqualTo(PhoneNumber that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    protected int getHashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
