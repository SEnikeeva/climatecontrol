package ru.itis.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.State;
import ru.itis.model.User;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserConfirmDto {

    private String login;
    private String email;
    private String confirmCode;
    private State state;
    private int id;
    public static UserConfirmDto from(User user) {
        return UserConfirmDto.builder().id(user.getId())
                .confirmCode(user.getConfirmCode())
                .state(user.getState())
                .email(user.getEmail())
                .build();
    }
}
