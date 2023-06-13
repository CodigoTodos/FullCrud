package com.brunoalves.crud.service;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;
import com.brunoalves.crud.exceptions.ProductNotFoundException;
import com.brunoalves.crud.mapper.ProductMapper;
import com.brunoalves.crud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {

        Product product = ProductMapper.mapToProduct(productDTO);

        Product savedProduct = repository.save(product);

        ProductDTO savedProductDTO = ProductMapper.mapToProductDTO(savedProduct);

        return savedProductDTO;
    }

    public List<ProductDTO> getProducts() {

        List<Product> products = repository.findAll();

        return products.stream().map((product) -> ProductMapper.mapToProductDTO(product))
                .collect(Collectors.toList());

    }

    public ProductDTO getProductById(Long id) {

        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id "+ id + " could not be found."));

        return ProductMapper.mapToProductDTO(product);
    }

    public String deleteProduct(Long id) {

        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id "+ id + " could not be found."));
        repository.deleteById(id);
        return "Product Removed with id: " + id;
    }

    public ProductDTO updateProduct(Long productId,ProductDTO productDTO) {

        Product existingProduct = repository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id "+ productId + " could not be found."));
        existingProduct.setName(productDTO.getName());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setPrice(productDTO.getPrice());
        Product product = repository.save(existingProduct);
        return ProductMapper.mapToProductDTO(product);
    }

}
