package io.petstore.domains.entity.pet.petResponses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.petstore.domains.entity.pet.petRequests.Category;
import io.petstore.domains.entity.pet.petRequests.Tag;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutablePetResponse.class)
@JsonDeserialize(as = ImmutablePetResponse.class)
@Value.Style(jdkOnly = true)
public interface PetResponse {

    int id();
    Category category();
    String name();
    List<String> photoUrls();
    List<Tag> tags();
    String status();
}
