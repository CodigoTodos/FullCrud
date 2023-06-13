package com.brunoalves.crud.service;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;

import java.util.List;

public interface ProductService {

    public ProductDTO saveProduct(ProductDTO productDTO);

    public List<ProductDTO> getProducts();

    public ProductDTO getProductById(Long id);

    public String deleteProduct(Long id);

    public ProductDTO updateProduct(Long productId,ProductDTO productDTO);
}
