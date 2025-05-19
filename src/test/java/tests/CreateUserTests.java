package tests;


import models.CreateNewUserRequestModel;
import models.CreateNewUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.CreateNewUserSpec.createNewUserRequestSpec;
import static specs.CreateNewUserSpec.createNewUserResponseSpec;


@DisplayName("Проверка метода создания пользователя")
public class CreateUserTests extends TestBase {
    @Test
    @DisplayName("Успешное создание пользователя")
    void createNewUserSuccessfulTest() {
        CreateNewUserRequestModel createData = new CreateNewUserRequestModel("Ilya", "tester", null);

        CreateNewUserResponseModel response = step("Делаем запрос", () ->
                given(createNewUserRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createNewUserResponseSpec(201))
                        .extract().as(CreateNewUserResponseModel.class));

        step("Проверяем данные id", () ->
                assertNotNull(response.getId()));
        step("Проверяем данные name", () ->
                assertEquals("Ilya", response.getName()));
        step("Проверяем данные job", () ->
                assertEquals("tester", response.getJob()));
        step("Проверяем формат createdAt", () -> {
            boolean ok = Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z", response.getCreatedAt());
            assertTrue(ok, () -> "createdAt имеет некорректный формат: " + response.getCreatedAt());
        });
    }


    @Test
    @DisplayName("Успешное создание пользователя без имени")
    void createNewUserWithoutNameTest() {
        CreateNewUserRequestModel createData = new CreateNewUserRequestModel(null, "tester",null);

        CreateNewUserResponseModel response = step("Делаем запрос", () ->
                given(createNewUserRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createNewUserResponseSpec(201))
                        .extract().as(CreateNewUserResponseModel.class));

        step("Проверяем данные id", () ->
                assertNotNull(response.getId()));
        step("Проверяем данные job", () ->
                assertEquals("tester", response.getJob()));
        step("Проверяем формат createdAt", () -> {
            boolean ok = Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z", response.getCreatedAt());
            assertTrue(ok, () -> "createdAt имеет некорректный формат: " + response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Успешное создание пользователя без профессии")
    void createNewUserWithoutJobTest() {
        CreateNewUserRequestModel createData = new CreateNewUserRequestModel("Ilya", null, null);

        CreateNewUserResponseModel response = step("Делаем запрос", () ->
                given(createNewUserRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createNewUserResponseSpec(201))
                        .extract().as(CreateNewUserResponseModel.class));

        step("Проверяем данные id", () ->
                assertNotNull(response.getId()));
        step("Проверяем данные name", () ->
                assertEquals("Ilya", response.getName()));
        step("Проверяем формат createdAt", () -> {
            boolean ok = Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z", response.getCreatedAt());
            assertTrue(ok, () -> "createdAt имеет некорректный формат: " + response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Успешное создание пользователя без данных")
    void createNewUserWithEmptyDataTest() {

        CreateNewUserResponseModel response = step("Делаем запрос", () ->
                given(createNewUserRequestSpec)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createNewUserResponseSpec(201))
                        .extract().as(CreateNewUserResponseModel.class));

        step("Проверяем данные id", () ->
                assertNotNull(response.getId()));
        step("Проверяем формат createdAt", () -> {
            boolean ok = Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z", response.getCreatedAt());
            assertTrue(ok, () -> "createdAt имеет некорректный формат: " + response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Отправка запроса с недекларированым параметром")
    void createNewUserWithWrongDataTypesTest() {
        CreateNewUserRequestModel createData = new CreateNewUserRequestModel("Ilya", "tester", "Test string");

        CreateNewUserResponseModel response = step("Делаем запрос", () ->
                given(createNewUserRequestSpec)
                        .body(createData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createNewUserResponseSpec(201))
                        .extract().as(CreateNewUserResponseModel.class));

        step("Проверяем данные id", () ->
                assertNotNull(response.getId()));
        step("Проверяем данные недекларированного поля test", () ->
                assertEquals("Test string", response.getTest()));
        step("Проверяем формат createdAt", () -> {
            boolean ok = Pattern.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z", response.getCreatedAt());
            assertTrue(ok, () -> "createdAt имеет некорректный формат: " + response.getCreatedAt());
        });
    }
}
