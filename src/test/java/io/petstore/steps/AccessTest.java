package io.petstore.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.petstore.domains.entity.userRequest.ImmutableUser;
import io.petstore.domains.entity.userRequest.User;
import io.petstore.domains.services.UsersServices;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void getUserWithUserName(){
        logger.info("RUNNING TEST CASE 'getUserWithUserName'" );
        User user = createUser();

        logger.info("Getting User with the USERNAME : " + user.username());
        int responseStatus = usersServices.getUserByNameStatusCode(user.username());

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, responseStatus);
    }

    @Test
    public void getUserWithIncorrectUsername() {
        logger.info("RUNNING TEST CASE 'getUserWithIncorrectUsername'" );
        String username = "EREN";

        logger.info("Getting User with the incorrect USERNAME : " + username);
        int responseStatus = usersServices.getUserByNameStatusCode(username);

        logger.info("Asserting Status Code as 404");
        assertEquals(404, responseStatus);
    }

    @Test
    public void getUserLogin(){

        logger.info("RUNNING TEST CASE 'getUserLogin'" );
        User user = createUser();

        //RestAssured.given().queryParams("username", user.username(), "password", user.password()).log().all().get();

        logger.info("Get User Login and Getting Status Code then Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.getUserLoginAndGetStatusCode("username", user.username(), "password", user.password()));
    }

    @Test
    public void getUserLogout(){

        logger.info("RUNNING TEST CASE 'getUserLogout'" );

        logger.info("Get User Logout and Getting Status Code then Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.getUserLogoutAndGetStatusCode(), "User could not logout");
    }

    @Test
    public void postUser() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'postUser'" );
        User user = createUser();

        logger.info("Creating a User with payload " + mapper.writeValueAsString(user));
        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postUserAndGetStatusCode(user), "Response code is not 200 as expected");

        logger.info("Asserting USERNAME");
        User userResponse = usersServices.getUserWithUsername(user.username());
        assertEquals(user.username(), userResponse.username());
    }

    @Test
    public void postUserArrayAndGetStatusCode() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'postUserArrayAndGetStatusCode'" );

        User user1= ImmutableUser.builder()
                .id(17)
                .username("Odin")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();

        User user2= ImmutableUser.builder()
                .id(18)
                .username("Odie")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(2).build();

        logger.info("Creating Users with payloads and add them to users array" + mapper.writeValueAsString(user1) + " , " + mapper.writeValueAsString(user2));
        User[] users = {user1, user2};

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postWithUserArray(users), "Response code is not 200 as expected");

        logger.info("Asserting first user's username in array : " + users[0].username());
        User user1Response = usersServices.getUserWithUsername(users[0].username());
        assertEquals(users[0].username(), user1Response.username());

        logger.info("Asserting second user's username in array : " + users[1].username());
        User user2Response = usersServices.getUserWithUsername(users[1].username());
        assertEquals(users[1].username(), user2Response.username());
    }

    @Test
    public void postUserListAndGetStatusCode() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'postUserListAndGetStatusCode'" );

        User user1= ImmutableUser.builder()
                .id(17)
                .username("Odin")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();

        User user2= ImmutableUser.builder()
                .id(18)
                .username("Odie")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(2).build();

        logger.info("Creating Users with payloads and add them to user list" + mapper.writeValueAsString(user1) + " , " + mapper.writeValueAsString(user2));
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postWithUserList(users), "Response code is not 200 as expected");

        logger.info("Asserting first user's username on the user list : " + users.get(0).username());
        User user1Response = usersServices.getUserWithUsername(users.get(0).username());
        assertEquals(users.get(0).username(), user1Response.username());

        logger.info("Asserting second user's username on the user list : " + users.get(1).username());
        User user2Response = usersServices.getUserWithUsername(users.get(1).username());
        assertEquals(users.get(1).username(), user2Response.username());
    }

    @Test
    public void deleteUser() {

        logger.info("RUNNING TEST CASE 'deleteUser'" );
        User user = createUser();

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not removed");

        logger.info("Getting Removed User and and Asserting Status Code as 404");
        assertEquals(404, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not deleted from Users");

    }

    @Test
    public void updateUser() throws JsonProcessingException {

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

        logger.info("Updating User as Another User and Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.updateUserAndGetStatusCode(secondUser, user.username()));
        logger.info("User is updated with payload " + mapper.writeValueAsString(secondUser));
    }

    @Test
    public void userE2ETest() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'userE2ETest'" );
        User user= ImmutableUser.builder()
                .id(17)
                .username("ODIN")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();

        logger.info("Creating a User with payload " + mapper.writeValueAsString(user));
        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postUserAndGetStatusCode(user), "Response code is not 200 as expected");

        logger.info("Getting User with the USERNAME : " + user.username());
        User userResponse1 = usersServices.getUserWithUsername(user.username());
        logger.info("Asserting username as " + user.username());
        assertEquals(user.username(), userResponse1.username());

        User secondUser = ImmutableUser.builder()
                .id(16)
                .username("ODIE")
                .firstName("Handan")
                .lastName("Deger")
                .email("odie@dog.com")
                .password("Odie123.")
                .phone("12345678")
                .userStatus(1).build();

        logger.info("Updating User as Another User and Asserting Status Code as SUCCESS");
        logger.info("User is updated with payload " + mapper.writeValueAsString(secondUser));
        assertEquals(200, usersServices.updateUserAndGetStatusCode(secondUser, user.username()));

        logger.info("Getting Updated User and Assert with the Payload");
        User userResponse2 = usersServices.getUserWithUsername(secondUser.username());
        assertEquals(secondUser, userResponse2);

        logger.info("Deleting User and Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.deleteUserAndGetStatusCode(secondUser.username()), "User was not removed");

        logger.info("Asserting User is Also Removed");
        assertEquals(404, usersServices.deleteUserAndGetStatusCode(userResponse2.username()), "User was not deleted from Users");

        logger.info("Getting Removed User and and Asserting Status Code as 404");
        int userResponseStatus = usersServices.getUserByNameStatusCode(secondUser.username());
        assertEquals(404, userResponseStatus);

    }

    private User createUser(){
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
