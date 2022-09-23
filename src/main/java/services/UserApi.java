package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class UserApi {
    public static final String BASE_URI = String.format(
            "http://%s:%s",
            System.getProperty("wiremock.host"),
            System.getProperty("wiremock.port"));
    public static final String BASE_PATH = "/user";
    private RequestSpecification rspec;

    public UserApi() {
        rspec = given()
                    .baseUri(BASE_URI)
                    .basePath(BASE_PATH)
                    .contentType(ContentType.JSON);
    }

    public ValidatableResponse getUserAll() {
        return given(rspec)
                    .log().all()
                .when()
                    .get("/get/all")
                .then()
                    .log().all();
    }

    public ValidatableResponse getUserScore(String id) {
        return given(rspec)
                .pathParam("id", id)
                    .log().all()
                .when()
                    .get("/get/{id}")
                .then()
                    .log().all();
    }

    public ValidatableResponse getUserScore(int id) {
        return getUserScore(String.valueOf(id));
    }
}
