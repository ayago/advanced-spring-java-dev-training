package com.ourecommerce.productmanagement.app.repository;

import com.ourecommerce.productmanagement.app.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>{
}
