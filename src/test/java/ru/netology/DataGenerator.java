package ru.netology;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }
    public static class Registration {
        private Registration() {
        }
        private static final Faker faker = new Faker(new Locale("en"));

        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        public static void sendRequest(RegistrationDto user) {
            given()
                    .spec(requestSpec)
                    .body(new Gson().toJson(user))
                    .when()
                    .post("/api/system/users")
                    .then() //
                    .statusCode(200);
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    public static String getRandomLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        return password;
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}
