package com.ourecommerce.productmanagement.service;

import com.ourecommerce.productmanagement.api.OnboardRequest;
import com.ourecommerce.productmanagement.data.OperationResult;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    
    public OperationResult onboardProduct(OnboardRequest onboardRequest){
        System.out.println("Onboarding with request "+onboardRequest);
        return new OperationResult();
    }
}
