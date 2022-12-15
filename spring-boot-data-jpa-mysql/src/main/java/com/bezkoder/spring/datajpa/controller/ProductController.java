package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Product;
import com.bezkoder.spring.datajpa.repository.ProductRepository;
import com.bezkoder.spring.datajpa.repository.ProductTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductRepository repository;

	@Autowired
	ProductTestRepository testRepository;

	@PostMapping("/products")
	public Product addProduct(@RequestParam(value = "name") String name,
									@RequestParam(value = "price") String price,
									@RequestParam(value = "categoryId") String categoryId,
									@RequestParam(value = "details") String details) {
		Product productToAdd = new Product(name, Double.parseDouble(price), categoryId, details);
		repository.save(productToAdd);
		System.out.println("Hello");
		return productToAdd;
	}

	@DeleteMapping("/products")
	public String deleteProduct(@RequestParam(value = "id") String id) {
		//repository.deleteById(UUID.fromString(id));

		System.out.println("Arrived in deleteproduct Method!");
		testRepository.deleteById(Integer.parseInt(id));
		return "success";
	}

	@GetMapping("/products")
	public List<Product> getProducts(@RequestParam(value = "searchQuery", required = false) String searchQuery,
									 @RequestParam(value = "minPrice", required = false) String minPrice,
									 @RequestParam(value = "maxPrice", required = false) String maxPrice) {
		if(searchQuery == null && minPrice == null && maxPrice == null) {
			List<Product> target = new ArrayList<>();
			repository.findAll().forEach(target::add);
			return target;
		} else {
			return repository.findProductsByNameContainingAndPriceGreaterThanAndPriceLessThan(searchQuery, Double.parseDouble(minPrice), Double.parseDouble(maxPrice));
		}
	}

	@GetMapping(path = "products/{id}", produces = "application/json")
	public Product getProductWithId(@PathVariable("id") final String id) {
		return repository.findById(UUID.fromString(id)).get();
	}
}
