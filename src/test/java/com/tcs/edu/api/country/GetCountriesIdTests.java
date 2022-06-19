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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetCountriesIdTests {
    static final String token = getToken();
    static List<Integer> countryIds = new ArrayList<>();
    private static Connection connection;
    private static Integer countryId;
    private static String countryName;

    @BeforeAll
    public static void connect() throws SQLException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/app-db",
                "app-db-admin",
                "mysecretpassword"
        );
        countryName = getRandomUniqCountryName();
        countryId = addCountryToDb(countryName);
    }

    @AfterAll
    public static void disconnect() throws SQLException {
        deleteTestDataFromDb(countryIds);
        connection.close();
    }

    @Step("Удаление тестовых записей из БД")
    public static void deleteTestDataFromDb(List<Integer> ids) throws SQLException {
        for (int id : ids) {
            PreparedStatement sql = connection.prepareStatement("delete from country c where c.id = ?");
            sql.setInt(1, id);
            System.out.println(sql);
            sql.executeUpdate();
        }
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса без токена")
    public void shouldErrorWithoutToken() {
        given()
                .contentType("application/json")
                .pathParam("id", countryId)
                .when()
                .get("/api/countries/{id}")
                .then()
                .statusCode(401)
                .body("type", notNullValue(),
                        "title", is("Unauthorized"),
                        "status", is(401),
                        "detail", is("Full authentication is required to access this resource"),
                        "path", is("/api/countries/" + countryId),
                        "message", is("error.http.401"));
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса с некорректным токеном")
    public void shouldErrorWithIncorrectToken() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + RandomStringUtils.randomAlphanumeric(50))
                .pathParam("id", countryId)
                .when()
                .get("/api/countries/{id}")
                .then()
                .statusCode(401)
                .body("type", notNullValue(),
                        "title", is("Unauthorized"),
                        "status", is(401),
                        "detail", is("Full authentication is required to access this resource"),
                        "path", is("/api/countries/" + countryId),
                        "message", is("error.http.401"));
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса с корректным токеном и существующим id")
    public void should200WithoutParams() {
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", countryId)
                .when()
                .get("/api/countries/{id}")
                .then()
                .statusCode(200)
                .body("id", is(countryId),
                        "countryName", is(countryName),
                        "locations", is(nullValue()));
    }

    @Test
    @DisplayName("Проверка ответа при отправке запроса с несуществующим id")
    public void should200WithNonExistId() {
        Integer nonExistId = countryId + 1;
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .pathParam("id", nonExistId)
                .when()
                .get("/api/countries/{id}")
                .then()
                .statusCode(404)
                .body("type", notNullValue(),
                        "title", is("Not Found"),
                        "status", is(404),
                        "detail", is("404 NOT_FOUND"),
                        "path", is("/api/countries/" + nonExistId),
                        "message", is("error.http.404"));
    }

    @Step("Генерация уникального названия страны")
    static private String getRandomUniqCountryName() throws SQLException {
        List<String> countryNames = getCountryNamesFromDb();
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

    @Step("Получение следующего незанятого id страны списка стран")
    static private Integer getNextCountryIdFromDb() throws SQLException {
        PreparedStatement sql = connection.prepareStatement("select max(id) from country");
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) + 1;
        } else {
            return 1;
        }
    }

    @Step("Получение списка названий стран")
    static private List<String> getCountryNamesFromDb() throws SQLException {
        List<String> countryNames = new ArrayList<>();
        PreparedStatement sql = connection.prepareStatement("select * from country");
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            countryNames.add(rs.getString(2));
        }
        return countryNames;
    }

    @Step("Добавление страны в список стран")
    static private int addCountryToDb(String name) throws SQLException {
        int id = getNextCountryIdFromDb();
        PreparedStatement sql = connection.prepareStatement("insert into country (id, country_name) values (?, ?)");
        sql.setInt(1, id);
        sql.setString(2, name);
        sql.executeUpdate();
        countryIds.add(id);
        return id;
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
