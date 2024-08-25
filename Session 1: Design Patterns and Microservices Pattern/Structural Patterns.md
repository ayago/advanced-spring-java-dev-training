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

## Bridge

Lets you split a large class or a set of closely related classes into two separate hierarchies—abstraction and implementation—which can be developed independently of each other.

**When to Use**

* When you want to avoid a permanent binding between an abstraction and its implementation.
* When both the abstraction and its implementation should be extensible by subclassing.
* When changes in the implementation of an abstraction should have no impact on clients.
* When you want to hide implementation details from clients.
  
**How to Implement**

1. Identify the Abstraction and Implementor: Separate the class's abstract part from its implementation.
2. Create the Abstraction Class: Define an interface or abstract class that holds a reference to an Implementor.
3. Create the Implementor Interface: Define the interface for the implementation classes.
4. Create Concrete Implementations: Create concrete classes that implement the Implementor interface.
5. Implement the Refined Abstraction: Create concrete subclasses of the Abstraction that use different implementations.

**Sample Implementation**

```java
// Implementor - PaymentMethod
interface PaymentMethod {
    void processPayment(double amount);
}

// Concrete Implementors - Payment Methods
class CreditCardPayment implements PaymentMethod {
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}

class PayPalPayment implements PaymentMethod {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}

// Implementor - ShippingOption
interface ShippingOption {
    void ship(String item);
}

// Concrete Implementors - Shipping Options
class StandardShipping implements ShippingOption {
    public void ship(String item) {
        System.out.println("Shipping " + item + " via Standard Shipping");
    }
}

class ExpressShipping implements ShippingOption {
    public void ship(String item) {
        System.out.println("Shipping " + item + " via Express Shipping");
    }
}

// Abstraction - CheckoutProcess
abstract class CheckoutProcess {
    protected PaymentMethod paymentMethod;
    protected ShippingOption shippingOption;

    protected CheckoutProcess(PaymentMethod paymentMethod, ShippingOption shippingOption) {
        this.paymentMethod = paymentMethod;
        this.shippingOption = shippingOption;
    }

    public abstract void checkout(String item, double amount);
}

// Refined Abstraction - ECommerceCheckout
class ECommerceCheckout extends CheckoutProcess {
    public ECommerceCheckout(PaymentMethod paymentMethod, ShippingOption shippingOption) {
        super(paymentMethod, shippingOption);
    }

    public void checkout(String item, double amount) {
        paymentMethod.processPayment(amount);
        shippingOption.ship(item);
    }
}

// Refined Abstraction - RemoteCheckout
class UserDetails {}

class RemoteCheckout extends CheckoutProcess {

    private final UserDetails userDetails;

    public RemoteCheckout(UserDetails userDetails, PaymentMethod paymentMethod, ShippingOption shippingOption) {
        super(paymentMethod, shippingOption);
        this.userDetails = userDetails;
    }

    public void checkout(String item, double amount) {
        validateInvitation();
        paymentMethod.processPayment(amount);
        shippingOption.ship(item);
    }

    private void validateInvitation(){
        System.out.println("Validation email invitation to user with details "+this.userDetails);
    }
}

// Client Code
public class BridgePatternECommerce {
    public static void main(String[] args) {
        // Choose payment method and shipping option dynamically
        PaymentMethod paymentMethod = new CreditCardPayment();
        ShippingOption shippingOption = new ExpressShipping();

        // Process ecommerce checkout
        CheckoutProcess checkout = new ECommerceCheckout(paymentMethod, shippingOption);
        checkout.checkout("Laptop", 1200.00);

        // Process remote checkout - Simulate the other refined abstraction
        UserDetails user = new UserDetails();
        CheckoutProcess remoteProcess = new RemoteCheckout(user, paymentMethod, shippingOption);
        remoteProcess.checkout("Laptop", 1200.00);
    }
}

```

**Real World Examples**

* Graphic Libraries: A graphical application where you might want to separate the logic of the graphical shapes (abstractions) from the rendering engines (implementations) to support multiple rendering systems (e.g., OpenGL, DirectX).
* Device Driver Software: An operating system might use the Bridge pattern to separate the generic device operations from specific hardware implementations, allowing the OS to support multiple types of devices without changing the device interface.

**Considerations**

* Scalability: This pattern makes it easy to scale the eCommerce system by adding new payment methods and shipping options without altering the checkout process logic.
* Flexibility: It offers flexibility in selecting or switching between different payment and shipping providers at runtime.
* Maintenance: Easier maintenance and updates since changes in one aspect (e.g., payment method) don’t require changes in another (e.g., shipping method).
* Overhead: While powerful, this pattern can introduce additional complexity, which might not be necessary if only a small number of fixed payment and shipping options are supported.
