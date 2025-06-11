import java.util.Scanner;

// 1. Abstract Products: Interfaces for the "family" of products

interface Button {
    void paint();
}

interface Checkbox {
    void toggle();
}

// 2. Concrete Products: Implementations for each family (e.g., Windows GUI components)

class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in Windows style.");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void toggle() {
        System.out.println("Toggling a checkbox in Windows style.");
    }
}

// Concrete Products: Implementations for another family (e.g., macOS GUI components)

class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in macOS style.");
    }
}

class MacOSCheckbox implements Checkbox {
    @Override
    public void toggle() {
        System.out.println("Toggling a checkbox in macOS style.");
    }
}

// 3. Abstract Factory: Interface for creating families of products

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// 4. Concrete Factories: Implementations for each family

class WindowsGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacOSGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

// 5. Client: Uses the Abstract Factory and Abstract Products

class Application {
    private GUIFactory factory;
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    public void interact() {
        System.out.println("\nInteracting with UI components:");
        button.paint();
        checkbox.toggle();
    }
}

// Demo Class
public class AbstractFactoryPattern {
    public static void main(String[] args) {
        System.out.println("--- Abstract Factory Pattern Demo ---");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter OS type (Windows/MacOS): ");
        String osType = scanner.nextLine();

        GUIFactory factory = null;

        // The client code is decoupled from concrete factory implementations.
        // It only depends on the GUIFactory interface.
        if (osType.equalsIgnoreCase("Windows")) {
            factory = new WindowsGUIFactory();
        } else if (osType.equalsIgnoreCase("MacOS")) {
            factory = new MacOSGUIFactory();
        } else {
            System.out.println("Invalid OS type. Exiting.");
            scanner.close();
            return;
        }

        Application app = new Application(factory);
        app.createUI();
        app.interact();

        scanner.close();
    }
}
