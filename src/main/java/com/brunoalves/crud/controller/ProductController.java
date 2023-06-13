package com.brunoalves.crud.controller;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;
import com.brunoalves.crud.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        productDTO = service.saveProduct(productDTO);

        return productDTO;
    }

    @GetMapping
    public List<ProductDTO> findAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO findProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }


    @PutMapping("/update/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        return service.updateProduct(id, productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
}
