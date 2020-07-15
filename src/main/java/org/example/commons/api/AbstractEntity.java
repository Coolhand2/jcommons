package org.example.commons.api;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

abstract public class AbstractEntity<T> {

    /**
     * The lombok-generated equals method override either goes too far, or not far enough (can't figure out which) and
     * mistakenly says that objects "this" and "that" are not the same, despite "that" being a clone of "this".
     *
     * So, we're forcing an override of the equals function, so we can use the commons-lang3
     * EqualsBuilder#reflectionEquals method, which does pass this particular test.
     */

    abstract public T copy();
    abstract protected boolean isEqualTo(T that);

    @Override
    public boolean equals(Object object) {
        return isEqualTo((T) object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
