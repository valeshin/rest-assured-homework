package petstore.pet;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import services.PetApi;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetNegativeTests {

    public PetApi petApi;
    public ResponseSpecification respSpec;

    @BeforeAll
    public void setUpClass() {
        respSpec = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .expectBody("code", equalTo(404))
                .expectBody("type", equalTo("unknown"))
                .build();
        petApi = new PetApi();
    }

    @Test
    public void checkUpdatePetNotFound() {
        petApi.updatePet("1000000000", "Changed Name", "sold")
                .spec(respSpec)
                .body("message", equalTo("not found"));
    }

    @Test
    public void checkUpdatePetByWrongId() {
        petApi.updatePet("wrongId", "Changed Name", "sold")
                .spec(respSpec)
                .body("message", equalTo("java.lang.NumberFormatException: For input string: \"wrongId\""));
    }

    @Test
    public void checkGetPetNotFound() {
        petApi.getPetById("1000000000")
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));
    }

    @Test
    public void checkGetPetByWrongId() {
        petApi.getPetById("wrongId")
                .spec(respSpec)
                .body("message", equalTo("java.lang.NumberFormatException: For input string: \"wrongId\""));
    }
}
