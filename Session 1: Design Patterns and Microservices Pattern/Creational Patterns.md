# Creational Patterns
Creational patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation. They provide a way to create objects while hiding the creation logic, rather than instantiating objects directly using new. This makes the program more flexible in deciding which objects need to be created for a given case.

## Singleton
Ensures a class has only one instance and provides a global point of access to it.

**When to use**
- Controlled Access to a Single Instance: If you need to control the access to a single instance of a class, the Singleton pattern ensures that only one instance is created and provides a global access point to that instance.

- Consistency Across the Application: When you need a single point of truth or a consistent state that needs to be shared across different parts of your application, a Singleton ensures that all parts of the application are using the same instance.

- Resource Management: In some cases, creating multiple instances of a class could be resource-intensive or impractical. For example, a database connection or a thread pool might be managed by a Singleton to avoid the overhead of creating and destroying instances.

- Global Access: A Singleton provides a global access point to the instance, making it easy to access the single instance from anywhere in the application.

**How to Implement**
1. Add a private static field to the class for storing the singleton instance.

2. Declare a public static creation method for getting the singleton instance.

3. Implement “lazy initialization” inside the static method. It should create a new object on its first call and put it into the static field. The method should always return that instance on all subsequent calls.

4. Make the constructor of the class private. The static method of the class will still be able to call the constructor, but not the other objects.


   ```java
   public class InMemoryLogger {
       private static InMemoryLogger instance;

       private final List<LogEntry> internalStorage = new ArrayList<>();

       private InMemoryLogger() {}

       public static InMemoryLogger getInstance() {
           if (instance == null) {
               instance = new InMemoryLogger();
           }
           return instance;
       }

       public List<String> getErrorLogsFrom(LocalDate date){
           return internalStorage.stream()
            .filter(LogEntry::isError)
            .filter(entry -> entry.happenedOnOrAfter(date))
            .map(LogEntry::getMessage)
            .collect(Collectors.toList());
       }

       public void writeErrorMessage(String message){
           LogEntry logEntry = new ErrorLogEntry(message);
           internalStorage.add(logEntry);
       }
   }
   ```

 **Sample Usage**

 ```java
 class PaymentService {

    private final BankGateway bankGateway;

    public void payForBill(BillDetails billDetails){
        try {
            Account from = resolveAccount(billDetails);
            Account to = resolvePayee(billDetails);
            Money billAmount = resolveAmount(billDetails);

            bankGateway.performPayment(from, to, billAmount);
        } catch (Exception e){
            //usage
            InMemoryLogger.getInstance().writeErrorMessage(e.getMessage());
        }
    }
 }
 ```  

**Implementation Considerations:**
While the Singleton pattern can be useful, it's important to use it judiciously. Overusing Singletons or using them inappropriately can lead to problems such as:

- Global State: Singletons can introduce global state into your application, which can make the system harder to understand and test.
  
- Concurrency Issues: In multi-threaded environments, you need to handle synchronization carefully to avoid issues with concurrent access.
  
- Testing Challenges: Singletons can make unit testing difficult because they introduce global state and can lead to hidden dependencies between tests.

## Factory Method
A Pattern that defines an interface for creating an object but lets subclasses alter the type of objects that will be created. It allows a class to defer instantiation to subclasses.

**When to Use**

- When a class cannot anticipate the type of objects it needs to create.
- When you want to delegate the responsibility of instantiating objects to subclasses.
- When classes in a system need to be independent of the way objects are created.

**How to Implement**

1. Define an interface or abstract class with a factory method. (Creator)
2. Subclasses implement or override the factory method to create specific objects (Product).
3. The client code interacts with the creator, allowing it to work with objects in a polymorphic manner without knowing their concrete classes.

**Sample Implementation**

```java
// Product interface
interface Vehicle {
    void startUp();
}

// Concrete Product - Sedan
class Sedan implements Vehicle {
    @Override
    public void startUp() {
        System.out.println("Sedan is starting up!");
    }
}

// Concrete Product - SUV
class SUV implements Vehicle {
    @Override
    public void startUp() {
        System.out.println("SUV is starting up!");
    }
}

// Creator - a class/interface with a Factory Method
abstract class VehicleSimulator {
    abstract Vehicle createVehicle();

    void startSimulation() {
        Vehicle vehicle = createVehicle();
        vehicle.startUp();
    }
}

// Concrete Creator - SedanSimulator
class SedanSimulator extends VehicleSimulator {
    @Override
    Vehicle createVehicle() {
        return new Sedan();
    }
}

// Concrete Creator - SUVSimulator
class SUVSimulator extends VehicleSimulator {
    @Override
    Vehicle createVehicle() {
        return new SUV();
    }
}

// Client code
public class SimulatorApp {
    public static void main(String[] args) {
        VehicleSimulator sedanSimulator = new SedanSimulator();
        sedanSimulator.startSimulation();  // Output: Sedan is starting up!

        VehicleSimulator suvSimulator = new SUVSimulator();
        suvSimulator.startSimulation();  // Output: SUV is starting up!
    }
}
```

**Real-World Examples**

1. Document Editor Application
   
    In a document editor application, users can create different types of documents such as Word documents, PDFs, and spreadsheets.

    - **Product Interface:** `Document` with a method `open()`.
    - **Concrete Products:** `WordDocument`, `PDFDocument`, `Spreadsheet`.
    - **Creator Class:** `DocumentCreator` with a method `createDocument()`.
    - **Concrete Creators:** `WordDocumentCreator`, `PDFDocumentCreator`, `SpreadsheetCreator`.

2. Notification System

    A notification system where notifications can be sent via different channels such as email, SMS, and push notifications.

   - **Product Interface:** `Notification` with a method `send()`.
   - **Concrete Products:** `EmailNotification`, `SMSNotification`, `PushNotification`.
    - **Creator Class:** `NotificationService` with a method `createNotification()`.
    - **Concrete Creators:** `EmailNotificationService`, `SMSNotificationService`, `PushNotificationService`.

3. Payment Gateway Integration

    An e-commerce platform where payments can be processed using different payment gateways like PayPal, Stripe, and Square.

   - **Product Interface:** `PaymentProcessor` with a method `processPayment()`.
   - **Concrete Products:** `PayPalProcessor`, `StripeProcessor`, `SquareProcessor`.
   - **Creator Class:** `PaymentGateway` with a method `createProcessor()`.
   - **Concrete Creators:** `PayPalGateway`, `StripeGateway`, `SquareGateway`.

4. GUI Component Library

    A GUI framework where components such as buttons, checkboxes, and text fields can be created for different platforms (Windows, macOS, Linux).

    - **Product Interface:** `GUIComponent` with a method `render()`.
    - **Concrete Products:** `WindowsButton`, `MacButton`, `LinuxButton`.
    - **Creator Class:** `ComponentFactory` with a method `createComponent()`.
    - **Concrete Creators:** `WindowsFactory`, `MacFactory`, `LinuxFactory`.

5. Logging Framework

    A logging framework that supports different logging mechanisms such as file logging, console logging, and database logging.
    - **Product Interface:** `Logger` with a method `log(String message)`.
    - **Concrete Products:** `FileLogger`, `ConsoleLogger`, `DatabaseLogger`.
    - **Creator Class:** `LoggerFactory` with a method `createLogger()`.
    - **Concrete Creators:** `FileLoggerFactory`, `ConsoleLoggerFactory`, `DatabaseLoggerFactory`.


**Considerations**

- Pros: Provides flexibility in object creation, promotes loose coupling, and follows the Open/Closed Principle.
- Cons: The code can become more complex with the addition of many subclasses. Each new type of product requires creating a new subclass of the creator.

## Abstract Factory

The Abstract Factory Pattern provides an interface for creating families of related or dependent objects without specifying their concrete classes. It allows for the creation of a group of related objects from a single point of control.

**When to use**

- When a system needs to be independent of the way its products are created, composed, or represented.
- When you want to enforce constraints about which related objects can be used together.
- When you have multiple families of products that need to be created together.

**How to Implement**

1. **Define Abstract Products**: Create interfaces or abstract classes for each type of product in the family.
2. **Create Concrete Products**: Implement concrete classes for each product that belong to different families.
3. **Define an Abstract Factory**: Create an interface or abstract class with a set of methods to create each type of product.
4. **Implement Concrete Factories**: Create concrete classes implementing the abstract factory, each responsible for creating objects from a specific product family.
5. **Use the Abstract Factory**: Client code interacts with the abstract factory to get instances of the product families without knowing the concrete classes.

**Sample Implementation**

Abstract Factory Pattern for Commerce Order System

1. **Product Interfaces:**

    - Order
    - Invoice
    
2. **Concrete Products:**

    - PhysicalOrder
    - DigitalOrder
    - PhysicalInvoice
    - DigitalInvoice
  
3. **Abstract Factory:**

    - OrderFactory
  
4. **Concrete Factories:**

   - PhysicalOrderFactory
   - DigitalOrderFactory
  

```java
// Product Interfaces
interface Order {
    void processOrder();
}

interface Invoice {
    void generateInvoice();
}

// Concrete Products - Physical Order
class PhysicalOrder implements Order {
    @Override
    public void processOrder() {
        System.out.println("Processing physical order.");
    }
}

class PhysicalInvoice implements Invoice {
    @Override
    public void generateInvoice() {
        System.out.println("Generating physical invoice.");
    }
}

// Concrete Products - Digital Order
class DigitalOrder implements Order {
    @Override
    public void processOrder() {
        System.out.println("Processing digital order.");
    }
}

class DigitalInvoice implements Invoice {
    @Override
    public void generateInvoice() {
        System.out.println("Generating digital invoice.");
    }
}

// Abstract Factory
interface OrderFactory {
    Order createOrder();
    Invoice createInvoice();
}

// Concrete Factories - Physical Order
class PhysicalOrderFactory implements OrderFactory {
    @Override
    public Order createOrder() {
        return new PhysicalOrder();
    }

    @Override
    public Invoice createInvoice() {
        return new PhysicalInvoice();
    }
}

// Concrete Factories - Digital Order
class DigitalOrderFactory implements OrderFactory {
    @Override
    public Order createOrder() {
        return new DigitalOrder();
    }

    @Override
    public Invoice createInvoice() {
        return new DigitalInvoice();
    }
}

// Client code
public class CommerceOrderSystem {
    public static void main(String[] args) {
        // Create and process physical order
        OrderFactory physicalOrderFactory = new PhysicalOrderFactory();
        Order physicalOrder = physicalOrderFactory.createOrder();
        Invoice physicalInvoice = physicalOrderFactory.createInvoice();

        physicalOrder.processOrder();  // Output: Processing physical order.
        physicalInvoice.generateInvoice();  // Output: Generating physical invoice.

        // Create and process digital order
        OrderFactory digitalOrderFactory = new DigitalOrderFactory();
        Order digitalOrder = digitalOrderFactory.createOrder();
        Invoice digitalInvoice = digitalOrderFactory.createInvoice();

        digitalOrder.processOrder();  // Output: Processing digital order.
        digitalInvoice.generateInvoice();  // Output: Generating digital invoice.
    }
}

```

**Real-World Examples**

1. Document Creation Software:

    A document creation tool that supports different document types with varying formatting options, such as Markdown and LaTeX.

    * Product Interfaces: Document, Formatter.
    * Concrete Products: MarkdownDocument, LatexDocument, MarkdownFormatter, LatexFormatter.
    * Factories: MarkdownFactory, LatexFactory.

2. Database Connectivity:

    A database connection pool that supports multiple types of databases, such as MySQL, PostgreSQL, and SQLite.

    * Product Interfaces: Connection, Statement.
    * Concrete Products: MySQLConnection, PostgreSQLConnection, SQLiteConnection, MySQLStatement, PostgreSQLStatement, SQLiteStatement.
    * Factories: MySQLFactory, PostgreSQLFactory, SQLiteFactory.

3. User Authentication Systems: 

    A user authentication system that supports different authentication methods, such as OAuth and SAML.

    * Product Interfaces: Authenticator, Token.
    * Concrete Products: OAuthAuthenticator, SAMLAuthenticator, OAuthToken, SAMLToken.
    * Factories: OAuthFactory, SAMLFactory.

**Considerations**

* Pros: Promotes consistency among related objects, isolates client code from concrete classes, and supports the Open/Closed Principle by allowing easy extension with new families of products.
  
* Cons: Can increase complexity due to the large number of interfaces and classes. Adding new products to the family may require changes in the factory interface and all its concrete implementations.

## Builder

Separate the construction of a complex object from its representation so that
the same construction process can create different representations.

**When to use**

* When constructing complex objects that require multiple steps.
* When you need to create different representations of an object using the same construction process.
* When the construction process needs to be independent of the parts that make up the product.

**How to Implement**

1. Create a Builder interface that defines methods to build different parts of the product.
2. Implement concrete Builder classes for constructing specific types of products.
3. Create a Director class that manages the construction process using the Builder interface. The Director ensures that the steps are executed in a specific sequence.
4. The Builder implementation is responsible for assembling the product and providing access to it (e.g., through a `getResult()` method).

**Sample Implementation**

```java
//Product
class ItemPamphlet {
    private final String title;
    private final String description;
    
    public ItemPamphlet(String title, String description){
        this.title = title;
        this.description = description;
    }
    
    @Override
    public String toString(){
        return "ItemPamphlet{" +
            "title='" + title + '\'' +
            ", description='" + description + '\'' +
            '}';
    }
}

//Another product
class ItemWebRender {
    private final String css;
    private final String html;
    private final String embeddedScript;
    
    public ItemWebRender(String css, String html, String embeddedScript){
        this.css = css;
        this.html = html;
        this.embeddedScript = embeddedScript;
    }
    
    @Override
    public String toString(){
        return "ItemWebRender{" +
            "css='" + css + '\'' +
            ", html='" + html + '\'' +
            ", embeddedScript='" + embeddedScript + '\'' +
            '}';
    }
}

//Builder Interface 
interface ItemPromotionBuilder<E> {
    void setName(String name);
    void setItemCode(String code);
    void setItemImagePath(String imageAssetPath);
    void setItemDescription(String description);
    E assemble();
}

//Builder implementation
class ItemPamphletPromotionBuilder implements ItemPromotionBuilder<ItemPamphlet> {
    
    private String name;
    private String code;
    private String imageAssetPath;
    private String description;
    
    
    @Override
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public void setItemCode(String code){
        this.code = code;
    }
    
    @Override
    public void setItemImagePath(String imageAssetPath){
        this.imageAssetPath = imageAssetPath;
    }
    
    @Override
    public void setItemDescription(String description){
        this.description = description;
    }
    
    @Override
    public ItemPamphlet assemble(){
        String title = buildTitle();
        String description = buildDescription();
        return new ItemPamphlet(title, description);
    }
    
    private String buildDescription(){
        return imageAssetPath + '\n' + description;
    }
    
    private String buildTitle(){
        return name + " (" + code + ")";
    }
}

//Builder implementation
class ItemWebRenderPromotionBuilder implements ItemPromotionBuilder<ItemWebRender> {
    
    private String name;
    private String code;
    private String imageAssetPath;
    private String description;
    
    
    @Override
    public void setName(String name){
        this.name = name;
    }
    
    @Override
    public void setItemCode(String code){
        this.code = code;
    }
    
    @Override
    public void setItemImagePath(String imageAssetPath){
        this.imageAssetPath = imageAssetPath;
    }
    
    @Override
    public void setItemDescription(String description){
        this.description = description;
    }
    
    @Override
    public ItemWebRender assemble(){
        String html = buildHTML();
        return new ItemWebRender("margin: 10em 10px", html, "javascript: void(0)");
    }
    
    private String buildHTML(){
        return new StringBuilder("<h3><strong>" + code + "</strong><br/>" + name + "</h3>")
            .append("<p>").append("<img src='")
            .append(imageAssetPath).append("'")
            .append(description)
            .append("</p>")
            .toString();
    }
}

class Item {
    
    private final String name;
    private final String code;
    private final String imageAssetPath;
    private final String description;
    
    public Item(String name, String code, String imageAssetPath, String description){
        this.name = name;
        this.code = code;
        this.imageAssetPath = imageAssetPath;
        this.description = description;
    }
    
    public String getName(){
        return name;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getImageAssetPath(){
        return imageAssetPath;
    }
    
    public String getDescription(){
        return description;
    }
}

//Director
class ItemPromotionManager {
    
    private final Item item;
    
    public ItemPromotionManager(Item item){
        this.item = item;
    }
    
    <E> E prepareMaterial(ItemPromotionBuilder<E> builder){
        builder.setName(item.getName());
        builder.setItemCode(item.getCode());
        builder.setItemImagePath(item.getImageAssetPath());
        builder.setItemDescription(item.getDescription());
        return builder.assemble();
    }
}

class SampleImplementation {
    public static void main(String[] args){
        Item sampleItem = new Item(
            "Six piece pandesal",
            "BR0001",
            "/pastries/pandesal/BR0001",
            "Six toasted pandesal packed together"
        );
        
        ItemPromotionManager itemPromotionManager = new ItemPromotionManager(sampleItem);
        
        ItemPamphlet sampleItemPamphlet = itemPromotionManager.prepareMaterial(new ItemPamphletPromotionBuilder());
        System.out.println(sampleItemPamphlet);
        
        ItemWebRender sampleItemWebRender = itemPromotionManager.prepareMaterial(new ItemWebRenderPromotionBuilder());
        System.out.println(sampleItemWebRender);
    }
}
```

**Real World Scenarios**

Some examples of using the Builder pattern in knowledge engineering include different generators. Parsers in various compilers are also designed using the Builder pattern.

**Considerations**

* **Single Responsibility**: The Director is focused purely on orchestrating the construction process, leaving the actual creation of parts to the builders.
* **Reusability**: The same Director can be reused with different builders to create various products, reducing code duplication and enhancing flexibility.
* **Complexity**: While providing powerful abstraction, the pattern may introduce complexity, especially if there are many products or varied construction processes.
* **Separation of Concerns**: The director and builders are independent, ensuring that changes to the construction process or the product type do not affect each other.

## Prototype

Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

**When to use**

Use the Prototype pattern when a system should be independent of how its products are created, composed, and represented; and

* when the classes to instantiate are specified at run-time for example, by dynamic loading; or
* to avoid building a class hierarchy of factories that parallels the class hierarchy of products; or
* when instances of a class can have one of only a few different combinations of state. It may be more convenient to install a corresponding number of prototypes and clone them rather than instantiating the class manually, each time with the appropriate state.

**How to Implement**

1. Create an interface or abstract class with a clone() or similar method. (The prototype interface)
2. Implement the clone() method in the concrete classes to return a copy of the object.
3. Use the clone() method to create new objects based on existing instances. (The client/system)


**Sample Implementation**

```java
class DressBrowsingConfiguration {
    private final int size;
    private final String color;
    
    DressBrowsingConfiguration(int size, String color){
        this.size = size;
        this.color = color;
    }
    
    public int getSize(){
        return size;
    }
    
    public String getColor(){
        return color;
    }
    
    @Override
    public String toString(){
        return "DressBrowsingConfiguration{" +
            "size=" + size +
            ", color='" + color + '\'' +
            '}';
    }
}

//Prototype interface
interface EfficientDressCatalog {
    EfficientDressCatalog customizeWith(DressBrowsingConfiguration customization);
}

//Concrete prototype
class AICustomizedDress implements EfficientDressCatalog {
    
    private final byte[] generatedDressImage;
    
    AICustomizedDress(byte[] generatedDressImage){
        this.generatedDressImage = generatedDressImage;
    }
    
    @Override
    public EfficientDressCatalog customizeWith(DressBrowsingConfiguration customization){
        byte[] customizedImage = customizeImageWith(customization);
        return new AICustomizedDress(customizedImage);
    }
    
    private byte[] customizeImageWith(DressBrowsingConfiguration customization){
        System.out.println("Customizing with "+customization.toString());
        return this.generatedDressImage;
    }
    
    @Override
    public String toString(){
        return "AICustomizedDress{" +
            "generatedDressImage=" + Arrays.toString(generatedDressImage) +
            '}';
    }
}
class UserSession {

}

class AIDressSuggestionGateway {
    List<EfficientDressCatalog> getDressSuggestions(UserSession userSession){
        System.out.println("Expensive AI dress suggestion generation call using user session "+userSession);
        return Collections.singletonList(
            new AICustomizedDress(new byte[]{'E','x','p','e','n','s','i','v','e'})
        );
    }
}

class SampleDressCatalogHandler{

    //sample client/system using the prototype
    public static void main(String[] args){
        AIDressSuggestionGateway aiDressSuggestionGateway = new AIDressSuggestionGateway();
        
        System.out.println("Simulating first request...");
        List<EfficientDressCatalog> dressSuggestions = aiDressSuggestionGateway.getDressSuggestions(new UserSession());
        DressBrowsingConfiguration userSettingsOne = new DressBrowsingConfiguration(1, "GREEN");
        List<EfficientDressCatalog> customizedSuggestions = dressSuggestions.stream()
            .map(dressSuggestion -> dressSuggestion.customizeWith(userSettingsOne))
            .collect(Collectors.toList());
        System.out.println(customizedSuggestions);
    
        System.out.println("Simulating second request...");
        DressBrowsingConfiguration userSettingsTwo = new DressBrowsingConfiguration(3, "BLUE");
        List<EfficientDressCatalog> customizedSuggestionsTwo = dressSuggestions.stream()
            .map(dressSuggestion -> dressSuggestion.customizeWith(userSettingsTwo))
            .collect(Collectors.toList());
        System.out.println(customizedSuggestionsTwo);
    }
}
```

**Real World Examples**

* Document Templates: A word processor may have templates for letters, memos, etc., that are used as prototypes to create new documents.
* Game Development: Characters or objects in a game can be cloned from existing prototypes to save time on initializing attributes.

**Considerations**

* **Shallow vs. Deep Copy**: Shallow copying duplicates the object but not its nested objects, while deep copying duplicates everything. Decide which is more appropriate based on your use case.
* **Prototype Registry**: You may need to maintain a registry of available prototypes to clone from, especially in large systems.
* **Copy Cost**: Although cloning can be faster than initializing from scratch, the clone() method may have performance implications if deep copies are required.