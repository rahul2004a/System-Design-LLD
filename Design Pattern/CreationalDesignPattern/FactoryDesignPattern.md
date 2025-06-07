# Factory Design Pattern

## Overview

- The Factory Pattern is a design pattern that provides a way to create objects without exposing the exact class name to the user.
- Instead of creating objects directly using the new keyword, you use a Factory class (or method) to get the object.
- It helps in managing object creation logic in one place and makes the code easier to maintain and extend.

## Diagram

Below is a diagram illustrating the Factory Design Pattern:

```
+---------------------+       +-----------------------------+
|       Factory       |       |         Product            |
|---------------------|       |-----------------------------|
| + createProduct()   |<------| + operation()               |
|                     |       |                             |
+---------------------+       +-----------------------------+
        ^                              ^
        |                              |
+---------------------+       +-----------------------------+
| Concrete Factory    |       |     Concrete Product        |
+---------------------+       +-----------------------------+
```

## Key Components

1. **Factory**: Defines a method for creating objects.
2. **Product**: The interface or abstract class for objects created by the factory.
3. **Concrete Factory**: Implements the factory interface and creates specific products.
4. **Concrete Product**: Implements the product interface and defines specific behavior.

## Problem Statement

### What is the Problem?

In many applications, we need to create objects dynamically based on user input or specific conditions. For example, in a graphics application, users may want to draw different shapes like circles or rectangles. Hardcoding the object creation logic in the client code leads to:

1. **Tight Coupling**: The client code becomes dependent on the specific classes.
2. **Reduced Maintainability**: Adding new shapes requires modifying the client code.
3. **Scalability Issues**: The system becomes harder to extend as the number of shapes increases.

### How Are We Solving It?

The Factory Design Pattern provides a solution by:

1. **Encapsulating Object Creation**: The `ShapeFactory` class centralizes the logic for creating shapes.
2. **Promoting Loose Coupling**: The client (`FactoryPatternDemo`) interacts with the factory and the `Shape` interface, without knowing the specific classes (`Circle`, `Rectangle`).
3. **Improving Scalability**: Adding new shapes only requires extending the factory logic, without modifying the client code.

In the example code:

- The `ShapeFactory` class determines which shape to create based on the input string.
- The client code simply requests a shape from the factory and uses it, without worrying about how it is created.

## Example Code

Below is an example implementation of the Factory Design Pattern:

### Product Interface

```java
public interface Shape {
    void draw();
}
```

### Concrete Products

```java
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}
```

### Factory

```java
public class ShapeFactory {
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        return null;
    }
}
```

### Demo

```java
public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        // Get an object of Circle and call its draw method
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();

        // Get an object of Rectangle and call its draw method
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        shape2.draw();
    }
}
```

## Code Diagram

Below is the corrected code diagram for the Factory Design Pattern:

```
+---------------------+       +-----------------------------+
|       Shape         |       |         ShapeFactory        |
|---------------------|       |-----------------------------|
| + draw()            |<------| + getShape(String)          |
|                     |       |                             |
+---------------------+       +-----------------------------+
        ^         ^---------------------
        |                              |
+---------------------+       +-----------------------------+
| Circle              |       | Rectangle                  |
+---------------------+       +-----------------------------+
| + draw()            |       | + draw()                   |
+---------------------+       +-----------------------------+
```

This diagram now accurately reflects the example code provided.

## Advantages

1. Promotes loose coupling between client code and object creation logic.
2. Simplifies object creation logic.
3. Makes the system more scalable and maintainable.

## Practical Use Cases

1. GUI frameworks where different types of buttons or windows are created.
2. Database connection creation based on the database type.
3. Parsing different file formats (e.g., XML, JSON).

## Conclusion

The Factory Design Pattern is a powerful tool for managing object creation in a scalable and maintainable way. It is widely used in software development to promote loose coupling and adhere to the Open/Closed Principle.
