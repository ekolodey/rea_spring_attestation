package com.ekolodey.spring_attestation.search;

import java.util.Set;


public class ProductSearchParam {
    private String name;
    private Float priceFrom;
    private Float priceTo;
    private Set<Integer> categories;
    private String sortOrder;

    public ProductSearchParam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Float priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Float getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Float priceTo) {
        this.priceTo = priceTo;
    }

    public Set<Integer> getCategories() {
        return categories;
    }

    public void setCategories(Set<Integer> categories) {
        this.categories = categories;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
