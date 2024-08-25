# Structural Patterns

Structural design patterns explain how to assemble objects and classes into larger structures, while keeping these structures flexible and efficient. In other words, these patterns describe ways to compose objects to realize new functionality. The added
flexibility of object composition comes from the ability to change the composition
at run-time, which is impossible with static class composition.

## Adapter

Convert the interface of a class into another interface that a client expects. Adapter allows classes to work together that couldn’t otherwise because of incompatible interfaces.

**When to Use**

* When you want to use an existing class, and its interface does not match the one you need.
* When you need to create a reusable class that cooperates with unrelated or unforeseen classes, that is, classes that don’t necessarily have compatible interfaces.
* When you want to simplify a complex interface for easier usage by a client.
  
**How to Implement**

1. Identify the interface that is expected by the client.
2. Create an adapter class that implements the client’s expected interface.
3. In the adapter class, include a reference to the adaptee object.
4. Implement the methods in the adapter class to delegate requests to the adaptee.

**Sample Implementation**

```java
//Adaptee class - from a 3rd party library
package com.axios.merchandies;

class AxiosMechandiseItem {
    private String productName;
    private double cost;
    private String details;

    public AxiosMechandiseItem(String productName, double cost, String details) {
        this.productName = productName;
        this.cost = cost;
        this.details = details;
    }

    public String fetchProductName() {
        return productName;
    }

    public double fetchCost() {
        return cost;
    }

    public String fetchDetails() {
        return details;
    }
}
```

```java
//Another adaptee class from a 3rd party library
package com.axg.medical;

class AXGMedicalItem {
    private String itemCode;
    private double price;
    private String itemDescription;

    public AXGMedicalItem(String itemCode, double price, String itemDescription) {
        this.itemCode = itemCode;
        this.price = price;
        this.itemDescription = itemDescription;
    }

    public String retrieveItemCode() {
        return this.itemCode;
    }

    public double retrievePrice() {
        return this.price;
    }

    public String retrieveDescription() {
        return this.itemDescription;
    }
}
```

```java
package com.our.ecommerce;

//Target interface
interface OurECommerceItem {
    String getItemName();
    double getPrice();
    String getDescription();
}


//Adapter for the AxiosMerchandiseItem
class AxiosMechandiseItemAdapter implements OurECommerceItem {
    private AxiosMechandiseItem axiosItem;

    public AxiosMechandiseItemAdapter(AxiosMechandiseItem axiosItem) {
        this.axiosItem = axiosItem;
    }

    @Override
    public String getItemName() {
        return axiosItem.fetchProductName();
    }

    @Override
    public double getPrice() {
        return axiosItem.fetchCost();
    }

    @Override
    public String getDescription() {
        return axiosItem.fetchDetails();
    }
}

//Adapter for the AXGMedicalItem
class AXGMedicalItemAdapter implements OurECommerceItem {
    private AXGMedicalItem axgItem;

    public AXGMedicalItemAdapter(AXGMedicalItem axgItem) {
        this.axgItem = axgItem;
    }

    @Override
    public String getItemName() {
        return axgItem.retrieveItemCode();
    }

    @Override
    public double getPrice() {
        return axgItem.retrievePrice();
    }

    @Override
    public String getDescription() {
        return axgItem.retrieveDescription();
    }
}

//Direct implementation of the interface - For in house out of db supported type
class DirectItem implements OurECommerceItem {
    private String itemName;
    private double price;
    private String description;

    public DirectItem(String itemName, double price, String description) {
        this.itemName = itemName;
        this.price = price;
        this.description = description;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

class ItemCatalogService {
    public void processItem(OurECommerceItem item) {
        System.out.println("Processing item: " + item.getItemName());
        System.out.println("Price: $" + item.getPrice());
        System.out.println("Description: " + item.getDescription());
    }
}

//Client - Adapts AxiosMerchandiseItem to target interface
class AxiosMechandiseEventHandler {
    private ItemCatalogService catalogService;

    public AxiosMechandiseEventHandler(ItemCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void handleEvent(AxiosMechandiseItem axiosItem) {
        OurECommerceItem adaptedItem = new AxiosMechandiseItemAdapter(axiosItem);
        catalogService.processItem(adaptedItem);
    }
}

//Client - Adapts AXGMedicalItem to target interface
class AXGMedicalEventHandler {
    private ItemCatalogService catalogService;

    public AXGMedicalEventHandler(ItemCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void handleEvent(AXGMedicalItem axgItem) {
        OurECommerceItem adaptedItem = new AXGMedicalItemAdapter(axgItem);
        catalogService.processItem(adaptedItem);
    }
}

//Showcase an event target interface is outright supported (Compatible)
class DirectItemEventHandler {
    private ItemCatalogService catalogService;

    public DirectItemEventHandler(ItemCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void handleEvent(DirectItem item) {
        catalogService.processItem(item);
    }
}

public class AdapterDemo {
    public static void main(String[] args) {
        // Create the service - Understands only the target interface
        ItemCatalogService catalogService = new ItemCatalogService();

        // Adapter clients
        AxiosMechandiseEventHandler axiosHandler = new AxiosMechandiseEventHandler(catalogService);
        AXGMedicalEventHandler axgHandler = new AXGMedicalEventHandler(catalogService);
        DirectItemEventHandler directItemHandler = new DirectItemEventHandler(catalogService);

        // Incompatible interface (Adaptee)
        AxiosMechandiseItem axiosItem = new AxiosMechandiseItem("Axios Widget", 25.50, "High-quality widget from Axios");
        axiosHandler.handleEvent(axiosItem); // Processing Axios item

        // Another incompatible interface (Adaptee)
        AXGMedicalItem axgItem = new AXGMedicalItem("AXG-101", 199.99, "Advanced medical device from AXG");
        axgHandler.handleEvent(axgItem); // Processing AXGMedical item

        // Directly implemented item event
        DirectItem directItem = new DirectItem("Direct Sale Item", 50.00, "Exclusive direct sale item");
        directItemHandler.handleEvent(directItem); // Processing DirectItem
    }
}

```

**Real World Examples**

* Music Players supporting different music file types (e.g. .mp4, .wav, .aac)
* Wrapper Classes in Java: The java.io.InputStreamReader acts as an adapter to convert InputStream (byte stream) into a Reader (character stream).

**Considerations**

* Object vs. Class Adapter: Object adapters use composition to adapt, while class adapters use inheritance. Object adapters are generally more flexible.
* Increased Complexity: Adapters can introduce complexity, particularly if multiple adapters are required. Evaluate if simplifying or redesigning the interfaces might be a better approach.

