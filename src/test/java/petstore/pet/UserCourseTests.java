package petstore.pet;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.hamcrest.Matchers.equalTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.Course;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.CourseApi;
import services.UserApi;
import stubs.CourseStub;
import stubs.UserStub;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserCourseTests {

    public static ResponseSpecification respSpec;
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUpClass() {
        respSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        if (System.getProperty("wiremock.remote").equalsIgnoreCase("false")) {
            wireMockServer = new WireMockServer(Integer.parseInt(System.getProperty("wiremock.port")));
            wireMockServer.start();
        }
        configureFor(System.getProperty("wiremock.host"), Integer.parseInt(System.getProperty("wiremock.port")));

        new UserStub()
                .registerStubGetUserAll()
                .registerStubGetUserScore();
        new CourseStub()
                .registerStubGetCourseAll();
    }

    @AfterAll
    public static void stopMock() {
        if (System.getProperty("wiremock.remote").equalsIgnoreCase("false"))
            wireMockServer.stop();
        WireMock.resetToDefault();
    }

    @Test
    public void checkGetUserAll() {
        new UserApi().getUserAll()
                .spec(respSpec)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/UserSchema.json"))
                .body("name", equalTo("Test user"))
                .body("course", equalTo("QA"))
                .body("email", equalTo("test@test.test"))
                .body("age", equalTo(23));
    }

    @Test
    public void checkGetUserScore() {
        new UserApi().getUserScore(13231231)
                .spec(respSpec)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/ScoreSchema.json"))
                .body("name", equalTo("Test user"))
                .body("score", equalTo(78));
    }

    @Test
    public void checkGetAllCourse() {
        Course java = new Course(12000, "Java");
        Course qaJava = new Course(15000, "QA java");
        List<Course> expectedCourses = Stream.of(java, qaJava)
                .sorted(Comparator.comparing(Course::getName, Comparator.naturalOrder()))
                .collect(Collectors.toList());

        List<Course> responseCourses = new CourseApi().getCourseAll()
                .spec(respSpec)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CourseSchema.json"))
                .extract()
                .response()
                .jsonPath()
                .getList("", Course.class)
                .stream()
                .sorted(Comparator.comparing(Course::getName, Comparator.naturalOrder()))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedCourses, responseCourses, "Список курсов в ответе отличается от ожидаемого");
    }
}
