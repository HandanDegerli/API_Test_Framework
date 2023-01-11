package io.petstore.domains.entity.user.userRequest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUser.class)
@JsonDeserialize(as = ImmutableUser.class)
//@Value.Style(jdkOnly = true) - eğer List seklinde bir field gonderceksek yararlı
public interface User {
    int id();
    String username();
    String firstName();
    String lastName();
    String email();
    String password();
    String phone();
    int userStatus();
}
