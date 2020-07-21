package org.example.commons.entities.filters;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.commons.api.TypedFilter;
import org.example.commons.entities.Group;

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
