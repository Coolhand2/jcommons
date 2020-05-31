package org.example.commons.entities.dtos;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.commons.entities.Address;
import org.example.commons.entities.PhoneNumber;
import org.example.commons.entities.User;
import org.example.commons.entities.UserStatus;
import org.example.commons.entities.UserType;

@Builder
@Data
@AllArgsConstructor
public class UserDataTransfer {

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

    public static UserDataTransfer from(User u) {
        return builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .phoneNumber(u.getPhoneNumber())
                .address(u.getAddress())
                .type(u.getType())
                .status(u.getStatus())
                .build();
    }

    public static Comparator<UserDataTransfer> defaultSort = Comparator.comparing(UserDataTransfer::getUsername);

}
