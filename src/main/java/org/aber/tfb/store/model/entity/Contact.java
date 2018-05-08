package org.aber.tfb.store.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CONTACTS")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID")
    private Long id;

    @Column(name = "CONTACT_NAME", nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    @JoinTable(name = "CONTACT_PRODUCT",
            joinColumns = @JoinColumn(name = "REF_CONTACT_ID", referencedColumnName = "CONTACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "REF_APPLICATION_ID", referencedColumnName = "APPLICATION_ID"))
    private Set<Product> products = new HashSet<>();

}
