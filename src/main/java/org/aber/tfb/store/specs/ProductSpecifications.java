package org.aber.tfb.store.specs;

import org.aber.tfb.store.model.entity.Contact;
import org.aber.tfb.store.model.entity.Contact_;
import org.aber.tfb.store.model.entity.Product;
import org.aber.tfb.store.model.entity.Product_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;

public class ProductSpecifications {

    public static Specification<Product> productIsForContact(Long contactId) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
            SetJoin<Product, Contact> productContactSetJoin = root.join(Product_.contacts, JoinType.LEFT);

            return criteriaBuilder.equal(productContactSetJoin.get(Contact_.id), contactId);
        };
    }
}
