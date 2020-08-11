package org.example.base.entities.filters;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.example.commons.TypedFilter;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.UserStatus;
import org.example.base.entities.UserType;

@Builder
@Data
public class UserFilter implements TypedFilter<User> {

    public static final UserFilter DEFAULT = UserFilter.builder().build();

    @Builder.Default
    private String id = "";

    @Builder.Default
    private String username = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String pkiDn = "";

    @Builder.Default
    private String verificationKey = "";

    @Builder.Default
    private String phoneNumber = "";

    @Builder.Default
    private String address = "";

    @Builder.Default
    private List<UserType> type = new ArrayList<>();

    @Builder.Default
    private List<UserStatus> status = new ArrayList<>();

    @Builder.Default
    private List<UserRole> role = new ArrayList<>();
}
