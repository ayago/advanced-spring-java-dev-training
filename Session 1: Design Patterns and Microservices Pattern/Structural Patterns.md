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
import java.time.LocalDateTime;

//Component Interface
interface User {
    void performOperation();
}

// Concrete Component
class AdminUser implements User {
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
abstract class UserDecorator implements User {
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
class BetaUserDecorator extends UserDecorator {
    
    private final OperationLogger logger;
    
    public BetaUserDecorator(User user, OperationLogger logger) {
        super(user);
        this.logger = logger;
    }
    
    @Override
    public void performOperation() {
        LogEntry entry = logger.createInfoEntry("Operation logged for Beta User.");
        super.performOperation();
        logger.commit(entry);
    }
}

class LogEntry {
    private final LocalDateTime start;
    private final String message;
    
    LogEntry(String message){
        this.message = message;
        this.start = LocalDateTime.now();
    }
    
    public LocalDateTime getStart(){
        return this.start;
    }
    
    public String getMessage(){
        return this.message;
    }
}

class OperationLogger {
    LogEntry createInfoEntry(String message){
        return new LogEntry(message);
    }
    
    void commit(LogEntry entry){
        System.out.printf("Start: %s, End: %s - %s", entry.getStart(), LocalDateTime.now(), entry.getMessage());
    }
}

// Usage
class InventorySystem {
    public static void main(String[] args) {
        // Regular Admin User
        User adminUser = new AdminUser();
        adminUser.performOperation();
        System.out.println();
        
        // Beta Admin User with logging
        OperationLogger logger = new OperationLogger();
        User betaAdminUser = new BetaUserDecorator(new AdminUser(), logger);
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

## Facade

The Facade pattern provides a simplified interface to a complex subsystem. It hides the complexities of the system and provides an easy-to-use interface for the client.

**When to Use**
* When you need to simplify interaction with a complex system.
* When you want to reduce dependencies between clients and the subsystem.
* When you want to provide a single entry point to a system or subsystem.

**How to Implement**

1. Identify the complex subsystem or set of operations that need to be simplified.
2. Design a Facade class that exposes a simple interface to the client.
3. Delegate requests from the Facade to appropriate subsystem classes.
4. The client interacts only with the Facade, without knowing the underlying complexities.

**Sample Implementation**

Scenario: A complex ECommerce settlement process involves multiple API calls to payment gateways, inventory systems, and shipping services. The Facade simplifies this process for the client.

```java
// Subsystem classes
class PaymentGateway {
    public void processPayment(String account, double amount) {
        System.out.println("Processing payment for account: " + account);
    }
}

class InventorySystem {
    public void updateInventory(String itemId, int quantity) {
        System.out.println("Updating inventory for item: " + itemId);
    }
}

class ShippingService {
    public void arrangeShipping(String address) {
        System.out.println("Arranging shipping to: " + address);
    }
}

// Facade class
class SettlementFacade {
    private PaymentGateway paymentGateway;
    private InventorySystem inventorySystem;
    private ShippingService shippingService;

    public SettlementFacade() {
        this.paymentGateway = new PaymentGateway();
        this.inventorySystem = new InventorySystem();
        this.shippingService = new ShippingService();
    }

    public void completeSettlement(String account, double amount, String itemId, int quantity, String address) {
        paymentGateway.processPayment(account, amount);
        inventorySystem.updateInventory(itemId, quantity);
        shippingService.arrangeShipping(address);
        System.out.println("Settlement process completed.");
    }
}

// Client code
public class ECommerceSystem {
    public static void main(String[] args) {
        SettlementFacade settlementFacade = new SettlementFacade();
        settlementFacade.completeSettlement("12345", 250.75, "item123", 10, "123 Main St, City");
    }
}

```

**Real World Examples**

* Java's java.net.URL class: This class acts as a facade to the underlying networking operations, simplifying the process of connecting to and interacting with a web resource.
* Database connection pools: A connection pool library often provides a facade that simplifies the management and use of multiple database connections.

**Considerations**

* Flexibility vs. Simplification: While the Facade pattern simplifies the interface, it can also reduce the flexibility of interacting directly with the subsystem classes.
* Maintenance: Changes in the subsystem may require changes in the Facade, increasing the maintenance burden.
* Performance: The Facade adds an additional layer, which might impact performance in highly optimized systems.

## Flyweight

The Flyweight pattern is used to minimize memory usage by sharing as much data as possible with similar objects. It is particularly useful when a large number of similar objects are needed.

**When to Use**

* When an application needs to create a large number of similar objects.
* When memory overhead is a concern, and the objects can share common data, distinguishing between intrinsic (shared) and extrinsic (unique) states.
* When the cost of creating and maintaining individual instances becomes prohibitive.
  
**How to Implement**

1. Identify shared state: Extract the common, intrinsic state from the objects and store it in shared Flyweight objects.
2. Separate extrinsic state: The unique, extrinsic state of each object instance is passed to methods that perform operations on the Flyweight.
3. Factory for Flyweights: Use a factory to manage the creation and reuse of Flyweight objects.
4. Client interaction: The client should work with Flyweight objects through the factory and provide extrinsic data when invoking methods on the Flyweight.

**Sample Implementation**

In a delivery real-time monitoring UI, many similar graphic components such as icons for delivery trucks, houses, trees, traffic lights, and roads are needed. These graphic components are expensive to create and render repeatedly.

```java
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

// Flyweight interface
interface Graphic {
    void draw(int x, int y); // The extrinsic state (coordinates)
}

// Concrete Flyweight
class TruckIcon implements Graphic {
    private final Image truckImage; // Intrinsic state

    public TruckIcon() {
        // Simulate loading a heavy truck image
        this.truckImage = loadImage("truck.png");
    }

    private Image loadImage(String filename) {
        // Simulate loading an image file (in reality, use ImageIO or similar)
        System.out.println("Loading image: " + filename);
        return null; // Return a mock Image object for this example
    }

    @Override
    public void draw(int x, int y) {
        // Use the truck image and draw at coordinates (x, y)
        render(truckImage, x, y);
    }

    private void render(Image image, int x, int y) {
        // Simulate rendering the image on a UI at the given coordinates
        System.out.println("Rendering truck at (" + x + ", " + y + ")");
    }
}

// Flyweight Factory
class GraphicFactory {
    private static final Map<String, Graphic> graphics = new HashMap<>();

    public static Graphic getGraphic(String type) {
        // Use computeIfAbsent to lazily load and store the graphic object
        return graphics.computeIfAbsent(type, t -> {
            switch (t) {
                case "truck":
                    return new TruckIcon();
                // Cases for other graphic types like "house", "tree", etc.
                default:
                    throw new IllegalArgumentException("Unknown graphic type: " + t);
            }
        });
    }
}

// Client code
class MonitoringUI {
    public void drawScene(String graphicType, Stream<int[]> coordinates) {
        Graphic graphic = GraphicFactory.getGraphic(graphicType);
        coordinates.forEach(coord -> graphic.draw(coord[0], coord[1]));
    }
}

// Main method to run the example
public class FlyweightExample {
    public static void main(String[] args) {
        MonitoringUI ui = new MonitoringUI();
        
        // Stream of coordinates where trucks should be rendered
        Stream<int[]> truckCoordinates = Stream.of(
            new int[]{50, 100},
            new int[]{150, 200},
            new int[]{250, 300}
        );
        
        // Draw trucks at the specified coordinates
        ui.drawScene("truck", truckCoordinates);
    }
}

```

**Real World Examples**

* Text editors: Characters are shared between documents to reduce memory footprint.
* Video games: Shared textures and sprites for objects like trees, rocks, and enemies.

**Considerations**

* Complexity: The Flyweight pattern can add complexity due to the management of shared and unique states.
* Performance: While memory usage is reduced, the performance may be impacted by the need to pass extrinsic data for each operation.
* Suitability: Not all scenarios benefit from the Flyweight pattern, especially when objects do not share much common data.
* Flyweight allows for savings in memory cost by referencing the same intrinsic property that is memory expensive

## Proxy

Provide a surrogate or placeholder for another object to control access to it.

**When to Use**

* When you need to control access to an object.
* When an object is resource-intensive to create or perform operations, and you want to delay its creation until it’s absolutely necessary.

**How to Implement**

1. Create an Interface: Define an interface that both the RealSubject and Proxy will implement.
2. Implement the Real Subject: This is the actual object that the proxy represents.
3. Implement the Proxy: Create a class that implements the interface and controls access to the RealSubject.
4. Use the Proxy: The client interacts with the Proxy as if it were the Real Subject.

**Sample Implementation**

Scenario: An eCommerce system uses a Feature Control Manager service that determines if specific features are enabled or disabled. Instead of making an HTTP request each time a feature status is needed, a Proxy is used that listens for real-time updates via a socket event listener and caches the feature status in memory.

```java
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

// 1. Interface
public interface FeatureManager {
    boolean isFeatureEnabled(String featureName);
}

// 2. Real Subject
public class FeatureControlManager implements FeatureManager {
    private final HttpClient httpClient;
    private final String featureServiceUrl;

    public FeatureControlManager(String featureServiceUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.featureServiceUrl = featureServiceUrl;
    }

    @Override
    public boolean isFeatureEnabled(String featureName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(featureServiceUrl + "/features/" + featureName))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            // Assuming the response body is "true" or "false"
            return Boolean.parseBoolean(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Default to false if an error occurs
        }
    }
}

// 3. Proxy
public class FeatureControlManagerProxy implements FeatureManager {
    private final FeatureControlManager featureControlManager;
    private final Map<String, Boolean> featureCache;
    private final WebSocket webSocket;

    public FeatureControlManagerProxy(String featureServiceUrl, String socketUrl) {
        this.featureControlManager = new FeatureControlManager(featureServiceUrl);
        this.featureCache = new ConcurrentHashMap<>();

        this.webSocket = HttpClient.newHttpClient().newWebSocketBuilder()
                .buildAsync(URI.create(socketUrl), new FeatureUpdateListener())
                .join();
    }

    @Override
    public boolean isFeatureEnabled(String featureName) {
        return featureCache.getOrDefault(featureName, featureControlManager.isFeatureEnabled(featureName));
    }

    // Socket Listener to update feature cache in real-time
    private class FeatureUpdateListener implements Listener {
        @Override
        public void onText(WebSocket webSocket, CharSequence data, boolean last) {
            // Assuming the data format is "featureName:true" or "featureName:false"
            String[] parts = data.toString().split(":");
            if (parts.length == 2) {
                featureCache.put(parts[0], Boolean.parseBoolean(parts[1]));
            }
            webSocket.request(1); // Request more messages
        }
        
        @Override
        public void onOpen(WebSocket webSocket) {
            webSocket.request(1); // Request the first message
        }

        @Override
        public CompletableFuture<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            return Listener.super.onClose(webSocket, statusCode, reason);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            error.printStackTrace();
        }
    }
}

// 4. Client usage
public class ECommerceApplication {
    public static void main(String[] args) {
        String featureServiceUrl = "http://featureservice.com";
        String socketUrl = "ws://featureservice.com/socket";

        FeatureManager featureManager = new FeatureControlManagerProxy(featureServiceUrl, socketUrl);
        
        System.out.println("Is 'discounts' feature enabled? " + featureManager.isFeatureEnabled("discounts"));
        System.out.println("Is 'newUI' feature enabled? " + featureManager.isFeatureEnabled("newUI"));
    }
}

```

**Real World Examples**

* Virtual Proxy with Caching: In web applications, an image proxy might cache images to reduce the number of requests made to a server.
* Remote Proxy: In microservices, a proxy might handle communication between services, adding retries, caching, or rate limiting.

**Considerations**

* You can control the service object without clients knowing about it.
* You can manage the lifecycle of the service object when clients don’t care about it.
* The proxy works even if the service object isn’t ready or is not available.
* Open/Closed Principle. You can introduce new proxies without changing the service or clients.
* Performance: A proxy can introduce overhead, especially if it adds significant logic (e.g., security checks, logging). Consider whether this overhead is acceptable.
* Complexity: Introducing a proxy can complicate the design. Ensure that the benefits outweigh the added complexity.
* Security: In cases where the proxy controls access, ensure that it doesn’t become a single point of failure or a security vulnerability.