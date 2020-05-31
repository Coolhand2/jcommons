package org.example.commons.entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.example.commons.api.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder(toBuilder = true)
@Getter
@Setter
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
    public PhoneNumber copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(PhoneNumber that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
