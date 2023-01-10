package io.petstore.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.petstore.domains.entity.userRequest.ImmutableUser;
import io.petstore.domains.entity.userRequest.User;
import io.petstore.domains.services.UsersServices;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessTest {

    private final UsersServices usersServices;
    private final Logger logger;
    ObjectMapper mapper;

    public AccessTest(){
        usersServices =new UsersServices();
        logger = Logger.getLogger(AccessTest.class);
        mapper = new ObjectMapper();
    }

    @Test
    public void postUser() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'postUser'" );

        User user = createUser();

        logger.info("Creating a User with payload " + mapper.writeValueAsString(user));
        logger.info("Asserting status code is returned as 200");
        assertEquals(200, usersServices.postUserAndGetStatusCode(user), "Response code is not 200 as expected");

        logger.info("Asserting username");
        User usersResponse = usersServices.getUserWithUsername(user.username());
        assertEquals(user.username(), usersResponse.username());
    }

    @Test
    public void getUserWithUserName(){
        logger.info("RUNNING TEST CASE 'getUserWithUserName'" );
        User user = createUser();

        logger.info("Getting User with the USERNAME : " + user.username());
        int responseStatus = usersServices.getUserByNameStatusCode(user.username()).getStatusCode();

        logger.info("Asserting the status code");
        assertEquals(200, responseStatus);
    }


    @Test
    public void getUserWithIncorrectUsername() {
        logger.info("RUNNING TEST CASE 'getUserWithIncorrectUsername'" );
        String username = "Eren";

        logger.info("Getting User with the incorrect USERNAME : " + username);
        int responseStatus = usersServices.getUserByNameStatusCode(username).getStatusCode();

        logger.info("Asserting the status code");
        assertEquals(404, responseStatus);
    }

    @Test
    public void deleteUser() {

        logger.info("RUNNING TEST CASE 'deleteUser'" );
        User user = createUser();

        logger.info("Asserting status code is returned as 200");
        assertEquals(200, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not removed");

        logger.info("Asserting the User is also removed from Users");
       assertEquals(404, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not deleted from Users");

    }

    @Test
    public void updateUser() {

        logger.info("RUNNING TEST CASE 'updateUser'" );
        User user = createUser();

        User secondUser = ImmutableUser.builder()
                .id(16)
                .username("Zeliha")
                .firstName("Handan")
                .lastName("Deger")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();

        logger.info("");
        assertEquals(200, usersServices.updateUserAndGetStatusCode(secondUser, user.username()));
    }

    private User createUser(){
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
