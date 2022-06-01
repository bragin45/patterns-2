package ru.netology;

import lombok.*;

@Value

public class RegistrationDto {
    String login;
    String password;
    String status;
}
