package io.petstore.domains.builders;

import io.petstore.domains.entity.user.userRequest.ImmutableUser;
import io.petstore.domains.entity.user.userRequest.User;
import io.petstore.domains.services.UsersServices;
import io.petstore.steps.AccessTest;
import org.apache.log4j.Logger;

import java.util.List;

public class UserBuilder {
    private final UsersServices usersServices;
    private final Logger logger;

    public UserBuilder(){
        usersServices =new UsersServices();
        logger = Logger.getLogger(AccessTest.class);
    }

    public User createUser(List<String> nameList){
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
        logger.info("Created User's username is saved on username List");
        nameList.add(user.username());
        return user;
    }
}
