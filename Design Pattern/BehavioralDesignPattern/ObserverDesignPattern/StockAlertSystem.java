import java.util.ArrayList;
import java.util.List;

// 1. iPhone Observable Interface
interface IPhoneObservable {
    void addSubscriber(NotificationAlertObserver observer);

    void removeSubscriber(NotificationAlertObserver observer);

    void notifySubscribers();

    void setStockCount(int newStockAdded);

    int getStockCount();
}

// 2. Concrete Stock Observable
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

// 3. Observer Interface
interface NotificationAlertObserver {
    void update();
}

// 4. Concrete Observers
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

// 5. Demo Usage
public class StockAlertSystem {
    public static void main(String[] args) {
        // Create subject
        StocksObservable iphoneStock = new StocksObservable();

        // Create observers
        NotificationAlertObserver emailAlert = new EmailAlertObserverImpl("user1@example.com", iphoneStock);
        NotificationAlertObserver mobileAlert = new MobileAlertObserverImpl("+1234567890", iphoneStock);

        // Register observers
        iphoneStock.addSubscriber(emailAlert);
        iphoneStock.addSubscriber(mobileAlert);

        // Stock update scenario
        iphoneStock.setStockCount(10); // Triggers notifications
        iphoneStock.setStockCount(0); // No notifications
        iphoneStock.setStockCount(5); // Triggers notifications again
    }
}