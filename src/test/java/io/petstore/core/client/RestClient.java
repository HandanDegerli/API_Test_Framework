package io.petstore.core.client;

import io.petstore.core.config.ConfigParser;
import io.petstore.utils.SpecificationFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;


import java.io.File;

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

    public Response getWithQueryParams(String endpoint, String key, Object value, String key2, String val) {
        return given().queryParam(key,value).queryParam(key2, val).spec(SpecificationFactory.log_Response_To_Allure()).when().get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return given().contentType(ContentType.JSON).spec(SpecificationFactory.log_Response_To_Allure())
                .when().body(body).post(endpoint);
    }
    public Response postDifferentContentType(String endpoint, String nameValue, String statusValue)  {
        return given().contentType(ContentType.URLENC).spec(SpecificationFactory.log_Response_To_Allure())
                .when().formParam("name", nameValue).formParam("status", statusValue).post(endpoint);
    }

    public Response postDifferentContentTypeAndFile(String endpoint, String additionalMetadata, String filePath)  {
        return given().contentType(ContentType.MULTIPART).spec(SpecificationFactory.log_Response_To_Allure())
                .when().formParam("additionalMetadata", additionalMetadata).multiPart(new File(filePath)).post(endpoint);
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

    public Response deleteWithAuth(String endpoint, Header header){
        return given().contentType(ContentType.JSON).spec(SpecificationFactory.log_Response_To_Allure())
                .when().header(header).delete(endpoint);
    }
}
