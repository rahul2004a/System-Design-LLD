# Builder Design Pattern

## Overview

The Builder Design Pattern is a **creational design pattern** that provides a flexible solution for constructing complex objects step by step. It separates the construction of a complex object from its representation, allowing the same construction process to create different representations of the object.

Unlike other creational patterns that create objects in one go, the Builder pattern constructs objects step by step, making it ideal for creating objects with many optional parameters or complex initialization logic.

## Diagram

Below is a diagram illustrating the Builder Design Pattern:

```
+---------------------+       +-----------------------------+
|      Director       |       |        Builder              |
|---------------------|       |-----------------------------|
| - builder: Builder  |------>| + buildPartA()              |
| + construct()       |       | + buildPartB()              |
+---------------------+       | + buildPartC()              |
                              | + getResult(): Product      |
                              +-----------------------------+
                                       ^
                                       |
                              +-----------------------------+
                              |    ConcreteBuilder          |
                              |-----------------------------|
                              | - product: Product          |
                              | + buildPartA()              |
                              | + buildPartB()              |
                              | + buildPartC()              |
                              | + getResult(): Product      |
                              +-----------------------------+
                                       |
                                       | creates
                                       v
                              +-----------------------------+
                              |        Product              |
                              |-----------------------------|
                              | - partA: String             |
                              | - partB: String             |
                              | - partC: String             |
                              +-----------------------------+
```

## Key Components

1. **Product**: The complex object being constructed
2. **Builder**: Abstract interface defining the construction steps
3. **ConcreteBuilder**: Implements the Builder interface and constructs specific variants of the product
4. **Director**: Orchestrates the construction process using the Builder interface (optional)

## Problem Statement

### What is the Problem?

Consider building a complex object like a **Computer** with many optional components:

1. **Required Components**: CPU, RAM, Storage
2. **Optional Components**: Graphics Card, Sound Card, Bluetooth, WiFi, Webcam, etc.

Traditional constructor approach leads to several issues:

```java
// Telescoping Constructor Anti-pattern
public class Computer {
    public Computer(String cpu, String ram) { ... }
    public Computer(String cpu, String ram, String storage) { ... }
    public Computer(String cpu, String ram, String storage, String gpu) { ... }
    public Computer(String cpu, String ram, String storage, String gpu, String soundCard) { ... }
    // ... many more constructors
}
```

Problems with this approach:

1. **Constructor Explosion**: Too many constructors with different parameter combinations
2. **Parameter Confusion**: Easy to mix up parameters of the same type
3. **Inflexibility**: Hard to add new optional parameters
4. **Poor Readability**: `new Computer("Intel i7", "16GB", null, "RTX 3080", null, true, false)`

### How Are We Solving It?

The Builder Pattern solves these problems by:

1. **Step-by-Step Construction**: Build the object incrementally
2. **Fluent Interface**: Chain method calls for better readability
3. **Optional Parameters**: Only set the parameters you need
4. **Immutable Objects**: Create immutable objects safely
5. **Validation**: Validate the object state before creation

## Example Code - Computer Builder

Below is a comprehensive example of the Builder Pattern for constructing a Computer:

### Product Class

```java
public class Computer {
    // Required parameters
    private final String cpu;
    private final String ram;
    private final String storage;

    // Optional parameters
    private final String gpu;
    private final String soundCard;
    private final boolean bluetooth;
    private final boolean wifi;
    private final boolean webcam;

    // Private constructor - only Builder can create instances
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.soundCard = builder.soundCard;
        this.bluetooth = builder.bluetooth;
        this.wifi = builder.wifi;
        this.webcam = builder.webcam;
    }

    // Getters
    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public String getSoundCard() { return soundCard; }
    public boolean hasBluetooth() { return bluetooth; }
    public boolean hasWifi() { return wifi; }
    public boolean hasWebcam() { return webcam; }

    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", RAM=" + ram + ", Storage=" + storage +
               ", GPU=" + gpu + ", SoundCard=" + soundCard + ", Bluetooth=" + bluetooth +
               ", WiFi=" + wifi + ", Webcam=" + webcam + "]";
    }

    // Static nested Builder class
    public static class ComputerBuilder {
        // Required parameters
        private final String cpu;
        private final String ram;
        private final String storage;

        // Optional parameters - initialized to default values
        private String gpu = "Integrated";
        private String soundCard = "Integrated";
        private boolean bluetooth = false;
        private boolean wifi = false;
        private boolean webcam = false;

        // Constructor with required parameters
        public ComputerBuilder(String cpu, String ram, String storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        // Methods for optional parameters - return Builder for chaining
        public ComputerBuilder setGpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public ComputerBuilder setSoundCard(String soundCard) {
            this.soundCard = soundCard;
            return this;
        }

        public ComputerBuilder setBluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public ComputerBuilder setWifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        public ComputerBuilder setWebcam(boolean webcam) {
            this.webcam = webcam;
            return this;
        }

        // Build method to create the final object
        public Computer build() {
            // Validation logic can be added here
            validateComputer();
            return new Computer(this);
        }

        private void validateComputer() {
            // Example validation
            if (cpu == null || cpu.isEmpty()) {
                throw new IllegalArgumentException("CPU is required");
            }
            if (ram == null || ram.isEmpty()) {
                throw new IllegalArgumentException("RAM is required");
            }
            if (storage == null || storage.isEmpty()) {
                throw new IllegalArgumentException("Storage is required");
            }
        }
    }
}
```

### Usage Example

```java
public class BuilderPatternDemo {
    public static void main(String[] args) {
        // Example 1: Basic Computer
        Computer basicComputer = new Computer.ComputerBuilder("Intel i5", "8GB", "256GB SSD")
                .build();

        System.out.println("Basic Computer: " + basicComputer);

        // Example 2: Gaming Computer
        Computer gamingComputer = new Computer.ComputerBuilder("Intel i9", "32GB", "1TB NVMe SSD")
                .setGpu("NVIDIA RTX 4080")
                .setSoundCard("Creative Sound Blaster")
                .setBluetooth(true)
                .setWifi(true)
                .setWebcam(true)
                .build();

        System.out.println("Gaming Computer: " + gamingComputer);

        // Example 3: Office Computer
        Computer officeComputer = new Computer.ComputerBuilder("AMD Ryzen 5", "16GB", "512GB SSD")
                .setWifi(true)
                .setWebcam(true)
                .build();

        System.out.println("Office Computer: " + officeComputer);
    }
}
```

## Advanced Example - SQL Query Builder

Here's another practical example using the Builder pattern for constructing SQL queries:

### SQL Query Builder

```java
public class SqlQuery {
    private final String table;
    private final List<String> columns;
    private final List<String> conditions;
    private final List<String> orderBy;
    private final Integer limit;
    private final String operation;

    private SqlQuery(SqlQueryBuilder builder) {
        this.table = builder.table;
        this.columns = builder.columns;
        this.conditions = builder.conditions;
        this.orderBy = builder.orderBy;
        this.limit = builder.limit;
        this.operation = builder.operation;
    }

    public String toSql() {
        StringBuilder sql = new StringBuilder();

        sql.append(operation).append(" ");

        if ("SELECT".equals(operation)) {
            if (columns.isEmpty()) {
                sql.append("*");
            } else {
                sql.append(String.join(", ", columns));
            }
            sql.append(" FROM ").append(table);
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        if (!orderBy.isEmpty()) {
            sql.append(" ORDER BY ").append(String.join(", ", orderBy));
        }

        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }

        return sql.toString();
    }

    public static class SqlQueryBuilder {
        private String table;
        private List<String> columns = new ArrayList<>();
        private List<String> conditions = new ArrayList<>();
        private List<String> orderBy = new ArrayList<>();
        private Integer limit;
        private String operation = "SELECT";

        public SqlQueryBuilder select() {
            this.operation = "SELECT";
            return this;
        }

        public SqlQueryBuilder from(String table) {
            this.table = table;
            return this;
        }

        public SqlQueryBuilder columns(String... columns) {
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public SqlQueryBuilder where(String condition) {
            this.conditions.add(condition);
            return this;
        }

        public SqlQueryBuilder orderBy(String column) {
            this.orderBy.add(column);
            return this;
        }

        public SqlQueryBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public SqlQuery build() {
            if (table == null || table.isEmpty()) {
                throw new IllegalArgumentException("Table name is required");
            }
            return new SqlQuery(this);
        }
    }
}
```

### SQL Query Usage

```java
public class SqlQueryBuilderDemo {
    public static void main(String[] args) {
        // Simple query
        SqlQuery query1 = new SqlQuery.SqlQueryBuilder()
                .select()
                .from("users")
                .build();

        System.out.println(query1.toSql());
        // Output: SELECT * FROM users

        // Complex query
        SqlQuery query2 = new SqlQuery.SqlQueryBuilder()
                .select()
                .from("users")
                .columns("id", "name", "email")
                .where("age > 18")
                .where("status = 'active'")
                .orderBy("name")
                .limit(10)
                .build();

        System.out.println(query2.toSql());
        // Output: SELECT id, name, email FROM users WHERE age > 18 AND status = 'active' ORDER BY name LIMIT 10
    }
}
```

## Builder Pattern Variations

### 1. Simple Builder (Most Common)

- Builder is a static nested class
- Fluent interface with method chaining
- Used in the Computer example above

### 2. Director-Based Builder

- Separate Director class orchestrates the building process
- Useful when you have complex construction algorithms

```java
public class ComputerDirector {
    public Computer buildGamingComputer(Computer.ComputerBuilder builder) {
        return builder
                .setGpu("High-end GPU")
                .setSoundCard("Premium Sound")
                .setBluetooth(true)
                .setWifi(true)
                .build();
    }

    public Computer buildOfficeComputer(Computer.ComputerBuilder builder) {
        return builder
                .setWifi(true)
                .setWebcam(true)
                .build();
    }
}
```

### 3. Generic Builder

- Can be used with any class
- Uses reflection or functional interfaces

```java
public class GenericBuilder<T> {
    private final Class<T> clazz;
    private final Map<String, Object> properties = new HashMap<>();

    public GenericBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public GenericBuilder<T> set(String property, Object value) {
        properties.put(property, value);
        return this;
    }

    public T build() {
        // Implementation using reflection or other mechanisms
        // This is a simplified example
        try {
            T instance = clazz.newInstance();
            // Set properties using reflection
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to build object", e);
        }
    }
}
```

## When to Use Builder Pattern

### Use Builder When:

1. **Complex Objects**: Object has many parameters (especially optional ones)
2. **Immutable Objects**: You want to create immutable objects
3. **Step-by-Step Construction**: Object construction requires multiple steps
4. **Validation**: Need to validate object state before creation
5. **Readability**: Constructor calls would be confusing or error-prone

### Don't Use Builder When:

1. **Simple Objects**: Object has few parameters
2. **Mutable Objects**: Object will be modified after creation
3. **Performance Critical**: Builder adds overhead
4. **Inheritance Heavy**: Complex inheritance hierarchies make builders complicated

## Advantages

1. **Flexibility**: Create different representations of the same object
2. **Readability**: Code is more readable and self-documenting
3. **Immutability**: Easy to create immutable objects
4. **Validation**: Centralized validation logic
5. **Optional Parameters**: Handle optional parameters elegantly
6. **Fluent Interface**: Method chaining improves code flow

## Disadvantages

1. **Code Duplication**: Builder duplicates object fields
2. **Memory Overhead**: Additional objects (builder instances)
3. **Complexity**: More complex than simple constructors
4. **Learning Curve**: Developers need to understand the pattern

## Practical Use Cases

1. **Configuration Objects**: Application settings, database configurations
2. **DTOs (Data Transfer Objects)**: Objects with many optional fields
3. **Test Data Builders**: Creating test objects with specific configurations
4. **API Request/Response Objects**: HTTP request builders, GraphQL queries
5. **UI Components**: Building complex UI elements with many properties
6. **Database Query Builders**: SQL, NoSQL query construction
7. **Document Builders**: PDF, HTML, XML document creation

## Best Practices

1. **Make Product Constructor Private**: Force object creation through builder
2. **Validate in Build Method**: Perform validation before creating the object
3. **Use Static Nested Class**: Keep builder close to the product class
4. **Return Builder from Setters**: Enable method chaining
5. **Consider Immutability**: Make product objects immutable
6. **Provide Defaults**: Set sensible default values for optional parameters
7. **Use Clear Method Names**: Make builder methods self-documenting

## Modern Java Features

### Understanding Records (Java 14+)

**What are Records?**
Records are a special kind of class introduced in Java 14 that are designed to be simple data carriers. They automatically generate:

- Constructor
- Getters (accessor methods)
- `equals()` and `hashCode()` methods
- `toString()` method

**Traditional Class vs Record:**

```java
// Traditional Class (lots of boilerplate code)
public class Computer {
    private final String cpu;
    private final String ram;
    private final String storage;

    public Computer(String cpu, String ram, String storage) {
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
    }

    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }

    @Override
    public boolean equals(Object obj) { /* lots of code */ }

    @Override
    public int hashCode() { /* code */ }

    @Override
    public String toString() { /* code */ }
}

// Record (much simpler - Java generates everything automatically!)
public record Computer(String cpu, String ram, String storage) {
    // That's it! Java automatically creates constructor, getters, equals, hashCode, toString
}
```

**Using Records with Builder Pattern:**

```java
public record Computer(
    String cpu,          // Required field
    String ram,          // Required field
    String storage,      // Required field
    String gpu,          // Optional field
    boolean bluetooth,   // Optional field
    boolean wifi         // Optional field
) {
    // You can still add a Builder inside a Record
    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String gpu = "Integrated";        // Default value
        private boolean bluetooth = false;        // Default value
        private boolean wifi = false;            // Default value

        // Fluent methods that return Builder for chaining
        public Builder cpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder ram(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder bluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public Builder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        // Build method creates the Record
        public Computer build() {
            if (cpu == null || ram == null || storage == null) {
                throw new IllegalArgumentException("CPU, RAM, and Storage are required");
            }
            // Record constructor takes all parameters in order
            return new Computer(cpu, ram, storage, gpu, bluetooth, wifi);
        }
    }
}

// Usage Example:
Computer computer = new Computer.Builder()
    .cpu("Intel i7")
    .ram("16GB")
    .storage("512GB SSD")
    .gpu("NVIDIA RTX 3060")
    .wifi(true)
    .build();

// Record automatically provides these methods:
System.out.println(computer.cpu());        // Getter method
System.out.println(computer.ram());        // Getter method
System.out.println(computer);              // toString() method
```

### Other Modern Java Features for Builder Pattern

#### 1. **Method References and Functional Interfaces (Java 8+)**

```java
// Using Function interface for validation
public class Computer {
    public static class Builder {
        private List<Function<Computer, Boolean>> validators = new ArrayList<>();

        public Builder addValidator(Function<Computer, Boolean> validator) {
            validators.add(validator);
            return this;
        }

        public Computer build() {
            Computer computer = new Computer(this);
            // Apply all validators
            validators.forEach(validator -> {
                if (!validator.apply(computer)) {
                    throw new IllegalArgumentException("Validation failed");
                }
            });
            return computer;
        }
    }
}

// Usage:
Computer computer = new Computer.Builder()
    .cpu("Intel i7")
    .addValidator(c -> c.getCpu() != null && !c.getCpu().isEmpty())
    .addValidator(c -> c.getRam() != null)
    .build();
```

#### 2. **Optional for Nullable Fields (Java 8+)**

```java
public class Computer {
    private final Optional<String> gpu;
    private final Optional<String> soundCard;

    public Optional<String> getGpu() { return gpu; }
    public Optional<String> getSoundCard() { return soundCard; }

    public static class Builder {
        private String gpu;
        private String soundCard;

        public Computer build() {
            return new Computer(
                Optional.ofNullable(gpu),
                Optional.ofNullable(soundCard)
            );
        }
    }
}

// Usage:
computer.getGpu().ifPresent(gpu -> System.out.println("GPU: " + gpu));
```

#### 3. **Builder with Generics and Type Safety**

```java
// Generic Builder that ensures type safety
public class TypeSafeBuilder<T> {
    private final Class<T> type;
    private final Map<String, Object> properties = new HashMap<>();

    public TypeSafeBuilder(Class<T> type) {
        this.type = type;
    }

    public <V> TypeSafeBuilder<T> set(String property, V value) {
        properties.put(property, value);
        return this;
    }

    public T build() {
        // Use reflection or other mechanisms to create object
        // This ensures type safety at compile time
        return createInstance();
    }

    private T createInstance() {
        // Implementation details...
        return null; // Simplified for explanation
    }
}
```

**Key Benefits of Modern Approach:**

1. **Less Boilerplate**: Records eliminate getter/setter/equals/hashCode code
2. **Immutability**: Records are naturally immutable
3. **Type Safety**: Generics and Optional provide better type safety
4. **Readability**: Modern syntax is more concise and readable
5. **Null Safety**: Optional helps handle nullable values better

## Conclusion

The Builder Design Pattern is a powerful creational pattern that provides a clean and flexible way to construct complex objects. It promotes code readability, handles optional parameters elegantly, and enables the creation of immutable objects with proper validation.

While it adds some complexity and code duplication, the benefits often outweigh the costs, especially for complex objects with many configuration options. The pattern is widely used in modern APIs and frameworks, making it an essential tool in a developer's toolkit.

The key to successful Builder implementation is knowing when to use it and following best practices to maximize its benefits while minimizing its drawbacks.
