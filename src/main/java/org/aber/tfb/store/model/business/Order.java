package org.aber.tfb.store.model.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@XmlRootElement
@NoArgsConstructor
public class Order implements Serializable {

    @JsonProperty("CONTACT_ID")
    private Long contactId;
    @JsonProperty("APPLICATION_ID")
    private Long applicationId;
    @JsonProperty("DT_CREATED")
    private LocalDateTime timeCreated;
    @JsonProperty("PRODUCT_NAME")
    private String productName;

}
