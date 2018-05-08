package org.aber.tfb.store.services.rest;

import org.aber.tfb.store.model.business.Order;
import org.aber.tfb.store.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController()
@RequestMapping("/tbstore")
public class RestStoreService {

    private final StoreService storeService;

    @Autowired
    public RestStoreService(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(value = "/contacts/{contactId}/lastOrder", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Order> getLatestOrderFor(@PathVariable(name = "contactId") @NotNull Long contactId) {
        Optional<Order> lastOrderForContact = storeService.getLastOrderFor(contactId);

        return lastOrderForContact.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
