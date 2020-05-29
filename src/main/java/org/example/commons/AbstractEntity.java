package org.example.commons;

abstract public class AbstractEntity<T> implements Comparable<T> {

    /**
     * The lombok-generated equals method override either goes too far, or not far enough (can't figure out which) and
     * mistakenly says that objects "this" and "that" are not the same, despite "that" being a clone of "this".
     *
     * So, we're forcing an override of the equals function, so we can use the commons-lang3
     * EqualsBuilder#reflectionEquals method, which does pass this particular test.
     */

    abstract public T copy();
    abstract public int compareTo(T that);
    abstract protected boolean isEqualTo(T that);
    abstract protected int getHashCode();

    @Override
    public boolean equals(Object object) {
        return isEqualTo((T) object);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }
}
