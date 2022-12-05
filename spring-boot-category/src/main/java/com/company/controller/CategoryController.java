package com.company.controller;

import com.company.model.Category;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/apic")
public class CategoryController {
	Logger logger = LogManager.getLogger(CategoryController.class);
	@Autowired
	CategoryRepository repository;

	@PostMapping("/categories")
	public Category addCategory(@RequestParam(value = "name") String name) {
		logger.log(Level.INFO, "adding category " + name);
		System.out.println("adding category " + name);
		Category categoryToAdd = new Category(name);
		repository.save(categoryToAdd);
		return categoryToAdd;
	}

	@DeleteMapping("/categories")
	public String deleteCategory(@RequestParam(value = "id") String id) {
		repository.deleteById(UUID.fromString(id));
		return "success";
	}

	@GetMapping("/categories")
	public List<Category> getCategories() {
		List<Category> target = new ArrayList<>();
		repository.findAll().forEach(target::add);
		return target;
	}

	@GetMapping(path = "categories/{id}", produces = "application/json")
	public Category getCategoryWithId(@PathVariable("id") final String id) {
		return repository.findById(UUID.fromString(id)).get();
	}
}
