package org.aber.tfb.store.services;

import org.aber.tfb.store.model.entity.Contact;
import org.aber.tfb.store.model.entity.Product;
import org.aber.tfb.store.repository.ContactRepository;
import org.aber.tfb.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
   Uncomment to create init data by java code instead of data.sql.
   In this case data.sql should be renamed to something like data_disabled.sql
*/
//@Component
public class TestDataCreator implements ApplicationRunner {

    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TestDataCreator(ContactRepository contactRepository, ProductRepository productRepository) {
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
    }

    public void createInitData() {
        Contact contact1 = new Contact();
        contact1.setName("contact1");
        Contact contact2 = new Contact();
        contact2.setName("contact2");
        Contact contact3 = new Contact();
        contact3.setName("contact3");

        LocalDateTime now = LocalDate.now().atTime(LocalTime.now());
        Product product1 = new Product();
        product1.setProductName("product1");
        product1.setDateCreated(now);
        Product product2 = new Product();
        product2.setProductName("product2");
        product2.setDateCreated(now.minusDays(2));
        Product product3 = new Product();
        product3.setProductName("product3");
        product3.setDateCreated(now.minusDays(5));

        contact1.getProducts().add(product1);
        contact1.getProducts().add(product2);
        contact2.getProducts().add(product2);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        contactRepository.save(contact1);
        contactRepository.save(contact2);
        contactRepository.save(contact3);
    }

    @Override
    public void run(ApplicationArguments args) {
        createInitData();
    }
}
