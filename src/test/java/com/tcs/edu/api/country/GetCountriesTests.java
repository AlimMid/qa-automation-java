package com.tcs.edu.api.country;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCountriesTests {
    static final String token = getToken();
    static List<Integer> ids = new ArrayList<>();

//    @BeforeAll
//    public static void setUpAuth() {
//        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
//        authScheme.setUserName("admin");
//        authScheme.setPassword("admin");
//    }

    @BeforeAll
    public static void setUpErrorLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterAll
    public static void deleteTestData() {
        for (int id : ids) {
            given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete("/api/countries/" + id)
                    .then()
                    .statusCode(204);
        }
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса без токена")
    public void shouldErrorWithoutToken() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/countries")
                .then()
                .statusCode(401)
                .body("type", notNullValue(),
                        "title", is("Unauthorized"),
                        "status", is(401),
                        "detail", is("Full authentication is required to access this resource"),
                        "path", is("/api/countries"),
                        "message", is("error.http.401"));
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса с некорректным токеном")
    public void shouldErrorWithIncorrectToken() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + RandomStringUtils.randomAlphanumeric(50))
                .when()
                .get("/api/countries")
                .then()
                .statusCode(401)
                .body("type", notNullValue(),
                        "title", is("Unauthorized"),
                        "status", is(401),
                        "detail", is("Full authentication is required to access this resource"),
                        "path", is("/api/countries"),
                        "message", is("error.http.401"));
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса с корректным токеном без параметров")
    public void should200WithoutParams() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/countries")
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue(),
                        "[0].countryName", notNullValue(),
                        "[0].locations", anything());
    }

    @Test
    @DisplayName("Проверка, что добавленный элемент отображается в списке элементов в ответе")
    public void shouldGiveAddedElement() {
        JsonPath response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/countries")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        int countBefore = response.getList("").size();
        List<Map<String, Object>> countryList = response.getList("");
        String countryName = getRandomUniqCountryName(getCountryNames(countryList));
        addCountry(countryName);
        JsonPath responseAfterAdd = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/countries")
                .then()
                .statusCode(200)
                .body("[" + countBefore + "].countryName", is(countryName))
                .extract()
                .jsonPath();
        assertEquals(countBefore + 1, responseAfterAdd.getList("").size(), "Должна добавиться 1 страна");
    }

    @Step("Генерация уникального названия страны")
    static private String getRandomUniqCountryName(List<String> countryNames) {
        List<String> possibleNames = new ArrayList<>();
        for (char i = 'A'; i <= 'Z'; i++) {
            for (char j = 'A'; j <= 'Z'; j++) {
                if (!countryNames.contains(i + String.valueOf(j))) {
                    possibleNames.add(i + String.valueOf(j));
                }
            }
        }
        double index = Math.random() * possibleNames.size();
        return possibleNames.get((int) index);
    }

    @Step("Получение списка названий стран")
    static private List<String> getCountryNames(List<Map<String, Object>> countryList) {
        List<String> countryNames = new ArrayList<>();
        for (var country : countryList) {
            if (country.get("countryName") != null) {
                countryNames.add(country.get("countryName").toString());
            }
        }
        return countryNames;
    }

    @Step("Получение следующего незанятого id страны списка стран")
    static private Integer getNextId(List<Map<String, Object>> countryList) {
        if (countryList == null) {
            return null;
        }
        if (countryList.size() == 0) {
            return 1;
        }
        int idMax = Integer.parseInt(countryList.get(0).get("id").toString());
        for (var country : countryList) {
            if (Integer.parseInt(country.get("id").toString()) > idMax) {
                idMax = Integer.parseInt(country.get("id").toString());
            }
        }
        return idMax + 1;
    }

    @Step("Добавление страны в список стран")
    static private void addCountry(String name) {
        JsonPath response = given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "  \"countryName\": \"" + name + "\"\n" +
                        "}\n")
                .when()
                .post("/api/countries")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath();
        ids.add(response.getInt("id"));
    }

    @Step("Получение токена")
    static private String getToken() {
        JsonPath response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "  \"username\": \"admin\",\n" +
                                "  \"password\": \"admin\",\n" +
                                "  \"rememberMe\": true\n" +
                                "}\n")
                        .when()
                        .post("/api/authenticate")
                        .then()
                        .statusCode(200)
                        .body(
                                "id_token", Matchers.notNullValue()
                        )
                        .extract()
                        .jsonPath();
        return response.getString("id_token");
    }
}
