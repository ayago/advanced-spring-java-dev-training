package com.ourecommerce.productmanagement.client;

import com.ourecommerce.productmanagement.api.endpoint.ProductManagementAPI;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-management", path = "/products")
public interface ProductManagementClient extends ProductManagementAPI{
}
