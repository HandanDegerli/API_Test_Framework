package io.petstore.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.petstore.domains.builders.OrderBuilder;
import io.petstore.domains.builders.PetBuilder;
import io.petstore.domains.entity.pet.petRequests.*;
import io.petstore.domains.entity.pet.petResponses.PetResponse;
import io.petstore.domains.entity.store.storeRequest.OrderRequest;
import io.petstore.domains.entity.store.storeResponse.OrderResponse;
import io.petstore.domains.entity.user.userRequest.ImmutableUser;
import io.petstore.domains.entity.user.userRequest.User;
import io.petstore.domains.services.PetServices;
import io.petstore.domains.services.StoreServices;
import io.petstore.domains.services.UsersServices;
import io.petstore.utils.UserFileHelper;
import io.restassured.http.Header;
import org.apache.log4j.Logger;
import org.junit.After;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTest {
    private final UsersServices usersServices;
    private final StoreServices storeServices;
    private final PetServices petServices;
    private final OrderBuilder orderBuilder;
    private final PetBuilder petBuilder;
    private final Logger logger;
    ObjectMapper mapper;

    private final List<String> usernameList;
    private final List<Integer> orderIdList;
    private final List<Long> petIdList;

    public AccessTest(){
        usersServices =new UsersServices();
        storeServices = new StoreServices();
        petServices = new PetServices();
        orderBuilder = new OrderBuilder();
        petBuilder = new PetBuilder();
        logger = Logger.getLogger(AccessTest.class);
        mapper = new ObjectMapper();
        usernameList = new ArrayList<>();
        orderIdList = new ArrayList<>();
        petIdList = new ArrayList<>();
    }

    //              ***    USER TEST CASES    ***
    @Test
    public void getUserWithUserName() throws IOException {
        logger.info("RUNNING TEST CASE 'getUserWithUserName'" );

        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();
        usersServices.postUser(user);

        logger.info("Created User's username is saved on username List");
        usernameList.add(user.username());

        logger.info("Getting User with the USERNAME : " + user.username());
        int responseStatus = usersServices.getUserByNameStatusCode(user.username());

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, responseStatus);
    }

    @Test
    public void getUserWithIncorrectUsername() {
        logger.info("RUNNING TEST CASE 'getUserWithIncorrectUsername'" );
        String username = "FREDDY";

        logger.info("Getting User with the incorrect USERNAME : " + username);
        int responseStatus = usersServices.getUserByNameStatusCode(username);

        logger.info("Asserting Status Code as 404");
        assertEquals(404, responseStatus);
    }

    @Test
    public void getUserLogin() throws IOException {

        logger.info("RUNNING TEST CASE 'getUserLogin'" );

        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();
        usersServices.postUser(user);

        logger.info("Created User's username is saved on username List");
        usernameList.add(user.username());

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
    public void postUser() throws IOException {

        logger.info("RUNNING TEST CASE 'postUser'" );

        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();

        logger.info("Created User's username is saved on username List");
        usernameList.add(user.username());

        logger.info("Created User's payload is: " + mapper.writeValueAsString(user));
        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postUserAndGetStatusCode(user), "Response code is not 200 as expected");

        logger.info("Asserting USERNAME as " + user.username());
        User userResponse = usersServices.getUserWithUsername(user.username());
        assertEquals(user.username(), userResponse.username());
    }

    @Test
    public void postUserArray() throws IOException {

        logger.info("RUNNING TEST CASE 'postUserArrayAndGetStatusCode'" );

        logger.info("First User is created");
        User user1 = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();

        logger.info("Created First User's username is saved on username List");
        usernameList.add(user1.username());

        logger.info("Second User is created");
        User user2= ImmutableUser.builder().from(UserFileHelper.fileReader())
                .id(18).username("Odie")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .userStatus(2).build();

        logger.info("Created Second User's username is saved on username List");
        usernameList.add(user2.username());

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
    public void postUserList() throws IOException {

        logger.info("RUNNING TEST CASE 'postUserListAndGetStatusCode'" );

        logger.info("First User is created");
        User user1 = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();

        logger.info("Created First User's username is saved on username List");
        usernameList.add(user1.username());

        logger.info("Second User is created");
        User user2= ImmutableUser.builder().from(UserFileHelper.fileReader())
                .id(18).username("Odie")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com")
                .userStatus(2).build();

        logger.info("Created Second User's username is saved on username List");
        usernameList.add(user2.username());

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
    public void deleteUser() throws IOException {

        logger.info("RUNNING TEST CASE 'deleteUser'" );
        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();
        usersServices.postUser(user);

        logger.info("Created User's username is saved on username List");
        usernameList.add(user.username());

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not removed");

        logger.info("Getting Removed User and Asserting Status Code as 404");
        assertEquals(404, usersServices.deleteUserAndGetStatusCode(user.username()), "User was not deleted from Users");

    }

    @Test
    public void updateUser() throws IOException {

        logger.info("RUNNING TEST CASE 'updateUser'" );

        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();
        usersServices.postUser(user);

        logger.info("Created User's username is saved on username List as " + user.username());
        usernameList.add(user.username());

        logger.info("Second User is created");
        User secondUser= ImmutableUser.builder().from(UserFileHelper.fileReader()).username("Penny").build();

        logger.info("Created Second User's username is saved on username List as " + secondUser.username());
        usernameList.add(secondUser.username());

        logger.info("Updating First User as Second User and Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.updateUserAndGetStatusCode(secondUser, user.username()));
        logger.info("Pet is updated with payload " + mapper.writeValueAsString(secondUser));
    }

    @Test
    public void userE2ETest() throws IOException {

        logger.info("RUNNING TEST CASE 'userE2ETest'" );

        logger.info("User is created");
        User user = ImmutableUser.builder().from(UserFileHelper.fileReader()).build();
        logger.info("Created User's username is saved on username List");
        usernameList.add(user.username());

        logger.info("Created User's payload is: " + mapper.writeValueAsString(user));
        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, usersServices.postUserAndGetStatusCode(user), "Response code is not 200 as expected");

        logger.info("Getting User with the USERNAME : " + user.username());
        User userResponse1 = usersServices.getUserWithUsername(user.username());
        logger.info("Asserting username as " + user.username());
        assertEquals(user.username(), userResponse1.username());

        logger.info("Second User is created");
        User secondUser= ImmutableUser.builder().from(UserFileHelper.fileReader())
                .id(17).username("ODIN")
                .firstName("Str")
                .lastName("Str")
                .email("odie@dog.com").build();

        logger.info("Created Second User's username is saved on username List");
        usernameList.add(secondUser.username());

        logger.info("Updating First User as Second User and Asserting Status Code as SUCCESS");
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

    //              ***    STORE TEST CASES    ***

    @Test
    public void getOrderWithOrderId() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'getOrderWithOrderId'" );

        OrderResponse order = orderBuilder.createOrder(orderIdList);

        logger.info("Getting Order with the ORDER ID : " + order.id() );
        OrderResponse orderResponse = storeServices.getOrderByOrderId(order.id());

        logger.info("Asserting ORDER PAYLOAD as :" + mapper.writeValueAsString(orderResponse));
        assertEquals(order, orderResponse);

    }

    @Test
    public void getOrderWithIncorrectOrderId() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'getOrderWithIncorrectOrderId'" );

        logger.info("Getting Order with the INCORRECT ORDER ID : 4444444");
        int responseStatusCode = storeServices.getOrderByOrderIdAndGetStatusCode(4444444);

        logger.info("Asserting Status Code as 404");
        assertEquals(404, responseStatusCode);

    }

    @Test
    public void postOrder() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'postOrder'" );

        OrderResponse orderResponse = orderBuilder.createOrder(orderIdList);
        OrderRequest order = mapper.convertValue(orderResponse, OrderRequest.class);

        logger.info("Posting order with payload " + mapper.writeValueAsString(order));
        int responseStatusCode = storeServices.postOrderAndGetStatusCode(order);

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, responseStatusCode , "Response code is not 200 as expected");

    }

    @Test
    public void deleteOrder() {

        logger.info("RUNNING TEST CASE 'deleteOrder'" );
        OrderResponse order = orderBuilder.createOrder(orderIdList);

        logger.info("Deleting Exist Order and Asserting Status Code as SUCCESS");
        assertEquals(200, storeServices.deleteOrderByOrderIdAndGetStatusCode(order.id()), "Order was not removed");

        logger.info("Deleting Removed Order and Asserting Status Code as 404");
        assertEquals(404, storeServices.deleteOrderByOrderIdAndGetStatusCode(order.id()), "Order was not removed");

    }

    @Test
    public void getStoreInventory(){
        logger.info("RUNNING TEST CASE 'getStoreInventory'" );

        logger.info("Getting Inventory of Store and Status Code");
        int inventoryResponseStatusCode = storeServices.getStoreInventoryAndGetStatusCode();

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, inventoryResponseStatusCode , "Response code is not 200 as expected");

    }

    @Test
    public void orderE2ETest() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'orderE2ETest'" );

        OrderResponse order = orderBuilder.createOrder(orderIdList);
        OrderRequest orderConvert = mapper.convertValue(order, OrderRequest.class);

        logger.info("Posting order with payload " + mapper.writeValueAsString(orderConvert));
        int responseStatusCode = storeServices.postOrderAndGetStatusCode(orderConvert);

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, responseStatusCode , "Response code is not 200 as expected");

        logger.info("Getting Order with the ORDER ID : " + order.id() );
        OrderResponse orderResponse = storeServices.getOrderByOrderId(order.id());

        logger.info("Asserting ORDER PAYLOAD as :" + mapper.writeValueAsString(orderResponse));
        assertEquals(order, orderResponse);

        logger.info("Deleting Exist Order and Asserting Status Code as SUCCESS");
        assertEquals(200, storeServices.deleteOrderByOrderIdAndGetStatusCode(order.id()), "Order was not removed");

        logger.info("Asserting Order is Also Removed");
        assertEquals(404, storeServices.deleteOrderByOrderIdAndGetStatusCode(order.id()), "Order was not removed");

        logger.info("Getting Removed Order and and Asserting Status Code as 404");
        int orderResponseStatus = storeServices.getOrderByOrderIdAndGetStatusCode(order.id());
        assertEquals(404, orderResponseStatus);

    }

    //              ***    PET TEST CASES    ***


    @Test
    public void getPetWithPetId() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'getPetWithPetId'" );

        PetResponse pet = petBuilder.createPet(petIdList);

        logger.info("Getting Pet with the PET ID : " + pet.id() );
        PetResponse petResponse = petServices.getPet(pet.id());

        logger.info("Asserting PET PAYLOAD as :" + mapper.writeValueAsString(petResponse));
        assertEquals(pet, petResponse);

    }

    @Test
    public void getPetWithIncorrectPetId() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'getPetWithIncorrectPetId'" );

        logger.info("Getting Pet with the INCORRECT PET ID : 11111");
        int responseStatusCode = petServices.getPetAndGetStatusCode(11111);

        logger.info("Asserting Status Code as 404");
        assertEquals(404, responseStatusCode);

    }

    @Test
    public void getPetWithPetStatus() throws JsonProcessingException {

        logger.info("RUNNING TEST CASE 'getPetWithPetStatus'" );
        PetResponse pet = petBuilder.createPet(petIdList);

        logger.info("Get Pet with Pet Status as " + "'" +pet.status() + "'" + " and Getting Status Code then Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.getPetsWithPetStatusAndGetStatusCode("status", pet.status()));


        PetResponse[] petListResponse = petServices.getPetsWithPetStatus("status", pet.status());

        PetResponse actualPet = Arrays.stream(petListResponse).filter(petResponse -> petResponse.id() == pet.id()).findFirst().get();

        logger.info("Asserting PET PAYLOAD as :" + mapper.writeValueAsString(actualPet));
        assertEquals(pet, actualPet);


    }
    @Test
    public void postPet() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'postPet'" );

        PetResponse petResponse = petBuilder.createPet(petIdList);
        Pet pet = mapper.convertValue(petResponse, Pet.class);

        logger.info("Posting a pet with payload " + mapper.writeValueAsString(pet));
        int responseStatusCode = petServices.postPetAndGetStatusCode(pet);

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, responseStatusCode , "Response code is not 200 as expected");

    }

    @Test
    public void postFileToExistPet() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'postFileToExistPet'" );
        PetResponse pet = petBuilder.createPet(petIdList);
        logger.info("Pet is created with payload " + mapper.writeValueAsString(pet));

        logger.info("Uploading Image with text successfully and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.postFileAndText(pet.id(), "Puppy Golden", "Puppy.jpeg"));

    }

    @Test
    public void updatePetNameAndStatus() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'updatePetNameAndStatus'" );
        PetResponse pet = petBuilder.createPet(petIdList);
        logger.info("Pet is created with payload " + mapper.writeValueAsString(pet));

        logger.info("Updating Pet's Name and Pet's Status successfully and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.updatePetNameAndStatusAndGetStatusCode(pet.id(), "Summer", "Sold"));

        logger.info("Getting Partially Updated Pet with the PET ID : " + pet.id());
        PetResponse petResponse = petServices.getPet(pet.id());

        logger.info("Pet is updated with NAME and STATUS. Updated Pet Payload is " + mapper.writeValueAsString(petResponse));

        logger.info("Asserting NOT Equals request Pet Name: " + pet.name() + " and " + " response Pet Name: " + petResponse.name());
        assertNotEquals(pet.name(), petResponse.name());

        logger.info("Asserting NOT Equals request Pet Status: " + pet.status() + " and " + " response Pet Status: " + petResponse.status());
        assertNotEquals(pet.status(), petResponse.status());
    }

    @Test
    public void updatePet() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'updatePet'" );
        PetResponse pet = petBuilder.createPet(petIdList);
        logger.info("Pet is created with payload " + mapper.writeValueAsString(pet));
        List<Tag> tags = new ArrayList<>();
        Tag tag2 = ImmutableTag.builder()
                .id(2)
                .name("Brown")
                .build();
        tags.add(pet.tags().get(0));
        tags.add(tag2);
        List<String> photoUrl= new ArrayList<>();
        photoUrl.add("https://www.skpups.com/site/assets/files/33093/susangolden17-1.2123x1413.jpg");
        Pet updatedPet = ImmutablePet.builder()
                .id(1)
                .category(pet.category())
                .name("Alex")
                .photoUrls(photoUrl)
                .tags(tags)
                .status(pet.status())
                .build();

        logger.info("Created Pet's id is saved on Id List");
        petIdList.add(updatedPet.id());

        logger.info("Updating Pet as Another Pet and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.updatePetAndGetStatusCode(updatedPet));
        logger.info("Pet is updated with payload " + mapper.writeValueAsString(updatedPet));
    }

    @Test
    public void deletePet() {

        logger.info("RUNNING TEST CASE 'deletePet'" );
        PetResponse pet = petBuilder.createPet(petIdList);

        Header key = new Header("api_key", "Content-Type,api_key,Authorization");

        logger.info("Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.deletePetAndGetStatusCode(pet.id(), key), "Pet was not removed");

        logger.info("Getting Removed Pet and and Asserting Status Code as 404");
       assertEquals(404, petServices.deletePetAndGetStatusCode(pet.id(), key), "Pet was not deleted");

    }

    @Test
    public void petE2ETest() throws JsonProcessingException {
        logger.info("RUNNING TEST CASE 'petE2ETest'" );

        Header key = new Header("api_key", "Content-Type,api_key,Authorization");

        PetResponse createdPet = petBuilder.createPet(petIdList);
        logger.info("Created Pet with payload " + mapper.writeValueAsString(createdPet));

        logger.info("Getting Pet with the PET ID : " + createdPet.id() );
        PetResponse createdPetResponse = petServices.getPet(createdPet.id());

        logger.info("Asserting PET PAYLOAD as :" + mapper.writeValueAsString(createdPetResponse));
        assertEquals(createdPet, createdPetResponse);

        logger.info("Updating Pet's Name as 'Summer' and Pet's Status  as 'Sold' successfully and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.updatePetNameAndStatusAndGetStatusCode(createdPetResponse.id(), "Summer", "Sold"));

        logger.info("Getting Partially Updated Pet with the PET ID : " + createdPetResponse.id());
        PetResponse petResponse = petServices.getPet(createdPetResponse.id());
        logger.info("Pet is Partially Updated with NAME and STATUS. Updated Pet Payload is " + mapper.writeValueAsString(petResponse));

        logger.info("Getting Pet List with the Partially Updated PET STATUS : " + petResponse.status());
        PetResponse[] petListResponse = petServices.getPetsWithPetStatus("status", petResponse.status());

        logger.info("Getting Partially Updated Pet from Pet List with the PET ID : " + petResponse.id());
        PetResponse actualPet = Arrays.stream(petListResponse).filter(p -> p.id() == petResponse.id()).findFirst().orElseThrow(null);

        logger.info("Asserting Partially Updated PET PAYLOAD as :" + mapper.writeValueAsString(actualPet));
        assertEquals(petResponse, actualPet);

        logger.info("Uploading Image with text successfully and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.postFileAndText(actualPet.id(), "Puppy Golden", "Puppy.jpeg"));

        logger.info("Getting Pet with the PET ID : " + actualPet.id() );
        PetResponse response = petServices.getPet(actualPet.id());

        logger.info("Asserting PET PAYLOAD as :" + mapper.writeValueAsString(response));
        assertEquals(actualPet, response);

        Pet updatedPet = ImmutablePet.builder()
                .id(2)
                .category(response.category())
                .name("Alex")
                .photoUrls(response.photoUrls())
                .tags(response.tags())
                .status(response.status())
                .build();

        logger.info("Updating Pet as Another Pet and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.updatePetAndGetStatusCode(updatedPet));
        logger.info("Pet is updated with payload " + mapper.writeValueAsString(updatedPet));

        logger.info("Updated Pet's id is saved on Id List");
        petIdList.add(updatedPet.id());

        logger.info("Deleting Exist Pet and Asserting Status Code as SUCCESS");
        assertEquals(200, petServices.deletePetAndGetStatusCode(updatedPet.id(), key), "Pet was not removed");

        logger.info("Getting Removed Pet and and Asserting Status Code as 404");
        assertEquals(404, petServices.deletePetAndGetStatusCode(updatedPet.id(), key), "Pet was not deleted");

    }

    @After
    public void cleanUp(){
        logger.info("RUNNING TEST CASE 'cleanUp'" );
        Header key = new Header("api_key", "Content-Type,api_key,Authorization");

        logger.info("All data is deleted" );

        usernameList.forEach(usersServices::deleteUserAndGetStatusCode);

        orderIdList.forEach(storeServices::deleteOrderByOrderIdAndGetStatusCode);

        petIdList.forEach(petId -> petServices.deletePetAndGetStatusCode(petId, key));

    }
}
