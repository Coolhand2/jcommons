package org.example.commons;

import java.util.List;

public interface FilterableRepository<T> {
    List<T> filter(TypedFilter<T> filter);
}
