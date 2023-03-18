package com.iso.bidding.controller;

import com.iso.bidding.model.Product;
import com.iso.bidding.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
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

        return productOptional.isPresent() ?
                new ResponseEntity<>(productOptional.get(), HttpStatus.FOUND) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/Products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product _product = productRepository.save(new Product(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.isPublished()
            ));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product _product = productOptional.get();
        _product.setId(product.getId());
        _product.setName(product.getName());
        _product.setDescription(product.getDescription());
        _product.setPublished(product.isPublished());

        return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
    }

    @DeleteMapping("/Products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
