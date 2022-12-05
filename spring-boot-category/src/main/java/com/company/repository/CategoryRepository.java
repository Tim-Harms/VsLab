package com.company.repository;


import com.company.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends CrudRepository <Category, UUID>{}
