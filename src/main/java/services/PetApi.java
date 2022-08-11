package services;

import static io.restassured.RestAssured.given;

import dto.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class PetApi {
    public static final String BASE_URI = System.getProperty("base.url", "https://petstore.swagger.io/v2");
    public static final String PET = "/pet";
    private RequestSpecification rspec;

    public PetApi() {
        rspec = given()
                .baseUri(BASE_URI)
                .basePath(PET)
                .accept(ContentType.JSON);
    }

    public ValidatableResponse addPet(Pet pet) {
        return given(rspec)
                     .contentType(ContentType.JSON)
                     .body(pet)
                     .log().all()
                .when()
                     .post()
                .then()
                     .log().all();
    }

    public ValidatableResponse updatePet(String petId, String name, String status) {
        return given(rspec)
                     .pathParam("petId", petId)
                     .contentType(ContentType.URLENC)
                     .formParam("name", name)
                     .formParam("status", status)
                     .log().all()
                .when()
                     .post("/{petId}")
                .then()
                     .log().all();
    }

    public ValidatableResponse getPetById(String petId) {
        return given(rspec)
                     .pathParam("petId", petId)
                     .log().all()
                .when()
                     .get("/{petId}")
                .then()
                     .log().all();
    }

    public ValidatableResponse deletePet(String petId) {
        return given(rspec)
                     .pathParam("petId", petId)
                     .log().all()
                .when()
                     .delete("/{petId}")
                .then()
                     .log().all();
    }
}
