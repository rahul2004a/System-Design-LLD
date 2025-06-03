# Strategy Design Pattern

## Overview

The Strategy Design Pattern is a behavioral design pattern that enables selecting an algorithm's behavior at runtime. It defines a family of algorithms, encapsulates each one, and makes them interchangeable. This pattern is particularly useful when you want to define multiple behaviors and switch between them dynamically without altering the context class.

## Key Components

1. **Strategy Interface**: Defines a common interface for all supported algorithms.
2. **Concrete Strategies**: Implement the strategy interface with specific behaviors.
3. **Context Class**: Maintains a reference to a strategy object and delegates the execution of the behavior to the strategy.

### Example Problem

Imagine a vehicle simulation system where different types of vehicles have different driving capabilities. Instead of hardcoding the driving logic for each vehicle type, the Strategy Design Pattern allows encapsulating the driving logic into separate strategy classes. This makes it easy to add new driving capabilities or modify existing ones without altering the vehicle classes.

### Problem Diagram

Below is a diagram illustrating the problem before applying the Strategy Design Pattern:

```
+------------------+
| Class Name -     |
| Vehicle          |
|------------------|
| Function 1()     |
| Function 2()     |
+------------------+
        ^
        |
+------------------+       +------------------+       +------------------+
| Sporty Vehicle   |       | Passenger Vehicle|       | Offroad Vehicle  |
| Overrides Func 1 |       | Uses same parent |       | Overrides Func 1 |
| same as Offroad  |       | func 1           |       |                  |
+------------------+       +------------------+       +------------------+
```

In this diagram, the `Vehicle` class has multiple child classes (`SportyVehicle`, `PassengerVehicle`, and `OffroadVehicle`) that override or reuse the same functions. This leads to code duplication and makes it difficult to add new behaviors without modifying the existing classes.

## Example Code

Below is an example implementation of the Strategy Design Pattern:

### Strategy Interface

```java
interface DriveStrategy {
    void drive();
}
```

### Concrete Strategies

```java
class NormalDriveStrategy implements DriveStrategy {
    @Override
    public void drive() {
        System.out.println("Normal driving capability");
    }
}

class SpecialDriveStrategy implements DriveStrategy {
    @Override
    public void drive() {
        System.out.println("Special driving capability");
    }
}
```

### Context Class

```java
class Vehicle {
    private DriveStrategy driveStrategy;

    // Constructor to initialize strategy
    public Vehicle(DriveStrategy driveStrategy) {
        this.driveStrategy = driveStrategy;
    }

    public void drive() {
        driveStrategy.drive();
    }
}
```

### Concrete Vehicles

```java
class PassengerVehicle extends Vehicle {
    public PassengerVehicle() {
        super(new NormalDriveStrategy()); // Uses normal drive
    }
}

class OffRoadVehicle extends Vehicle {
    public OffRoadVehicle() {
        super(new SpecialDriveStrategy()); // Uses special drive
    }
}

class SportyVehicle extends Vehicle {
    public SportyVehicle() {
        super(new SpecialDriveStrategy()); // Also uses special drive
    }
}
```

### Demo

```java
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Vehicle passenger = new PassengerVehicle();
        Vehicle offRoad = new OffRoadVehicle();
        Vehicle sporty = new SportyVehicle();

        passenger.drive();  // Output: Normal driving capability
        offRoad.drive();    // Output: Special driving capability
        sporty.drive();     // Output: Special driving capability
    }
}
```

## Diagram

Below is a diagram illustrating the Strategy Design Pattern:

```
+------------------+       +---------------------+
|   Vehicle        |       |   DriveStrategy     |
|------------------|       |---------------------|
| - driveStrategy  |<------| + drive()           |
|------------------|       |---------------------|
| + drive()        |       |                     |
+------------------+       +---------------------+
        ^                          ^
        |                          |
        |                          |
+------------------+       +---------------------+
| PassengerVehicle |       | NormalDriveStrategy |
+------------------+       +---------------------+
| OffRoadVehicle   |       | SpecialDriveStrategy|
+------------------+       +---------------------+
| SportyVehicle    |
+------------------+
```

## Advantages

- **Flexibility**: Allows switching between different algorithms dynamically.
- **Encapsulation**: Encapsulates the algorithm's implementation, making it easier to modify or extend.
- **Open/Closed Principle**: New strategies can be added without modifying the existing code.

## Use Cases

- When you have multiple algorithms for a specific task and want to choose the appropriate one at runtime.
- When you want to avoid conditional statements for selecting behaviors.

## Conclusion

The Strategy Design Pattern is a powerful tool for designing flexible and maintainable systems. By encapsulating algorithms and making them interchangeable, it promotes code reusability and simplifies the addition of new behaviors.
