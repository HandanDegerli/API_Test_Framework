package io.petstore.domains.builders;

import io.petstore.domains.entity.pet.petRequests.*;
import io.petstore.domains.entity.pet.petResponses.PetResponse;
import io.petstore.domains.services.PetServices;
import io.petstore.steps.AccessTest;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PetBuilder {
    private final PetServices petServices;
    private final Logger logger;

    public PetBuilder(){
        petServices = new PetServices();
        logger = Logger.getLogger(AccessTest.class);
    }
    public PetResponse createPet(List<Long> petIdList){
        Category category = ImmutableCategory.builder()
                .id(1)
                .name("Dog")
                .build();

        List<Tag> tags = new ArrayList<>();
        Tag tag1 = ImmutableTag.builder()
                .id(1)
                .name("Golden")
                .build();

        Tag tag2 = ImmutableTag.builder()
                .id(1)
                .name("Gold")
                .build();
        tags.add(tag1);
        tags.add(tag2);

        List<String> photoUrl= new ArrayList<>();
        photoUrl.add("https://www.dailydogtag.com/wp-content/uploads/2020/02/%C2%A9Bark-Gold-Photography-lifestyle-dog-photography-21.jpg");

        Pet pet = ImmutablePet.builder()
                .id(1)
                .category(category)
                .name("Odie")
                .photoUrls(photoUrl)
                .tags(tags)
                .status("pending")
                .build();

        logger.info("Creating a pet");
        logger.info("Created Pet's id is saved on Id List");
        petIdList.add(pet.id());
        return petServices.postPet(pet);
    }
}
