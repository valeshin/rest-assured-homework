package petstore.pet;

import static org.hamcrest.Matchers.equalTo;

import dto.Pet;
import dto.PetFactory;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import services.PetApi;

@TestInstance(Lifecycle.PER_CLASS)
public class PetTests {

    public Pet pet;
    public PetApi petApi;
    public ResponseSpecification respSpec;

    @BeforeAll
    public void setUpClass() {
        respSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        petApi = new PetApi();
    }

    @BeforeEach
    public void setUpTest() {
        pet = new PetFactory().createPet();
        petApi.addPet(pet);
    }

    @AfterEach
    public void tearDown() {
        if (pet != null) {
            petApi.deletePet(String.valueOf(pet.getId()));
        }
    }

    @Test
    public void checkUpdatePet() {
        int id = pet.getId();
        String strId = String.valueOf(id);

        petApi.updatePet(strId, "Changed Name", "sold")
                .spec(respSpec)
                .body("type", equalTo("unknown"))
                .body("message", equalTo(strId));

        petApi.getPetById(strId)
                .spec(respSpec)
                .body("id", equalTo(id))
                .body("name", equalTo("Changed Name"))
                .body("status", equalTo("sold"));
    }

    @Test
    public void checkGetPet() {
        ValidatableResponse response = petApi.getPetById(String.valueOf(pet.getId()))
                .spec(respSpec)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/PetSchema.json"));

        Pet responsePet = response.extract().body().as(Pet.class);
        Assertions.assertEquals(pet, responsePet, "Питомец в ответе отличается от добавленного ранее");
    }
}
