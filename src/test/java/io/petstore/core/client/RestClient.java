package io.petstore.core.client;

import io.petstore.core.config.ConfigParser;
import io.petstore.utils.SpecificationFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestClient {


    public RestClient() {
        RestAssured.baseURI = ConfigParser.getValue("BaseUrl");
    }

    public Response get(String endpoint) {
        return given().spec(SpecificationFactory.log_Response_To_Allure()).when().get(endpoint);
    }

    public Response getWithQueryParam(String endpoint, String key, Object value) {
        return given().queryParam(key,value).spec(SpecificationFactory.log_Response_To_Allure()).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return given().contentType(ContentType.JSON).spec(SpecificationFactory.log_Response_To_Allure())
                .when().body(body).post(endpoint);
    }

    public Response put(String endpoint, Object body) {
        return given().contentType(ContentType.JSON).spec(SpecificationFactory.log_Response_To_Allure())
                .when().body(body).put(endpoint);
    }

    public Response patch(String endpoint, Object body) {
        return given().contentType(ContentType.JSON).spec(SpecificationFactory.log_Response_To_Allure())
                .when().body(body).patch(endpoint);
    }

    public Response delete(String endpoint) {
        return given().spec(SpecificationFactory.log_Response_To_Allure()).when().delete(endpoint);
    }
}
