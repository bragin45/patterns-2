package ru.netology;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class RegistrationDto {
    String login;
    String password;
    String status;
}
