package com.ourecommerce.ordermanagement.app.clients;

import com.ourecommerce.ordermanagement.app.clients.loadbalancer.ProductManagementLoadBalancerConfiguration;
import com.ourecommerce.ordermanagement.app.data.Product;
import com.ourecommerce.ordermanagement.app.service.ProductService;
import com.ourecommerce.productmanagement.client.ProductManagementClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@LoadBalancerClient(name = "product-management", configuration = ProductManagementLoadBalancerConfiguration.class)
public class ProductServiceClient implements ProductService{
    
    private final ProductManagementClient productManagementClient;
    
    public ProductServiceClient(ProductManagementClient productManagementClient){
        this.productManagementClient = productManagementClient;
    }
    
    @Override
    public Mono<Product> getProductWithCode(String code){
        return Mono.fromSupplier(() -> productManagementClient.getProduct(code).getBody())
            .map(pmProduct -> new Product(pmProduct.getProductCode()));
    }
}
