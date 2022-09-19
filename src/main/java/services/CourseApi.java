package services;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CourseApi {
    public static final String BASE_URI = String.format(
            "http://%s:%s",
            System.getProperty("wiremock.host"),
            System.getProperty("wiremock.port"));
    public static final String BASE_PATH = "/course";
    private RequestSpecification rspec;

    public CourseApi() {
        rspec = given()
                    .baseUri(BASE_URI)
                    .basePath(BASE_PATH)
                    .contentType(ContentType.JSON);
    }

    public ValidatableResponse getCourseAll() {
        return given(rspec)
                    .log().all()
                .when()
                    .get("/get/all")
                .then()
                    .log().all();
    }
}
