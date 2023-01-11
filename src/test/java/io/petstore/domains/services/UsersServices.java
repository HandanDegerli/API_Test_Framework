package io.petstore.domains.services;

import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.user.userRequest.User;
import io.petstore.domains.entity.user.usersResponse.UsersResponse;

import java.util.List;

public class UsersServices extends RestClient {
    private final String endpoint;

    public UsersServices() {
        this.endpoint = "user";
    }

    public int getUserByNameStatusCode(String username){
        return get(endpoint + "/" + username).then().extract().response().getStatusCode();
    }

    public int getUserLoginAndGetStatusCode(String username, String value, String password, String val){
        return getWithQueryParams(endpoint + "/login", username, value, password, val).then().extract().response().getStatusCode();
    }

    public int getUserLogoutAndGetStatusCode(){
        return get(endpoint + "/logout").then().extract().response().getStatusCode();
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

    public int postWithUserArray(User[] users){
        return post(endpoint + "/createWithArray", users).then().extract().response().getStatusCode();
    }

    public int postWithUserList(List<User> users){
        return post(endpoint + "/createWithList", users).then().extract().response().getStatusCode();
    }

    public int deleteUserAndGetStatusCode(String username){
        return delete(endpoint + "/" + username).then().extract().response().getStatusCode();
    }

    public int updateUserAndGetStatusCode(User user, String username){
        return put(endpoint + "/" + username, user).then().extract().response().getStatusCode();
    }


}