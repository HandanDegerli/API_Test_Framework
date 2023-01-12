package io.petstore.domains.entity.pet.petRequests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutablePet.class)
@JsonDeserialize(as = ImmutablePet.class)
@Value.Style(jdkOnly = true)
public interface Pet {

    int id();
    Category category();
    String name();
    List<String> photoUrls();
    List<Tag> tags();
    String status();


}
