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