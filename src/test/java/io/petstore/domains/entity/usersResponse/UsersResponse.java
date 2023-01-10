package io.petstore.domains.entity.usersResponse;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUsersResponse.class)
@JsonDeserialize(as = ImmutableUsersResponse.class)
public interface UsersResponse {
    int code();
    String type();
    String message();

}
