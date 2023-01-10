package io.petstore.domains.services;

import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.userRequest.User;
import io.petstore.domains.entity.usersResponse.UsersResponse;
import io.restassured.response.Response;

public class UsersServices extends RestClient {
    private final String endpoint;

    public UsersServices() {
        this.endpoint = "user";
    }

    public Response getUserByNameStatusCode(String username){
        return get(endpoint + "/" + username);
    }

    public User getUserWithUsername(String username){
        return get(endpoint + "/" + username).as(User.class);
    }

    public int postUserAndGetStatusCode(User user){
        return post(endpoint, user).then().extract().response().getStatusCode();
    }

    public void postUser(User user){
        post(endpoint, user).then().extract().response().body().as(UsersResponse.class);
    }

    public int deleteUserAndGetStatusCode(String username){
        return delete(endpoint + "/" + username).then().extract().response().getStatusCode();
    }

    public int updateUserAndGetStatusCode(User user, String username){
        return put(endpoint + "/" + username, user).then().extract().response().getStatusCode();
    }

}