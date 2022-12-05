package com.bezkoder.spring.datajpa.repository;


import com.bezkoder.spring.datajpa.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository <Product, UUID>{
    public Product findProductByCategoryId(String categoryId);
    List<Product> findProductsByNameContainingOrPriceGreaterThanOrPriceLessThan(String name, double minPrice, double maxPrice);
}
