package com.gb.market.core.converters;

import com.gb.market.api.dtos.ProductDTO;
import com.gb.market.core.dtos.ProductFullInfo;
import com.gb.market.core.dtos.ViewDTO;
import com.gb.market.core.entities.Product;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ProductConverter {

    public static ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }

    public static ViewDTO convertToViewDTO(Page<Product> products) {
        ViewDTO viewDTO = new ViewDTO();
        viewDTO.setPages(new ArrayList<>());
        for (int i = 0; i < products.getTotalPages(); i++) {
            viewDTO.getPages().add(i);
        }
        viewDTO.setProductList(products.toList());
        return viewDTO;
    }

    public static ProductFullInfo convertToProductFullInfo(Product product) {
        ProductFullInfo productFullInfo = new ProductFullInfo();
        productFullInfo.setId(product.getId());
        productFullInfo.setTitle(product.getTitle());
        productFullInfo.setPrice(product.getPrice());
        productFullInfo.setDescription(product.getDescription());
        return productFullInfo;
    }
}
