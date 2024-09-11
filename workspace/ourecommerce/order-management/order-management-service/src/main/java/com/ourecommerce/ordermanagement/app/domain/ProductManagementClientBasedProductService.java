package com.ourecommerce.ordermanagement.app.domain;

import com.ourecommerce.ordermanagement.app.config.ProductManagementLoadBalancerConfiguration;
import com.ourecommerce.ordermanagement.domain.entity.Product;
import com.ourecommerce.ordermanagement.domain.adapters.ProductService;
import com.ourecommerce.productmanagement.api.ProductDetailsResponse;
import com.ourecommerce.productmanagement.client.ProductManagementClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@LoadBalancerClient(name = "product-management", configuration = ProductManagementLoadBalancerConfiguration.class)
public class ProductManagementClientBasedProductService implements ProductService{
    
    private final ProductManagementClient productManagementClient;
    
    public ProductManagementClientBasedProductService(ProductManagementClient productManagementClient){
        this.productManagementClient = productManagementClient;
    }
    
    @Override
    public Mono<Product> getProductWithCode(String code){
        return getResponseEntityMono(code)
            .flatMap(entity -> Mono.fromSupplier(entity::getBody))
            .map(pmProduct -> new Product(pmProduct.getProductCode()));
    }
    
    private Mono<ResponseEntity<ProductDetailsResponse>> getResponseEntityMono(String code){
        return Mono.fromCallable(() -> productManagementClient.getProduct(code))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
