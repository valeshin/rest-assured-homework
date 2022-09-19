package stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserStub {

    private final String basePath = "/user/get";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public UserStub registerStubGetUserAll() {
        ObjectNode user = OBJECT_MAPPER.createObjectNode()
                .put("name", "Test user")
                .put("course", "QA")
                .put("email", "test@test.test")
                .put("age", 23);


        stubFor(get(urlMatching(String.format("%s/(\\d+)", basePath)))
                .willReturn(jsonResponse(user, 200)));

        return this;
    }

    public UserStub registerStubGetUserScore() {
        ObjectNode score = OBJECT_MAPPER.createObjectNode()
                .put("name", "Test user")
                .put("score", 78);


        stubFor(get(urlMatching(String.format("%s/all", basePath)))
                .willReturn(jsonResponse(score, 200)));

        return this;
    }
}
