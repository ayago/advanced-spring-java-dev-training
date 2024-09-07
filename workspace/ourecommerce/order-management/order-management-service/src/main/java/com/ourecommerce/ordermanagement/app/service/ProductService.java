package com.ourecommerce.ordermanagement.app.service;

import com.ourecommerce.ordermanagement.app.data.Product;
import reactor.core.publisher.Mono;

public interface ProductService{
    Mono<Product> getProductWithCode(String code);
}
