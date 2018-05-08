package org.aber.tfb.store.services;

import com.google.common.collect.ImmutableList;
import org.aber.tfb.store.model.business.Order;
import org.aber.tfb.store.model.entity.Product;
import org.aber.tfb.store.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class StoreServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetLastOrderForContact_when_not_found() {
        long givenContactId = 1111L;

        when(productRepository.findAll(any(Specification.class))).thenReturn(emptyList());

        Optional<Order> lastOrder = storeService.getLastOrderFor(givenContactId);

        assertFalse(lastOrder.isPresent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetLastOrderForContact_when_multiple_found() {
        long givenContactId = 1111L;

        LocalDateTime now = LocalDate.now().atTime(LocalTime.now());
        Product expectedProductToReturn = aProduct(now);

        when(productRepository.findAll(any(Specification.class))).thenReturn(ImmutableList.of(aProduct(now.minusDays(2)), expectedProductToReturn));

        Optional<Order> lastOrder = storeService.getLastOrderFor(givenContactId);

        assertEquals(lastOrder.get().getApplicationId(), expectedProductToReturn.getId());
    }

    private Product aProduct(LocalDateTime date) {
        Product aProduct = new Product();

        aProduct.setId(Long.valueOf(randomNumeric(4)));
        aProduct.setDateCreated(date);

        return aProduct;
    }
}