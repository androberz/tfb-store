package org.aber.tfb.store.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLICATION_ID")
    private Long id;

    @Column(name = "PRODUCT_NAME", nullable = false, unique = true)
    private String productName;

    @Column(name = "DT_CREATED")
    private LocalDateTime dateCreated;

    @ManyToMany(targetEntity = Contact.class,
            mappedBy = "products",
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @OrderBy("dateCreated ASC")
    private Set<Contact> contacts = new HashSet<>();

}
