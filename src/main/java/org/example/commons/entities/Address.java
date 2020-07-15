package org.example.commons.entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.example.commons.api.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address extends AbstractEntity<Address> {

    private static final Logger LOG = LoggerFactory.getLogger(Address.class);

    public static final Address DEFAULT = Address.builder().build();

    @Builder.Default
    private String street1 = "";

    @Builder.Default
    private String street2 = "";

    @Builder.Default
    private String city = "";

    @Builder.Default
    private String state = "";

    @Builder.Default
    private String country = "";

    @Builder.Default
    private String zipcode = "";

    @Builder.Default
    @Transient
    private boolean editing = false;

    @Override
    public Address copy() {
        return this.toBuilder().build();
    }

    @Override
    protected boolean isEqualTo(Address that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
