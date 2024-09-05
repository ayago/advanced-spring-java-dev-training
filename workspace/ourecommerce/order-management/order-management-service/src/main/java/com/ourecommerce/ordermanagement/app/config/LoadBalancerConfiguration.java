package com.ourecommerce.ordermanagement.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadBalancerConfiguration {
    
    @Value("${load-balancers-for}")
    private List<String> serviceNames;
    
    @Bean
    public List<RoundRobinLoadBalancer> loadBalancers(LoadBalancerClientFactory loadBalancerClientFactory){
        return serviceNames.stream()
            .map(serviceName -> new RoundRobinLoadBalancer(loadBalancerClientFactory.getLazyProvider(serviceName, ServiceInstanceListSupplier.class), serviceName))
            .toList();
    }
}

