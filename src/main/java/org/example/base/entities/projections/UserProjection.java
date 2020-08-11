package org.example.base.entities.projections;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.base.entities.Address;
import org.example.base.entities.PhoneNumber;
import org.example.base.entities.User;
import org.example.base.entities.UserRole;
import org.example.base.entities.UserStatus;
import org.example.base.entities.UserType;

@Builder
@Data
@AllArgsConstructor
public class UserProjection {

    @Builder.Default
    private Long id = 0L;

    @Builder.Default
    private String username = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private PhoneNumber phoneNumber = PhoneNumber.DEFAULT.copy();

    @Builder.Default
    private Address address = Address.DEFAULT.copy();

    @Builder.Default
    private UserType type = UserType.MEMBER;

    @Builder.Default
    private UserStatus status = UserStatus.UNVERIFIED;

    @Builder.Default
    private UserRole role = UserRole.DEFAULT.copy();

    public static UserProjection from(User u) {
        return builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .phoneNumber(u.getPhoneNumber())
                .address(u.getAddress())
                .type(u.getType())
                .status(u.getStatus())
                .role(u.getRole())
                .build();
    }

    public static Comparator<UserProjection> defaultSort = Comparator.comparing(UserProjection::getUsername);

}
