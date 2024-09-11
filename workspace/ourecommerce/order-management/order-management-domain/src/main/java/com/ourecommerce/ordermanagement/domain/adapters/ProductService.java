package com.ourecommerce.ordermanagement.domain.adapters;

import com.ourecommerce.ordermanagement.domain.entity.Product;
import reactor.core.publisher.Mono;

public interface ProductService{
    Mono<Product> getProductWithCode(String productId);
}
