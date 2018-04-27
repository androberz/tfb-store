package org.aber.tfb.store.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue
    @Column(name = "CONTACT_ID")
    private Long id;

    private Set<Product> products;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_product",
            joinColumns = @JoinColumn(name = "REF_CONTACT_ID", referencedColumnName = "CONTACT_ID"),
            inverseJoinColumns = @JoinColumn(name = "REF_PRODUCT_ID", referencedColumnName = "PRODUCT_ID"))
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
