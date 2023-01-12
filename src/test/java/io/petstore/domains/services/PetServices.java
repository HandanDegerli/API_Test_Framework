package io.petstore.domains.services;

import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.pet.petRequests.Pet;
import io.petstore.domains.entity.pet.petResponses.PetResponse;


public class PetServices extends RestClient {

    private final String endpoint;

    public PetServices(){
        endpoint = "pet";
    }

    public int postPetAndGetStatusCode(Pet pet){
        return post(endpoint, pet).then().extract().response().getStatusCode();
    }

    public PetResponse postPet(Pet pet){
        return post(endpoint, pet).then().extract().response().body().as(PetResponse.class);
    }
}
