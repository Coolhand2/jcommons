package org.example.commons.api;

import java.util.List;

public interface FilteredRepository<T> {
    List<T> filter(TypedFilter<T> filter);
}
