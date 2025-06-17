// 1. Strategy Interface: Defines the common interface for all algorithms
interface PaymentStrategy {
    void pay(double amount);
}

// 2. Concrete Strategies: Implement the Strategy interface with specific algorithms

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String name;

    public CreditCardPayment(String cardNumber, String name) {
        this.cardNumber = cardNumber;
        this.name = name;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid with Credit Card (Card: " + cardNumber + ", Name: " + name + ")");
        // Add actual credit card processing logic here
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid with PayPal (Email: " + email + ")");
        // Add actual PayPal processing logic here
    }
}

class BitcoinPayment implements PaymentStrategy {
    private String walletAddress;

    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid with Bitcoin (Wallet: " + walletAddress + ")");
        // Add actual Bitcoin transaction logic here
    }
}

// 3. Context: Uses the Strategy interface
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment strategy set. Cannot checkout.");
            return;
        }
        System.out.println("Processing checkout for amount: " + amount);
        paymentStrategy.pay(amount);
    }
}

// Demo Class
public class StrategyPattern {
    public static void main(String[] args) {
        System.out.println("--- Strategy Pattern Demo ---");

        ShoppingCart cart = new ShoppingCart();
        double orderAmount = 150.75;

        // Pay with Credit Card
        System.out.println("\nPaying with Credit Card:");
        PaymentStrategy creditCard = new CreditCardPayment("1234-5678-9012-3456", "John Doe");
        cart.setPaymentStrategy(creditCard);
        cart.checkout(orderAmount);

        // Pay with PayPal
        System.out.println("\nPaying with PayPal:");
        PaymentStrategy payPal = new PayPalPayment("john.doe@example.com");
        cart.setPaymentStrategy(payPal);
        cart.checkout(200.00); // New amount

        // Pay with Bitcoin
        System.out.println("\nPaying with Bitcoin:");
        PaymentStrategy bitcoin = new BitcoinPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
        cart.setPaymentStrategy(bitcoin);
        cart.checkout(50.50); // Another amount

        // Attempt checkout without a strategy
        System.out.println("\nAttempting checkout without strategy:");
        ShoppingCart emptyCart = new ShoppingCart();
        emptyCart.checkout(10.00);
    }
}
