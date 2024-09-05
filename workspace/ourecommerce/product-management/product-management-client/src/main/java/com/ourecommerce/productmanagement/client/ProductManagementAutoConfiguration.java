package com.ourecommerce.productmanagement.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ourecommerce.productmanagement.client")
public class ProductManagementAutoConfiguration{
}
