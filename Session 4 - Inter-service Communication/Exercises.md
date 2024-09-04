# Exercises

1. Synchronous Communication
    * **Exercise 4.1:** Implement RESTful communication between Product Service and Order Service to fetch product details when placing an order
    
    ```json
    //GET /products/itemCode
    {
        "isActive": true,
        "description": "6 pcs. Pandesal"
    }
    ```
2. Asynchronous Communication
    * **Exercise 4.2:** Setup RabbitMQ and implement asynchronous communication for upgrading order status between Order Service and Inventory Service
    
    ```json
    //send to queue reserve_items - event format
    {"itemCode":"Pandesal","count":6}
    ```

    * **Exercise 4.3:** Share data on Order Service and Inventory Service when Product Service produces a black listed product event using RabbitMQ for event-driven architecture
  
    ```json
    //send to exchange product_catalog_exchange - event format
    //inventory listens at inventory_product_catalog_queue
    //order listens at order_product_catalog_queue
    {"productCode":"10029"}
    ```