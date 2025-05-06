import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Проверка метода создания пользователя")
public class CreateUserTests extends TestBase {
    @Test
    @DisplayName("Успешное создание пользователя")
    void createNewUserSuccessfulTest() {
        String bodyJSON = "{\"name\":\"Ilya\",\"job\":\"tester\"}";

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(bodyJSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Ilya"))
                .body("job", equalTo("tester"))
                .body("createdAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Успешное создание пользователя без имени")
    void createNewUserWithoutNameTest() {
        String bodyJSON = "{\"job\":\"tester\"}";

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(bodyJSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("job", equalTo("tester"))
                .body("createdAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Успешное создание пользователя без профессии")
    void createNewUserWithoutJobTest() {
        String bodyJSON = "{\"name\":\"Ilya\"}";

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(bodyJSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Ilya"))
                .body("createdAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Успешное создание пользователя без данных")
    void createNewUserWithEmptyDataTest() {
        String bodyJSON = "{}";

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(bodyJSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("createdAt", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    @Test
    @DisplayName("Отправка запроса с неверным типом данных")
    void createNewUserWithWrongDataTypesTest() {
        String bodyJSON = "{\"name\":123,\"job\":}";

        given()
                .log().all()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(bodyJSON)
                .when()
                .post("/users")
                .then()
                .log().all()
                .statusCode(400);
    }
}
