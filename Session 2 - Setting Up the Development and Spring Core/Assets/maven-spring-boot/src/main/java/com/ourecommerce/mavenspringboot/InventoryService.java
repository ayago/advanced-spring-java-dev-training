package com.ourecommerce.mavenspringboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
public class InventoryService implements BeanNameAware, InitializingBean {
    private String beanName;
    
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean name is set to: " + beanName);
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("InventoryService @PostConstruct: Bean is going through post-construction initialization.");
    }
    
    @Override
    public void afterPropertiesSet() {
        System.out.println("InventoryService afterPropertiesSet: Bean properties have been set.");
    }
    
    public void customInit() {
        System.out.println("InventoryService customInit: Performing custom initialization logic.");
    }
    
    public void performInventoryCheck() {
        System.out.println("Performing inventory check...");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("InventoryService @PreDestroy: Bean is about to be destroyed.");
    }
    
    public void customDestroy() {
        System.out.println("InventoryService customDestroy: Performing custom cleanup logic.");
    }
}