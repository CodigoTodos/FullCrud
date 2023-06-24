package com.brunoalves.crud.mapper;

import com.brunoalves.crud.dto.ProductDTO;
import com.brunoalves.crud.entity.Product;

public class ProductMapper {

    private ProductMapper(){}

    public static ProductDTO mapToProductDTO(Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public static Product mapToProduct(ProductDTO productDTO){
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getQuantity(),
                productDTO.getPrice()
        );
    }


}
