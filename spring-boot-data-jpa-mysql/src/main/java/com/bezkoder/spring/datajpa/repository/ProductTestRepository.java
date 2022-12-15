package com.bezkoder.spring.datajpa.repository;


import com.bezkoder.spring.datajpa.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductTestRepository extends CrudRepository <Product, Integer>{
    void deleteById(int id);
}
