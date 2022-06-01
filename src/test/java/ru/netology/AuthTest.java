package ru.netology;

import org.junit.jupiter.api.BeforeEach;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        DataGenerator.RegistrationDto user = DataGenerator.Registration.getRegisteredUser("active");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(), 'Личный кабинет')]")
                .should(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        DataGenerator.RegistrationDto user = DataGenerator.Registration.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']").should(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        DataGenerator.RegistrationDto user = DataGenerator.Registration.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(DataGenerator.getRandomLogin());
        $x("//input[@type='password']").val(user.getPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        DataGenerator.RegistrationDto user = DataGenerator.Registration.getRegisteredUser("blocked");
        $x("//input[@type='text']").val(user.getLogin());
        $x("//input[@type='password']").val(DataGenerator.getRandomPassword());
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10));
    }
}
