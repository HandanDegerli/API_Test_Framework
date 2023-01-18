package io.petstore.domains.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.pet.petRequests.Pet;
import io.petstore.domains.entity.pet.petResponses.PetResponse;
import io.restassured.http.Header;


public class PetServices extends RestClient {

    private final String endpoint;
    private final ObjectMapper mapper;

    public PetServices(){

        endpoint = "pet";
        mapper = new ObjectMapper();
    }

    public int getPetAndGetStatusCode(long petId){
        return get(endpoint + "/" + petId).then().extract().response().getStatusCode();
    }

    public  PetResponse getPet(long petId){
        return get(endpoint + "/" + petId).then().extract().response().body().as(PetResponse.class);
    }

    public int getPetsWithPetStatusAndGetStatusCode(String petStatus, String value){
        return getWithQueryParam(endpoint + "/findByStatus", petStatus, value).then().extract().response().getStatusCode();
    }

    public PetResponse[] getPetsWithPetStatus(String petStatus, String value){
        return getWithQueryParam(endpoint + "/findByStatus", petStatus, value).then().extract().response().as(PetResponse[].class);
    }
    public int postPetAndGetStatusCode(Pet pet){
        return post(endpoint, pet).then().extract().response().getStatusCode();
    }
    public PetResponse postPet(Pet pet){
        return post(endpoint, pet).then().extract().response().body().as(PetResponse.class);
    }

    public int deletePetAndGetStatusCode(long petId, Header key){
        return deleteWithAuth(endpoint + "/" + petId, key).getStatusCode();
    }

    public int updatePetAndGetStatusCode(Pet pet){
        return put(endpoint, pet).then().extract().response().getStatusCode();
    }

    public int updatePetNameAndStatusAndGetStatusCode(long petId, String nameValue, String statusValue){
        return postDifferentContentType(endpoint + "/" + petId, nameValue, statusValue).then().extract().response().getStatusCode();
    }

    public int postFileAndText(long petId, String additionalMetadata, String filePath){
        return postDifferentContentTypeAndFile(endpoint + "/" + petId + "/uploadImage", additionalMetadata, filePath).then().extract().response().getStatusCode();
    }

    public PetResponse updatePet(Pet pet){
        return put(endpoint, pet).then().extract().response().body().as(PetResponse.class);
    }
}
