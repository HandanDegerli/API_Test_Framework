package io.petstore.domains.entity.store.storeResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableInventoryResponse.class)
@JsonDeserialize(as = ImmutableInventoryResponse.class)
public interface InventoryResponse {

    //not static
    int sold();
    int string();
    int pending();
    int available();
    @JsonProperty("not available")
    int notAvailable();
    int totvs1();

}
