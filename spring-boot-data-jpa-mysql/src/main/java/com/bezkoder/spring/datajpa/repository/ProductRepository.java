package com.bezkoder.spring.datajpa.repository;


import com.bezkoder.spring.datajpa.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository <Product, UUID>{
    public Product findProductByCategoryId(String categoryId);
    List<Product> findProductsByNameContainingOrPriceGreaterThanOrPriceLessThan(String name, double minPrice, double maxPrice);
    //@Query("SELECT p FROM Product p WHERE (:name is null or p.name = :name) and (:minPrice is null or p.price >= :minPrice) and (:maxPrice is null or p.price <= :maxPrice)")
    //List<Product> findProductsByNameContainingAndPriceGreaterThanAndPriceLessThan(@Param("name") String name, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    List<Product> findProductsByNameContainingAndPriceGreaterThanAndPriceLessThan(String name, double minPrice, double maxPrice);
}
