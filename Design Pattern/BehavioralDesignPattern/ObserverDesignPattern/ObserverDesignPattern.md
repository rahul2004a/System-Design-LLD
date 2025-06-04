# Observer Design Pattern

## Overview

The Observer Design Pattern is a behavioral design pattern that establishes a one-to-many relationship between objects. When the state of one object (the subject) changes, all its dependent objects (observers) are notified automatically. This pattern is particularly useful for implementing distributed event-handling systems.

## Diagram

Below is a diagram illustrating the Observer Design Pattern:

```
+---------------------+       +-----------------------------+
|     Observable      |       |         Observer            |
|---------------------|       |-----------------------------|
| + addSubscriber()   |<------| + update()                  |
| + removeSubscriber()|       |                             |
| + notifySubscribers()|      |                             |
+---------------------+       +-----------------------------+
        ^                              ^
        |                              |
+---------------------+       +-----------------------------+
| Concrete Observable |       |     Concrete Observer       |
+---------------------+       +-----------------------------+
```

## Key Components

1. **Subject (Observable)**: Maintains a list of observers and notifies them of any state changes.
2. **Observer**: Defines an interface for objects that should be notified of changes in the subject.
3. **Concrete Subject**: Implements the subject interface and maintains the state.
4. **Concrete Observer**: Implements the observer interface and updates its state based on notifications from the subject.

## Problem Statement

The problem addressed by the example code is the need for a notification system that alerts users when the stock of a product (e.g., iPhone) becomes available. The system should:

1. Allow multiple users to subscribe for stock availability notifications.
2. Notify all subscribed users automatically when the stock count changes from 0 to a positive value.
3. Support different notification methods, such as email and SMS.

This problem is solved using the Observer Design Pattern, where the stock availability is the subject (observable), and the users are the observers.

## Example Code

Below is an example implementation of the Observer Design Pattern:

### Observable Interface

```java
interface IPhoneObservable {
    void addSubscriber(NotificationAlertObserver observer);
    void removeSubscriber(NotificationAlertObserver observer);
    void notifySubscribers();
    void setStockCount(int newStockAdded);
    int getStockCount();
}
```

### Concrete Observable

```java
class StocksObservable implements IPhoneObservable {
    private final List<NotificationAlertObserver> observers = new ArrayList<>();
    private int stockCount = 0;

    @Override
    public void addSubscriber(NotificationAlertObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeSubscriber(NotificationAlertObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
        for (NotificationAlertObserver observer : observers) {
            observer.update();
        }
    }

    @Override
    public void setStockCount(int newStockAdded) {
        if (stockCount == 0 && newStockAdded > 0) {
            stockCount += newStockAdded;
            notifySubscribers(); // Notify only when stock changes from 0→available
        } else {
            stockCount += newStockAdded;
        }
    }

    @Override
    public int getStockCount() {
        return stockCount;
    }
}
```

### Observer Interface

```java
interface NotificationAlertObserver {
    void update();
}
```

### Concrete Observers

```java
class EmailAlertObserverImpl implements NotificationAlertObserver {
    private final String email;
    private final IPhoneObservable observable;

    public EmailAlertObserverImpl(String email, IPhoneObservable observable) {
        this.email = email;
        this.observable = observable;
    }

    @Override
    public void update() {
        sendEmail();
    }

    private void sendEmail() {
        System.out.println("📧 Email sent to: " + email +
                          " | Stock available: " + observable.getStockCount());
    }
}

class MobileAlertObserverImpl implements NotificationAlertObserver {
    private final String mobileNumber;
    private final IPhoneObservable observable;

    public MobileAlertObserverImpl(String mobileNumber, IPhoneObservable observable) {
        this.mobileNumber = mobileNumber;
        this.observable = observable;
    }

    @Override
    public void update() {
        sendSMS();
    }

    private void sendSMS() {
        System.out.println("📱 SMS sent to: " + mobileNumber +
                         " | Hurry! iPhone stock: " + observable.getStockCount());
    }
}
```

### Demo

```java
public class StockAlertSystem {
    public static void main(String[] args) {
        // Create subject
        StocksObservable iphoneStock = new StocksObservable();

        // Create observers
        NotificationAlertObserver emailAlert =
            new EmailAlertObserverImpl("user1@example.com", iphoneStock);
        NotificationAlertObserver mobileAlert =
            new MobileAlertObserverImpl("+1234567890", iphoneStock);

        // Register observers
        iphoneStock.addSubscriber(emailAlert);
        iphoneStock.addSubscriber(mobileAlert);

        // Stock update scenario
        iphoneStock.setStockCount(10); // Triggers notifications
        iphoneStock.setStockCount(0);  // No notifications
        iphoneStock.setStockCount(5);  // Triggers notifications again
    }
}
```

### Code Diagram

Below is the corrected code diagram for the Observer Design Pattern:

```
+---------------------+       +-----------------------------+
| IPhoneObservable    |       | NotificationAlertObserver   |
|---------------------|       |-----------------------------|
| + addSubscriber()   |<------| + update()                  |
| + removeSubscriber()|       |                             |<---------------------
| + notifySubscribers()|      |                             |                     |
| + setStockCount()   |       |                             |                     |
| + getStockCount()   |       |                             |                     |
+---------------------+       +-----------------------------+                     |
        ^                              ^                                          |
        |                              |                                          |
+---------------------+       +-----------------------------+          +-------------------------+
| StocksObservable    |       | EmailAlertObserverImpl      |          | MobileAlertObserverImpl |
|---------------------|       |-----------------------------|          |-------------------------|
| - observers         |       | - email                     |          |- mobileNumber           |
| - stockCount        |       | - observable                |          |- observable             |
| + addSubscriber()   |       | + update()                  |          |+ update()               |
| + removeSubscriber()|       | + sendEmail()               |          |+ sendSMS()              |
| + notifySubscribers()|      |                             |          |                         |
| + setStockCount()   |       |                             |          +-------------------------|
| + getStockCount()   |       |                             |                       |
+---------------------+       +-----------------------------+                       |
                                       |                                            |
                                       |                        ---------------------
                                       |                        |
                                      +-----------------------------+
                                      | StockAlertSystem            |
                                      |-----------------------------|
                                      | - main()                    |
                                      |                             |
                                      |                             |
                                      |                             |
                                      +-----------------------------+
```

## Advantages

- **Loose Coupling**: Observers and subjects are loosely coupled, making the system more flexible and easier to maintain.
- **Scalability**: New observers can be added without modifying the subject.
- **Event Handling**: Ideal for implementing event-driven systems.

## Use Cases

- **Stock Market Monitoring**: Notify investors when stock prices change.
- **Notification Systems**: Send alerts when certain conditions are met.
- **GUI Frameworks**: Update UI components when the underlying data changes.

## Conclusion

The Observer Design Pattern is a powerful tool for building systems where multiple objects need to stay updated with changes in a subject. By decoupling the subject and observers, it promotes flexibility and scalability.
