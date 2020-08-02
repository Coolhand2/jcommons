package org.example.commons.entities.projections;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.commons.entities.Address;
import org.example.commons.entities.PhoneNumber;
import org.example.commons.entities.User;
import org.example.commons.entities.UserRole;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.UserType;

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
