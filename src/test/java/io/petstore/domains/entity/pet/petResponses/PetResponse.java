package io.petstore.domains.entity.pet.petResponses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.petstore.domains.entity.pet.petRequests.Category;
import io.petstore.domains.entity.pet.petRequests.Tag;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutablePetResponse.class)
@JsonDeserialize(as = ImmutablePetResponse.class)
@Value.Style(jdkOnly = true)

public interface PetResponse {

    long id();
    @Nullable
    Category category();
    @Nullable
    String name();
    List<String> photoUrls();
    @Nullable
    List<Tag> tags();
    String status();
}
