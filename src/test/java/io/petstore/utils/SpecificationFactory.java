package io.petstore.utils;

import io.petstore.core.config.ConfigParser;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Objects;

public class SpecificationFactory {

    public static synchronized RequestSpecification log_Response_To_Allure() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        RequestSpecification requestSpecification;

        if(Objects.requireNonNull(ConfigParser.getValue("Log")).equalsIgnoreCase("ENABLED")){
            requestSpecBuilder.addFilter(new AllureRestAssured());
        }

        requestSpecification = requestSpecBuilder.build();
        return requestSpecification;
    }
}
