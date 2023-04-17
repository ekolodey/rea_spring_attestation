package com.ekolodey.spring_attestation.search;

import com.ekolodey.spring_attestation.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductSearchSpecification implements org.springframework.data.jpa.domain.Specification<Product> {
    private final String name;
    private final Float priceFrom;
    private final Float priceTo;
    private final Set<Integer> categories;
    private final String sortOrder;

    public ProductSearchSpecification(ProductSearchParam param) {
        this.name = param.getName();
        this.priceFrom = param.getPriceFrom();
        this.priceTo = param.getPriceTo();
        this.categories =  param.getCategories();
        this.sortOrder = param.getSortOrder();
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if(name != null)
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("title")),
                                    "%" + name.toLowerCase()+ "%"
                            ),
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("description")),
                                    "%" + name.toLowerCase()+ "%"
                            )
                    )
            );

        if(priceFrom != null)
            predicates.add(criteriaBuilder.greaterThan(root.get("price"), priceFrom));

        if(priceTo != null)
            predicates.add(criteriaBuilder.lessThan(root.get("price"), priceTo));

        if(categories != null && !categories.isEmpty())
            predicates.add(root.get("category").get("id").in(categories));

        if(sortOrder != null && !sortOrder.isEmpty()){
            if(sortOrder.equals("asc"))
                query.orderBy(criteriaBuilder.asc(root.get("price")));
            if(sortOrder.equals("desc"))
                query.orderBy(criteriaBuilder.desc(root.get("price")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
