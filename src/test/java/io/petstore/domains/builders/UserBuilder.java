package io.petstore.domains.builders;

import io.petstore.domains.entity.user.userRequest.ImmutableUser;
import io.petstore.domains.entity.user.userRequest.User;
import io.petstore.domains.services.PetServices;
import io.petstore.domains.services.StoreServices;
import io.petstore.domains.services.UsersServices;
import io.petstore.steps.AccessTest;
import org.apache.log4j.Logger;

public class UserBuilder {
    private final UsersServices usersServices;
    private final Logger logger;

    public UserBuilder(){
        usersServices =new UsersServices();
        logger = Logger.getLogger(AccessTest.class);
    }

    public User createUser(){
        logger.info("Creating a COMMON USER");
        User user = ImmutableUser.builder()
                .id(16)
                .username("Handan")
                .firstName("Handan")
                .lastName("Deger")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();
        usersServices.postUser(user);
        return user;
    }
}
