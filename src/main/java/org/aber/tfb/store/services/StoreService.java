package org.aber.tfb.store.services;

import org.aber.tfb.store.model.business.Order;
import org.aber.tfb.store.model.entity.Contact;
import org.aber.tfb.store.model.entity.Product;

import java.util.Optional;

public interface StoreService {

    Optional<Order> getLastOrderFor(Long contactId);

    Product addProduct(Product product);

    Contact addContact(Contact contact);
}
