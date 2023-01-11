package io.petstore.domains.services;

import io.petstore.core.client.RestClient;
import io.petstore.domains.entity.store.storeRequest.OrderRequest;
import io.petstore.domains.entity.store.storeResponse.DeleteOrderResponse;
import io.petstore.domains.entity.store.storeResponse.InventoryResponse;
import io.petstore.domains.entity.store.storeResponse.OrderResponse;

public class StoreServices extends RestClient {
    private final String endpoint;

    public StoreServices() {
        endpoint = "store";
    }

    public int getOrderByOrderIdAndGetStatusCode(int orderId){
        return get(endpoint + "/order/" + orderId).then().extract().response().getStatusCode();
    }

    public OrderResponse getOrderByOrderId(int orderId){
        return get(endpoint + "/order/" + orderId).then().extract().response().body().as(OrderResponse.class);
    }

    public int postOrderAndGetStatusCode(OrderRequest order){
        return post(endpoint + "/order", order).then().extract().response().getStatusCode();
    }

    public OrderResponse postOrder(OrderRequest order){
        return post(endpoint + "/order", order).then().extract().response().as(OrderResponse.class);
    }

    public int deleteOrderByOrderIdAndGetStatusCode(int orderId){
        return delete(endpoint + "/order/" + orderId).then().extract().response().getStatusCode();
    }

    public DeleteOrderResponse deleteOrder(int orderId){
        return delete(endpoint + "/order/" + orderId).then().extract().response().body().as(DeleteOrderResponse.class);
    }


    public int getStoreInventoryAndGetStatusCode(){
        return get(endpoint + "/inventory").then().extract().response().getStatusCode();
    }

    public InventoryResponse getStoreInventory(){
        return get(endpoint + "/inventory").then().extract().response().body().as(InventoryResponse.class);
    }



}
