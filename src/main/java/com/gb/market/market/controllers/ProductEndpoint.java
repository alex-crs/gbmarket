package com.gb.market.market.controllers;

import com.gb.market.market.services.ProductService;
import com.gb.market.market.soap.product.GetAllProductsRequest;
import com.gb.market.market.soap.product.GetAllProductsResponse;
import com.gb.market.market.soap.product.GetProductByIdRequest;
import com.gb.market.market.soap.product.GetProductByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.gb.com/market/market/product";
    private final ProductService productService;

    //Выгружает товар по ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductByIdResponse(@RequestPayload GetProductByIdRequest request){
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.getSoapProductById(request.getId()));
        return response;
    }

    //Выгружает список товаров
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProductsResponse(@RequestPayload GetAllProductsRequest request){
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllSoapProduct().forEach(response.getProducts()::add);
        return response;
    }
}
