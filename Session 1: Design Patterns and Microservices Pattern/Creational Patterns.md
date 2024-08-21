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
   public class InMemoryPhoneDatabase {
       private static InMemoryPhoneDatabase instance;

       private final Map<String, Integer> internalMap = new HashMap<>();

       private InMemoryPhoneDatabase() {}

       public static InMemoryPhoneDatabase getInstance() {
           if (instance == null) {
               instance = new InMemoryPhoneDatabase();
           }
           return instance;
       }

       public Integer getPhoneNumberOf(String name){
           return internalMap.get(name);
       }

       public void registerNumberOf(Integer phoneNumber, String name){
           internalMap.putIfAbsent(name, phoneNumber);
       }
   }
   ```

**Implementation Considerations:**
While the Singleton pattern can be useful, it's important to use it judiciously. Overusing Singletons or using them inappropriately can lead to problems such as:

- Global State: Singletons can introduce global state into your application, which can make the system harder to understand and test.
  
- Concurrency Issues: In multi-threaded environments, you need to handle synchronization carefully to avoid issues with concurrent access.
  
- Testing Challenges: Singletons can make unit testing difficult because they introduce global state and can lead to hidden dependencies between tests.