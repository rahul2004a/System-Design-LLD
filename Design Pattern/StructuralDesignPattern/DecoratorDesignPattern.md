# Decorator Design Pattern

## Introduction

The Decorator Design Pattern is a structural design pattern that allows behavior to be added to individual objects, either statically or dynamically, without affecting the behavior of other objects from the same class. The pattern is used to extend or alter the functionality of objects at runtime by wrapping them in an object of a decorator class.

## Problem Statement

Sometimes we need to add responsibilities to individual objects, not to an entire class. Inheritance is one way to add responsibilities, but it's static and applies to an entire class. The Decorator pattern provides a more flexible alternative to subclassing for extending functionality.

## Structure

### Components

1. **Component Interface/Abstract Class**: Defines the interface for objects that can have responsibilities added to them dynamically.
2. **Concrete Component**: The original object to which additional responsibilities can be attached.
3. **Decorator Abstract Class**: Maintains a reference to a Component object and defines an interface that conforms to Component's interface.
4. **Concrete Decorators**: Add responsibilities to the component. Each decorator wraps the component and adds its own behavior either before or after delegating to the component.

### UML Diagram

```
┌─────────────┐
│  Component  │  <------
└─────────────┘         |
       ▲                |
       │                |
┌──────┴──────┐         |
│             │         |
┌─────────────┐  ┌─────────────┐
│  Concrete   │  │  Decorator  │
│  Component  │  │ (Abstract)  │
└─────────────┘  └─────────────┘
                        ▲
                        │
                ┌───────┴───────┐
                │               │
        ┌───────────────┐ ┌───────────────┐
        │    Concrete   │ │    Concrete   │
        │  Decorator A  │ │  Decorator B  │
        └───────────────┘ └───────────────┘
```

## Implementation

### **1. Pizza Interface**

```java
public interface Pizza {
    double getCost();
    String getDescription();
}
```

### **2. Base Pizza (Concrete Component)**

```java
public class Margherita implements Pizza {
    @Override
    public double getCost() {
        return 6.0; // Base price
    }

    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }
}
```

### **3. Base Decorator (Abstract Topping Wrapper)**

```java
public abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public double getCost() {
        return pizza.getCost();
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }
}
```

### **4. Concrete Toppings (Decorators)**

```java
// Extra Cheese
public class ExtraCheese extends PizzaDecorator {
    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.5; // Cheese costs extra
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Extra Cheese";
    }
}

// Pepperoni
public class Pepperoni extends PizzaDecorator {
    public Pepperoni(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getCost() {
        return super.getCost() + 2.0; // Pepperoni costs extra
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Pepperoni";
    }
}

// Mushrooms
public class Mushrooms extends PizzaDecorator {
    public Mushrooms(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.0; // Mushrooms cost extra
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Mushrooms";
    }
}
```

### **5. Client Code (Ordering Pizza)**

```java
public class PizzaShop {
    public static void main(String[] args) {
        // Order a Margherita Pizza
        Pizza pizza = new Margherita();
        System.out.println(pizza.getDescription() + ": $" + pizza.getCost());

        // Add Extra Cheese
        pizza = new ExtraCheese(pizza);
        System.out.println(pizza.getDescription() + ": $" + pizza.getCost());

        // Add Pepperoni
        pizza = new Pepperoni(pizza);
        System.out.println(pizza.getDescription() + ": $" + pizza.getCost());

        // Add Mushrooms
        pizza = new Mushrooms(pizza);
        System.out.println(pizza.getDescription() + ": $" + pizza.getCost());
    }
}
```

### UML Diagram for Pizza Example

```
┌──────────────┐
│    Pizza     │ <── Interface
├──────────────┤
│ + getCost()  │
│ + getDesc()  │
└──────────────┘
        |
        |
┌───────┴───────────────────────────┐
|                                   |
|                                   |
┌──────────────┐          ┌──────────────────┐
│  Margherita  │          │  PizzaDecorator  │ <── Base Decorator
├──────────────┤          ├──────────────────┤
│ + getCost()  │          │ - pizza          │
│ + getDesc()  │          │ + getCost()      │
└──────────────┘          │ + getDesc()      │
                          └──────────────────┘
                                   |
                                   |
               ┌──────────────────┬┴─────────────────┐
               |                  |                  |
      ┌────────────────┐  ┌──────────────┐  ┌──────────────┐
      │  ExtraCheese   │  │  Pepperoni   │  │  Mushrooms   │
      ├────────────────┤  ├──────────────┤  ├──────────────┤
      │ + getCost()    │  │ + getCost()  │  │ + getCost()  │
      │ + getDesc()    │  │ + getDesc()  │  │ + getDesc()  │
      └────────────────┘  └──────────────┘  └──────────────┘
```

## Advantages

1. **Flexibility**: More flexible than static inheritance. The decorator pattern allows adding and removing responsibilities at runtime.
2. **Avoids Feature-Laden Classes**: Allows for a clean separation of features into individual classes instead of having a single class with all features.
3. **Open/Closed Principle**: Adheres to the open/closed principle by allowing extension without modification.
4. **Composition over Inheritance**: Uses object composition instead of inheritance for extensibility.

## Disadvantages

1. **Complexity**: Can lead to many small objects with similar structure but different behavior.
2. **Complex Configuration**: Complex systems with many decorators can become difficult to configure.
3. **Order Dependency**: The order in which decorators are applied can be significant.

## Real-World Applications

1. **Java I/O Classes**: Java's I/O streams are a classic example of the Decorator pattern (`BufferedInputStream`, `FileInputStream`, etc.).
2. **User Interface Components**: Adding borders, scrollbars, or behaviors to UI components.
3. **Web Services**: Adding authentication, logging, or caching to service requests.
4. **Food Ordering Systems**: Adding toppings or modifications to base food items.
5. **Middleware in Web Development**: Adding features like logging, authentication, and compression to HTTP requests.

## When to Use

- When you need to add responsibilities to objects dynamically and transparently.
- When extension by subclassing is impractical due to a large number of independent extensions.
- When you want to avoid a class hierarchy bloated with every possible combination of features.
- When you cannot modify the original class but need to extend its functionality.

## Conclusion

The Decorator Design Pattern is a powerful way to extend object functionality without modifying existing code. It's particularly useful in scenarios where a large number of combinations of behaviors are possible, and creating a separate class for each combination would be impractical. By using composition instead of inheritance, it offers greater flexibility and adheres to the open/closed principle.
