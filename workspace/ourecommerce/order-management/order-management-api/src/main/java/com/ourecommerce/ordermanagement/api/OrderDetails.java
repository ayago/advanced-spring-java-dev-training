package com.ourecommerce.ordermanagement.api;

public class OrderDetails{
    

    private final String pandesal;
    private final String pinoyBread;
    
    public OrderDetails(String pandesal, String pinoyBread){
        this.pandesal = pandesal;
        this.pinoyBread = pinoyBread;
    }
    
    public String getPandesal(){
        return pandesal;
    }
    
    public String getPinoyBread(){
        return pinoyBread;
    }
}
