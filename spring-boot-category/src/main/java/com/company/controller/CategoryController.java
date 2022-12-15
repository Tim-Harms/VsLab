package com.company.controller;

import com.company.model.Category;
import com.company.repository.CategoryRepository;
import com.company.repository.CategoryTestRepository;
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

	@Autowired
	private CategoryTestRepository testRepository;

	@PostMapping("/categories")
	public Category addCategory(@RequestParam(value = "name") String name) {
		System.out.println("Arrived in AddCategory");
		logger.log(Level.INFO, "Logger: adding category " + name);
		System.out.println("System: adding category " + name);
		Category categoryToAdd = new Category(name);
		repository.save(categoryToAdd);
		return categoryToAdd;
	}

	@DeleteMapping("/categories")
	public String deleteCategory(@RequestParam(value = "id") String id) {
		//repository.deleteById(UUID.fromString(id));
		//System.out.println("Deleting Category: " + id);
		System.out.println("Arrived in deletecategory Method!");
		testRepository.deleteById(Integer.parseInt(id));
		return "success";
	}

	@GetMapping("/categories")
	public List<Category> getCategories() {
		System.out.println("Arrived in Get Categories Method!");
		List<Category> target = new ArrayList<>();
		repository.findAll().forEach(target::add);
		return target;
	}

	@GetMapping(path = "categories/{id}", produces = "application/json")
	public Category getCategoryWithId(@PathVariable("id") final String id) {
		return repository.findById(UUID.fromString(id)).get();
	}
}
