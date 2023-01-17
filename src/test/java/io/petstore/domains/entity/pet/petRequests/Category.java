package io.petstore.domains.entity.pet.petRequests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@Value.Immutable
@JsonDeserialize(as = ImmutableCategory.class)
@JsonSerialize(as = ImmutableCategory.class)
public interface Category{


    long id();
    @Nullable
    String name();
}
