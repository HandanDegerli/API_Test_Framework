package io.petstore.domains.entity.store.storeResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableOrderResponse.class)
@JsonDeserialize(as = ImmutableOrderResponse.class)
public interface OrderResponse {
    int id();
    int petId();
    int quantity();
    String shipDate();
    String status();
    boolean complete();
}
