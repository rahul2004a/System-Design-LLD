// Strategy Interface
interface DriveStrategy {
    void drive();
}

// Concrete Strategies
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

// Context Class
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

// Concrete Vehicles
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

// Demo
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Vehicle passenger = new PassengerVehicle();
        Vehicle offRoad = new OffRoadVehicle();
        Vehicle sporty = new SportyVehicle();

        passenger.drive(); // Output: Normal driving capability
        offRoad.drive(); // Output: Special driving capability
        sporty.drive(); // Output: Special driving capability
    }
}