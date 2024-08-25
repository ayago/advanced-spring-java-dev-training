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

## Composite

Compose objects into tree structures to represent part-whole hierarchies.
Composite lets clients treat individual objects and compositions of objects
uniformly.

**When to Use**

* When you need to represent a tree structure of objects.
* When clients need to treat leaf nodes and composite nodes uniformly.
* When you want to avoid conditional code when dealing with a hierarchy of objects.

**How to Implement**

1. Component Interface: Define a common interface for both leaf and composite objects.
2. Leaf Class: Implements the component interface and represents individual objects.
3. Composite Class: Implements the component interface and contains child components, which can be either leaves or other composites.
4. Client: Interacts with the component interface and can work with both individual objects and composites uniformly.

**Sample Implementation**

```java
import java.util.ArrayList;
import java.util.List;

// Component
interface Item {
    double getPrice();
}

// Leaf - Product
class Product implements Item {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

// Composite - Box
class Box implements Item {
    private List<Item> items = new ArrayList<>();
    private String description;

    public Box(String description) {
        this.description = description;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public double getPrice() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    @Override
    public String toString() {
        return description + " containing " + items.size() + " items (Total: $" + getPrice() + ")";
    }
}

// Client
public class ECommerceCompositeDemo {
    public static void main(String[] args) {
        // Creating individual products
        Item laptop = new Product("Laptop", 1000);
        Item phone = new Product("Phone", 500);
        Item charger = new Product("Charger", 50);
        Item headphones = new Product("Headphones", 100);

        // Creating a small box that contains products
        Box smallBox = new Box("Small Box");
        smallBox.addItem(phone);
        smallBox.addItem(charger);

        // Creating a nested box within the small box
        Box nestedBox = new Box("Nested Box");
        nestedBox.addItem(new Product("USB Cable", 10));
        nestedBox.addItem(new Product("Adapter", 20));
        smallBox.addItem(nestedBox);  // Add nested box to small box

        // Creating a large box that contains another box and a product
        Box largeBox = new Box("Large Box");
        largeBox.addItem(laptop);
        largeBox.addItem(smallBox);

        // Creating an order that contains products and boxes
        Box order = new Box("Order Box");
        order.addItem(largeBox);
        order.addItem(headphones);

        // Calculating the total price of the order
        System.out.println("Order Details:");
        System.out.println(order.toString());
        System.out.println("Total Order Price: $" + order.getPrice());
    }
}

```
   
**Real World Examples**

* File System Hierarchy: Folders can contain files or other folders, and both can be treated uniformly when performing operations like listing or searching.
* Graphics Editors: Objects like circles, lines, and rectangles can be grouped into larger objects, and the editor can treat them all as a single shape.
  
**Considerations**

* Overhead: The Composite pattern can introduce complexity, especially when managing a large number of components.
* Uniformity vs. Flexibility: While it provides a uniform interface, you might lose some flexibility since all components have to support the operations in the interface.

## Decorator

The Decorator pattern dynamically adds behavior or responsibilities to an object without altering its structure. It allows for flexible and scalable modifications by wrapping the original object in decorator classes.

**When to Use**

* When you need to add responsibilities to individual objects dynamically and transparently without affecting other objects.
* When extending behavior through inheritance is impractical, either because of too many possible extensions or because it might introduce unintended changes to all subclasses.
* When the extension of functionality through composition is preferred over inheritance.
  
**How to Implement**

1. Component Interface: Define an interface that both the concrete component and decorators will implement.
2. Concrete Component: Implement the component interface with a base object that can have responsibilities added to it.
3. Decorator Base Class: Create an abstract decorator class that implements the component interface and holds a reference to a component object.
4. Concrete Decorators: Implement concrete decorators that extend the decorator base class. These decorators can add or override behavior before/after delegating calls to the wrapped component.

**Sample Implementation**

Imagine a user experience team studying how inventory system admins use the current system:

```java
// Component Interface
public interface User {
    void performOperation();
}

// Concrete Component
public class AdminUser implements User {
    @Override
    public void performOperation() {
        authenticate();
        System.out.println("Performing administrative operation.");
    }

    private void authenticate() {
        System.out.println("User authenticated successfully.");
    }
}

// Decorator Base Class
public abstract class UserDecorator implements User {
    protected User decoratedUser;

    public UserDecorator(User user) {
        this.decoratedUser = user;
    }

    @Override
    public void performOperation() {
        decoratedUser.performOperation();
    }
}

// Concrete Decorator: Logging for Beta Users
public class BetaUserDecorator extends UserDecorator {

    public BetaUserDecorator(User user) {
        super(user);
    }

    @Override
    public void performOperation() {
        super.performOperation();
        logOperation();
    }

    private void logOperation() {
        System.out.println("Operation logged for Beta User.");
    }
}

// Usage
public class InventorySystem {
    public static void main(String[] args) {
        // Regular Admin User
        User adminUser = new AdminUser();
        adminUser.performOperation();
        System.out.println();

        // Beta Admin User with logging
        User betaAdminUser = new BetaUserDecorator(new AdminUser());
        betaAdminUser.performOperation();
    }
}

```

**Real World Example**

* Java I/O Streams: The java.io package uses the Decorator pattern extensively. For instance, BufferedInputStream and DataInputStream are decorators that add additional behavior to an InputStream.

**Considerations**

* Overuse: Excessive use of decorators can make the system overly complex, with many small classes, leading to difficulty in understanding and maintaining the code.
* Identity Issues: Since decorators can change an object's interface, it can be difficult to determine the actual type of the wrapped object.
* Performance Impact: Each decorator adds a layer of abstraction, which might introduce some performance overhead.