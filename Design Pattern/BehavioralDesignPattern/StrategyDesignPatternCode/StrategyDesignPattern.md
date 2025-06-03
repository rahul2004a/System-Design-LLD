# Strategy Design Pattern

## <span style="color:blue;">Overview</span>

The <span style="color:green;">Strategy Design Pattern</span> is a behavioral design pattern that enables selecting an algorithm's behavior at runtime. It defines a family of algorithms, encapsulates each one, and makes them interchangeable. This pattern is particularly useful when you want to define multiple behaviors and switch between them dynamically without altering the context class.

---

## <span style="color:blue;">Key Components</span>

1. <span style="color:purple;">Strategy Interface</span>: Defines a common interface for all supported algorithms.
2. <span style="color:purple;">Concrete Strategies</span>: Implement the strategy interface with specific behaviors.
3. <span style="color:purple;">Context Class</span>: Maintains a reference to a strategy object and delegates the execution of the behavior to the strategy.

---

## <span style="color:blue;">Problem Solved</span>

The Strategy Design Pattern solves the problem of having multiple conditional statements or hardcoded logic for selecting and executing different behaviors. By encapsulating algorithms into separate classes, it promotes code reusability, flexibility, and maintainability. It is particularly useful in scenarios where the behavior of a class needs to be changed dynamically at runtime.

### <span style="color:blue;">Example Problem</span>

Imagine a vehicle simulation system where different types of vehicles have different driving capabilities. Instead of hardcoding the driving logic for each vehicle type, the Strategy Design Pattern allows encapsulating the driving logic into separate strategy classes. This makes it easy to add new driving capabilities or modify existing ones without altering the vehicle classes.

### <span style="color:blue;">Problem Diagram</span>

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

---

## <span style="color:blue;">Example Code</span>

### <span style="color:green;">Strategy Interface</span>

```java
interface DriveStrategy {
    void drive();
}
```

### <span style="color:green;">Concrete Strategies</span>

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

### <span style="color:green;">Context Class</span>

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

### <span style="color:green;">Concrete Vehicles</span>

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

### <span style="color:green;">Demo</span>

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

---

## <span style="color:blue;">Diagram</span>

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

---

## <span style="color:blue;">Advantages</span>

- <span style="color:green;">Flexibility</span>: Allows switching between different algorithms dynamically.
- <span style="color:green;">Encapsulation</span>: Encapsulates the algorithm's implementation, making it easier to modify or extend.
- <span style="color:green;">Open/Closed Principle</span>: New strategies can be added without modifying the existing code.

---

## <span style="color:blue;">Use Cases</span>

- When you have multiple algorithms for a specific task and want to choose the appropriate one at runtime.
- When you want to avoid conditional statements for selecting behaviors.

---

## <span style="color:blue;">Conclusion</span>

The Strategy Design Pattern is a powerful tool for designing flexible and maintainable systems. By encapsulating algorithms and making them interchangeable, it promotes code reusability and simplifies the addition of new behaviors.
