package com.iso.bidding.controller;

import com.iso.bidding.model.Product;
import com.iso.bidding.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/Products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String title) {
        return ResponseEntity.ok().body(productRepository.findAll());
    }

    @GetMapping("/Products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.isPresent() ? ResponseEntity.ok().body(productOptional.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/Products")
    public ResponseEntity<Product> createProduct(@RequestBody Product Product) {
    
    }

    @PutMapping("/Products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product Product) {

    }

    @DeleteMapping("/Products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id) {

    }

    @DeleteMapping("/Products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {

    }

    @GetMapping("/Products/published")
    public ResponseEntity<List<Product>> findByPublished() {

    }
}
