package org.aber.tfb.store.model.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Order {

    @JsonProperty("CONTACT_ID")
    private Long contactId;
    @JsonProperty("APPLICATION_ID")
    private Long applicationId;
    @JsonProperty("DT_CREATED")
    private LocalDateTime timeCreated;
    @JsonProperty("PRODUCT_NAME")
    private String productName;

}
