package com.gb.market.core.converters;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.core.entities.Product;

public class ProductConverter {

    public static ProductDTO convertFromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}
