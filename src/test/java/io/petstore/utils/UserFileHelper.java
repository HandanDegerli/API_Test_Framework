package io.petstore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.petstore.domains.entity.user.userRequest.User;

import java.io.File;
import java.io.IOException;

public class UserFileHelper {

    public static User fileReader() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(new File("/Users/zelihadegerli/Desktop/RestAssured-API/apiTestApp/user.json"), User.class);
        return user;
    }

}
