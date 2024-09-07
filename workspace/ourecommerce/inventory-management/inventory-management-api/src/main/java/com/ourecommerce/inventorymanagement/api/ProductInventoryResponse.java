package com.ourecommerce.inventorymanagement.api;

public class ProductInventoryResponse{
    private String productCode;
    private Integer availableStock;
    private Integer reservedStock;
    
    public String getProductCode(){
        return productCode;
    }
    
    public ProductInventoryResponse setProductCode(String productCode){
        this.productCode = productCode;
        return this;
    }
    
    public Integer getAvailableStock(){
        return availableStock;
    }
    
    public ProductInventoryResponse setAvailableStock(Integer availableStock){
        this.availableStock = availableStock;
        return this;
    }
    
    public Integer getReservedStock(){
        return reservedStock;
    }
    
    public ProductInventoryResponse setReservedStock(Integer reservedStock){
        this.reservedStock = reservedStock;
        return this;
    }
}
