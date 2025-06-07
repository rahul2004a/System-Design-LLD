# Abstract Factory Design Pattern

## Overview

- The Abstract Factory Pattern is a creational design pattern that provides an interface for creating families of related or dependent objects without specifying their concrete classes.
- It is often used when there are multiple types of objects that need to be created, and these objects are related or share common characteristics.

## Diagram

Below is a diagram illustrating the Abstract Factory Design Pattern:

```
+---------------------+       +-----------------------------+
| Abstract Factory    |       |         Abstract Product    |
|---------------------|       |-----------------------------|
| + createProductA()  |<------| + operationA()              |
| + createProductB()  |       | + operationB()              |
+---------------------+       +-----------------------------+
        ^                              ^
        |                              |
+---------------------+       +-----------------------------+
| Concrete Factory A  |       |     Concrete Product A      |
| Concrete Factory B  |       |     Concrete Product B      |
+---------------------+       +-----------------------------+
```

## Key Components

1. **Abstract Factory**: Defines an interface for creating abstract products.
2. **Abstract Product**: Defines an interface for a type of product.
3. **Concrete Factory**: Implements the abstract factory interface and creates concrete products.
4. **Concrete Product**: Implements the abstract product interface and defines specific behavior.

## Problem Statement

### What is the Problem?
In the automotive industry, manufacturers often need to produce different types of cars (e.g., Luxury and Ordinary) with distinct brands and features. For example:

1. **Luxury Cars**: Brands like Mercedes and BMW offer premium features and interiors.
2. **Ordinary Cars**: Brands like Swift and Honda focus on affordability and basic functionality.

Hardcoding the creation logic for these cars and their brands in the client code leads to:

1. **Tight Coupling**: The client code becomes dependent on specific car and brand classes.
2. **Reduced Maintainability**: Adding new car types or brands requires modifying the client code.
3. **Scalability Issues**: The system becomes harder to extend as the number of car types and brands increases.

### How Are We Solving It?
The Abstract Factory Design Pattern provides a solution by:

1. **Encapsulating Object Creation**: The `CarFactory` interface centralizes the logic for creating cars and their associated brands.
2. **Promoting Loose Coupling**: The client interacts with the factory and abstract products (`Car` and `Brand`), without knowing the specific classes.
3. **Improving Scalability**: Adding new car types or brands only requires extending the factory logic, without modifying the client code.

In the example code:

- The `LuxuryCarFactory` creates luxury cars and their associated brands (e.g., Mercedes).
- The `OrdinaryCarFactory` creates ordinary cars and their associated brands (e.g., Swift).
- The client code simply requests a car and its brand from the factory and uses them, without worrying about how they are created.

## Example Code

Below is an example implementation of the Abstract Factory Design Pattern using the concept of Luxury and Ordinary Cars:

### Abstract Product Interfaces

```java
public interface Car {
    void assemble();
}

public interface Brand {
    void showBrand();
}
```

### Concrete Products

```java
public class LuxuryCar implements Car {
    @Override
    public void assemble() {
        System.out.println("Assembling Luxury Car");
    }
}

public class OrdinaryCar implements Car {
    @Override
    public void assemble() {
        System.out.println("Assembling Ordinary Car");
    }
}

public class MercedesBrand implements Brand {
    @Override
    public void showBrand() {
        System.out.println("Brand: Mercedes");
    }
}

public class BMWBrand implements Brand {
    @Override
    public void showBrand() {
        System.out.println("Brand: BMW");
    }
}

public class SwiftBrand implements Brand {
    @Override
    public void showBrand() {
        System.out.println("Brand: Swift");
    }
}

public class HondaBrand implements Brand {
    @Override
    public void showBrand() {
        System.out.println("Brand: Honda");
    }
}
```

### Abstract Factory

```java
public interface CarFactory {
    Car createCar();
    Brand createBrand();
}
```

### Concrete Factories

```java
public class LuxuryCarFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new LuxuryCar();
    }

    @Override
    public Brand createBrand() {
        return new MercedesBrand();
    }
}

public class OrdinaryCarFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new OrdinaryCar();
    }

    @Override
    public Brand createBrand() {
        return new SwiftBrand();
    }
}
```

### Demo

```java
public class AbstractFactoryCarDemo {
    public static void main(String[] args) {
        CarFactory factory;

        // Determine the factory based on the car type
        String carType = "Luxury"; // Example input
        if (carType.equalsIgnoreCase("Luxury")) {
            factory = new LuxuryCarFactory();
        } else {
            factory = new OrdinaryCarFactory();
        }

        // Create car components
        Car car = factory.createCar();
        Brand brand = factory.createBrand();

        // Assemble car and show brand
        car.assemble();
        brand.showBrand();
    }
}
```

## Code Diagram

Below is the corrected code diagram for the Abstract Factory Design Pattern using Luxury and Ordinary Cars:

```
+---------------------+       +-----------------------------+
|     CarFactory      |       |         Car                 |
|---------------------|       |-----------------------------|
| + createCar()       |<------| + assemble()                |
| + createBrand()     |       |                             |
+---------------------+       +-----------------------------+
        ^                              ^
        |                              |
+---------------------+       +-----------------------------+
| LuxuryCarFactory    |       | LuxuryCar                  |
| OrdinaryCarFactory  |       | OrdinaryCar                |
+---------------------+       +-----------------------------+
| + createCar()       |       | + assemble()               |
| + createBrand()     |       |                             |
+---------------------+       +-----------------------------+
        ^                              ^
        |                              |
+---------------------+       +-----------------------------+
| MercedesBrand       |       | SwiftBrand                 |
| BMWBrand            |       | HondaBrand                 |
+---------------------+       +-----------------------------+
| + showBrand()       |       | + showBrand()              |
+---------------------+       +-----------------------------+
```

This example demonstrates how the Abstract Factory Design Pattern can be applied to create families of related car components.

## Advantages

1. Promotes loose coupling between client code and object creation logic.
2. Simplifies object creation logic for families of related objects.
3. Makes the system more scalable and maintainable.

## Practical Use Cases

1. GUI frameworks where different types of buttons and text fields are created based on the operating system.
2. Database connection creation based on the database type.
3. Parsing different file formats (e.g., XML, JSON) with related parsers.

## Conclusion

The Abstract Factory Design Pattern is a powerful tool for managing the creation of families of related objects in a scalable and maintainable way. It is widely used in software development to promote loose coupling and adhere to the Open/Closed Principle.
