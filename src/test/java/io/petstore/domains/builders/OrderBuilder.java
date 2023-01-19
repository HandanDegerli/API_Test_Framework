package io.petstore.domains.builders;

import io.petstore.domains.entity.store.storeRequest.ImmutableOrderRequest;
import io.petstore.domains.entity.store.storeRequest.OrderRequest;
import io.petstore.domains.entity.store.storeResponse.OrderResponse;
import io.petstore.domains.services.PetServices;
import io.petstore.domains.services.StoreServices;
import io.petstore.domains.services.UsersServices;
import io.petstore.steps.AccessTest;
import org.apache.log4j.Logger;

public class OrderBuilder {
    private final StoreServices storeServices;
    private final Logger logger;

    public OrderBuilder(){
        storeServices = new StoreServices();
        logger = Logger.getLogger(AccessTest.class);
    }

    public OrderResponse createOrder(){
        OrderRequest order = ImmutableOrderRequest.builder()
                .id(1)
                .petId(1)
                .quantity(3)
                .shipDate("2023-01-12T14:15:02.414+0000")
                .status("approved")
                .complete(true)
                .build();

        logger.info("Creating an ORDER");
        return storeServices.postOrder(order);
    }
}
