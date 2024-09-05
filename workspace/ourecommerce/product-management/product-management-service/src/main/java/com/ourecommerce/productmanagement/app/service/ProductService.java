package com.ourecommerce.productmanagement.app.service;

import com.ourecommerce.productmanagement.api.AddNewProductResponse;
import com.ourecommerce.productmanagement.api.ProductBlacklistedEvent;
import com.ourecommerce.productmanagement.api.ProductDetailsRequest;
import com.ourecommerce.productmanagement.app.document.Product;
import com.ourecommerce.productmanagement.app.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    
    public ProductService(RabbitTemplate rabbitTemplate, ProductRepository productRepository){
        this.rabbitTemplate = rabbitTemplate;
        this.productRepository = productRepository;
    }
    
    public void blacklistProduct(String productCode){
        rabbitTemplate.convertAndSend("product_catalog_exchange", "", new ProductBlacklistedEvent(productCode));
    }
    
    public AddNewProductResponse registerNewProduct(ProductDetailsRequest request){
        Product newProduct = resolveProductFrom(request);
        productRepository.save(newProduct);
        return new AddNewProductResponse(newProduct.getId());
    }
    
    private Product resolveProductFrom(ProductDetailsRequest request){
        return new Product()
            .setDescription(request.getDescription())
            .setName(request.getName())
            .setStatus("ACTIVE");
    }
}
