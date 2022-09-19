package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class CourseStub {

    private final String basePath = "/course/get";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void registerStubGetCourseAll() {
        ArrayNode courses = OBJECT_MAPPER.createArrayNode();
        courses.addObject()
                .put("name", "QA java")
                .put("price", 15000);
        courses.addObject()
                .put("name", "Java")
                .put("price", 12000);

        stubFor(get(urlEqualTo(String.format("%s/all", basePath)))
                .willReturn(jsonResponse(courses, 200)));
    }
}
