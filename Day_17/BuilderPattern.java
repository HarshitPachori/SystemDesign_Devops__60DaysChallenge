// 1. Product: The complex object to be built
class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String graphicsCard;
    private String operatingSystem;
    private boolean hasWebcam;

    // Private constructor to force use of the Builder
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.operatingSystem = builder.operatingSystem;
        this.hasWebcam = builder.hasWebcam;
    }

    // Static nested Builder class
    public static class Builder {
        // Required parameters (can be passed to builder constructor or set later)
        private String cpu;
        private String ram;
        private String storage;

        // Optional parameters (with default values or null)
        private String graphicsCard = "Integrated";
        private String operatingSystem = "None";
        private boolean hasWebcam = false;

        // Constructor for required parameters
        public Builder(String cpu, String ram, String storage) {
            this.cpu = cpu;
            this.ram = ram;
            this.storage = storage;
        }

        // Methods for setting optional parameters (return Builder for chaining)
        public Builder withGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder withOperatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Builder hasWebcam(boolean hasWebcam) {
            this.hasWebcam = hasWebcam;
            return this;
        }

        // Build method to create the final Computer object
        public Computer build() {
            // Optional: Add validation logic here before building
            if (this.cpu == null || this.ram == null || this.storage == null) {
                throw new IllegalStateException("CPU, RAM, and Storage are required.");
            }
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               "  CPU: " + cpu + "\n" +
               "  RAM: " + ram + "\n" +
               "  Storage: " + storage + "\n" +
               "  Graphics Card: " + graphicsCard + "\n" +
               "  OS: " + operatingSystem + "\n" +
               "  Webcam: " + (hasWebcam ? "Yes" : "No");
    }
}

// 2. Director (Optional): Encapsulates common building processes
class ComputerAssembler {
    public Computer buildGamingComputer(Computer.Builder builder) {
        return builder.withGraphicsCard("NVIDIA RTX 4090")
                      .withOperatingSystem("Windows 11")
                      .hasWebcam(true)
                      .build();
    }

    public Computer buildOfficeComputer(Computer.Builder builder) {
        return builder.withGraphicsCard("Integrated") // Explicitly set or rely on default
                      .withOperatingSystem("Windows 10")
                      .hasWebcam(false) // Explicitly set or rely on default
                      .build();
    }
}


// Demo Class
public class BuilderPattern {
    public static void main(String[] args) {
        System.out.println("--- Builder Pattern Demo ---");

        // 1. Building a simple computer directly using the builder
        System.out.println("Building Basic Computer:");
        Computer basicComputer = new Computer.Builder("Intel i3", "8GB DDR4", "256GB SSD").build();
        System.out.println(basicComputer);

        System.out.println("\n----------------------------------");

        // 2. Building a gaming computer step-by-step
        System.out.println("Building Custom Gaming Computer:");
        Computer gamingComputer = new Computer.Builder("AMD Ryzen 9", "32GB DDR5", "1TB NVMe SSD")
                                            .withGraphicsCard("AMD Radeon RX 7900 XTX")
                                            .withOperatingSystem("Windows 11")
                                            .hasWebcam(true)
                                            .build();
        System.out.println(gamingComputer);

        System.out.println("\n----------------------------------");

        // 3. Building a computer using a Director (for predefined configurations)
        System.out.println("Building Gaming Computer using Assembler (Director):");
        ComputerAssembler assembler = new ComputerAssembler();
        Computer standardGamingComputer = assembler.buildGamingComputer(
            new Computer.Builder("Intel i7", "16GB DDR4", "512GB SSD")
        );
        System.out.println(standardGamingComputer);

        System.out.println("\n----------------------------------");

        System.out.println("Building Office Computer using Assembler (Director):");
        Computer officeComputer = assembler.buildOfficeComputer(
            new Computer.Builder("Intel Celeron", "4GB DDR4", "128GB SSD")
        );
        System.out.println(officeComputer);
    }
}
