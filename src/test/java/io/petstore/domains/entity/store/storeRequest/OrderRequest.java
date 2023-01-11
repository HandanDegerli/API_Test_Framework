package io.petstore.domains.entity.store.storeRequest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableOrderRequest.class)
@JsonDeserialize(as = ImmutableOrderRequest.class)
public interface OrderRequest {

    int id();
    int petId();
    int quantity();
    String shipDate();
    String status();
    boolean complete();

}
