package org.example.base.entities.filters;

import lombok.Builder;
import lombok.Data;
import org.example.commons.TypedFilter;
import org.example.base.entities.Group;

@Builder
@Data
public class GroupFilter implements TypedFilter<Group> {

    public static final GroupFilter DEFAULT = GroupFilter.builder().build();

    @Builder.Default
    private String id = "";

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String description = "";
}
