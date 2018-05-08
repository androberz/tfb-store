package org.aber.tfb.store.services.rest;

import com.google.common.collect.ImmutableSet;
import org.aber.tfb.store.TfbStoreApplication;
import org.aber.tfb.store.model.business.Order;
import org.aber.tfb.store.model.entity.Contact;
import org.aber.tfb.store.model.entity.Product;
import org.aber.tfb.store.repository.ContactRepository;
import org.aber.tfb.store.repository.ProductRepository;
import org.aber.tfb.store.services.StoreService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TfbStoreApplication.class)
@WebAppConfiguration
public class RestStoreServiceTest {

    private static final String GET_CONTACT_LAST_ORDER_URL = "/tbstore/contacts/{contactId}/lastOrder";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Jackson2ObjectMapperBuilder objectMapperBuilder;

    private Contact contactWithProducts;
    private Contact contactWithoutProducts;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();

        initTestData();
    }

    @After
    public void tearDown() {
        contactRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testGettingLastOrderForContact_contact_not_found() throws Exception {
        String contactIdNotFound = "99991";

        mockMvc.perform(MockMvcRequestBuilders.get(GET_CONTACT_LAST_ORDER_URL, contactIdNotFound))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGettingLastOrderForContact_contact_found_product_not_found() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GET_CONTACT_LAST_ORDER_URL, contactWithoutProducts.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGettingLastOrderForContact_contact_found_product_found_json() throws Exception {
        Product lastProduct = contactWithProducts.getProducts().stream().filter(product -> product.getProductName().equals("TestProduct_1")).findFirst().get();
        Long contactId = contactWithProducts.getId();

        mockMvc.perform(MockMvcRequestBuilders.get(GET_CONTACT_LAST_ORDER_URL, contactId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapperBuilder.build().writeValueAsString(Order.builder()
                        .contactId(contactId)
                        .applicationId(lastProduct.getId())
                        .productName(lastProduct.getProductName())
                        .timeCreated(lastProduct.getDateCreated())
                        .build())));
    }

    @Test
    public void testGettingLastOrderForContact_contact_found_product_found_xml() throws Exception {
        Product lastProduct = contactWithProducts.getProducts().stream().filter(product -> product.getProductName().equals("TestProduct_1")).findFirst().get();
        Long contactId = contactWithProducts.getId();

        mockMvc.perform(MockMvcRequestBuilders.get(GET_CONTACT_LAST_ORDER_URL, contactId)
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().xml(objectMapperBuilder.createXmlMapper(true).build().writeValueAsString(Order.builder()
                        .contactId(contactId)
                        .applicationId(lastProduct.getId())
                        .productName(lastProduct.getProductName())
                        .timeCreated(lastProduct.getDateCreated())
                        .build())));
    }

    private void initTestData() {
        contactRepository.deleteAll();
        productRepository.deleteAll();

        contactWithProducts = aContact("ContactWithProducts");
        contactWithoutProducts = aContact("ContactWithoutProducts");
        LocalDateTime now = LocalDateTime.now();
        Product product1 = aProduct("TestProduct_1", now);
        Product product2 = aProduct("TestProduct_2", now.minusDays(1));
        contactWithProducts.getProducts().addAll(ImmutableSet.of(product1, product2));

        storeService.addProduct(product1);
        storeService.addProduct(product2);
        storeService.addContact(contactWithProducts);
        storeService.addContact(contactWithoutProducts);
    }

    private Contact aContact(String contactName) {
        Contact aContact = new Contact();
        aContact.setName(contactName);

        return aContact;
    }

    private Product aProduct(String productName, LocalDateTime dateTime) {
        Product aProduct = new Product();
        aProduct.setProductName(productName);
        aProduct.setDateCreated(dateTime);

        return aProduct;
    }
}