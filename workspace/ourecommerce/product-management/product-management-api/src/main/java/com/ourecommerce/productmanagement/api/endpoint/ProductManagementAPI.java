package com.ourecommerce.productmanagement.api.endpoint;

import com.ourecommerce.productmanagement.api.ProductDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


public interface ProductManagementAPI{
    @GetMapping("/{productCode}")
    ResponseEntity<ProductDetailsResponse> getProduct(@PathVariable("productCode") String productCode);
}
