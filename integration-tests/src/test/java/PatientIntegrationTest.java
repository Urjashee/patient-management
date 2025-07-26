import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        String loginPayload = """
                {
                            "email": "testuser@test.com",
                            "password": "password123"
                          }
                """;

        String token = given()
                .contentType("application/json") //Arrange
                .body(loginPayload)
                .when() // Act
                .post("/auth/login")
                .then() // Assert
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/patientsgit ")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }
}
