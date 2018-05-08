package org.aber.tfb.store.services;

import org.aber.tfb.store.model.business.Order;
import org.aber.tfb.store.model.entity.Contact;
import org.aber.tfb.store.model.entity.Product;
import org.aber.tfb.store.repository.ContactRepository;
import org.aber.tfb.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.aber.tfb.store.specs.ProductSpecifications.productIsForContact;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class StoreServiceImpl implements StoreService {

    private final ProductRepository productRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public StoreServiceImpl(ProductRepository productRepository, ContactRepository contactRepository) {
        this.productRepository = productRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Optional<Order> getLastOrderFor(Long contactId) {
        List<Product> productsForContact = productRepository.findAll(where(productIsForContact(contactId)));

        Optional<Product> lastCreatedProduct = productsForContact.stream().max(comparing(Product::getDateCreated));

        return lastCreatedProduct.map(product -> new Order(contactId, product.getId(), product.getDateCreated(), product.getProductName()));
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }
}
