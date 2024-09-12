package com.ourecommerce.ordermanagement.app.domain;

import com.ourecommerce.ordermanagement.app.config.ProductManagementLoadBalancerConfiguration;
import com.ourecommerce.ordermanagement.domain.entity.Product;
import com.ourecommerce.ordermanagement.domain.adapters.ProductService;
import com.ourecommerce.productmanagement.api.ProductDetailsResponse;
import com.ourecommerce.productmanagement.client.ProductManagementClient;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@LoadBalancerClient(name = "product-management", configuration = ProductManagementLoadBalancerConfiguration.class)
public class ProductManagementClientBasedProductService implements ProductService{
    
    private final ProductManagementClient productManagementClient;
    private final ReactiveCircuitBreaker remoteServiceCircuitBreaker;
    
    private static int counter = 0;
    
    public ProductManagementClientBasedProductService(
        ProductManagementClient productManagementClient,
        ReactiveCircuitBreakerFactory<?, ?> reactiveCircuitBreakerFactory
    ){
        this.productManagementClient = productManagementClient;
        this.remoteServiceCircuitBreaker = reactiveCircuitBreakerFactory.create("productServiceCircuitBreaker");
    }
    
    @Override
    public Mono<Product> getProductWithCode(String code){
        System.out.println("Retrieving product details for product with code: "+code);
        System.out.println("Call number: "+ ++counter);
        return remoteServiceCircuitBreaker.run(
            getProductFromPM(code),
            throwable -> {
                System.out.println("Server error: "+throwable.getMessage());
                return Mono.just(new Product("CACHED01111"));
            }
        );
    }
    
    private Mono<Product> getProductFromPM(String code){
        return getResponseEntityMono(code)
            .flatMap(entity -> Mono.fromSupplier(entity::getBody))
            .map(pmProduct -> new Product(pmProduct.getProductCode()));
    }
    
    private Mono<ResponseEntity<ProductDetailsResponse>> getResponseEntityMono(String code){
        return Mono.fromCallable(() -> productManagementClient.getProduct(code))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
