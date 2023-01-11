package io.petstore.domains.entity.store.storeResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableDeleteOrderResponse.class)
@JsonDeserialize(as = ImmutableDeleteOrderResponse.class)
public interface DeleteOrderResponse {
    int code();
    String type();
    String message();
}
