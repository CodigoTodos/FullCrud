package com.brunoalves.crud.mapper;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;

public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        );
        return productDTO;
    }

    public static Product mapToProduct(ProductDTO productDTO){
        Product product = new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getQuantity(),
                productDTO.getPrice()
        );
        return product;
    }


}
