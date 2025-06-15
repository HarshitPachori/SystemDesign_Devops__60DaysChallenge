// 1. Component Interface
interface Coffee {
    String getDescription();
    double getCost();
}

// 2. Concrete Component
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 5.0;
    }
}

// 3. Decorator Abstract Class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }

    // Delegate to the decorated coffee
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

// 4. Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Milk";
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Sugar";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}

class CaramelDecorator extends CoffeeDecorator {
    public CaramelDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Caramel";
    }

    @Override
    public double getCost() {
        return super.getCost() + 2.0;
    }
}

// Client Code
public class DecoratorPattern {
    public static void main(String[] args) {
        // Order a simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println("Order 1: " + coffee.getDescription() + " Cost: $" + coffee.getCost());

        // Order a coffee with milk
        Coffee milkCoffee = new MilkDecorator(new SimpleCoffee());
        System.out.println("Order 2: " + milkCoffee.getDescription() + " Cost: $" + milkCoffee.getCost());

        // Order a coffee with milk and sugar
        Coffee milkAndSugarCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("Order 3: " + milkAndSugarCoffee.getDescription() + " Cost: $" + milkAndSugarCoffee.getCost());

        // Order a coffee with caramel, milk, and sugar
        Coffee complexCoffee = new SugarDecorator(new MilkDecorator(new CaramelDecorator(new SimpleCoffee())));
        System.out.println("Order 4: " + complexCoffee.getDescription() + " Cost: $" + complexCoffee.getCost());
    }
}