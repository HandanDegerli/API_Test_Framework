package io.petstore.domains.services;

import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.pet.petRequests.Pet;
import io.petstore.domains.entity.pet.petResponses.PetResponse;
import io.restassured.http.Header;


public class PetServices extends RestClient {

    private final String endpoint;

    public PetServices(){
        endpoint = "pet";
    }

    public int getPetAndGetStatusCode(int petId){
        return get(endpoint + "/" + petId).then().extract().response().getStatusCode();
    }

    public  PetResponse getPet(int petId){
        return get(endpoint + "/" + petId).then().extract().response().body().as(PetResponse.class);
    }
    public int postPetAndGetStatusCode(Pet pet){
        return post(endpoint, pet).then().extract().response().getStatusCode();
    }
    public PetResponse postPet(Pet pet){
        return post(endpoint, pet).then().extract().response().body().as(PetResponse.class);
    }

    public int deletePetAndGetStatusCode(int petId, Header key){
        return deleteWithAuth(endpoint + "/" + petId, key).getStatusCode();
    }

    public int updatePetAndGetStatusCode(Pet pet){
        return put(endpoint, pet).then().extract().response().getStatusCode();
    }

    public PetResponse updatePet(Pet pet){
        return put(endpoint, pet).then().extract().response().body().as(PetResponse.class);
    }
}
