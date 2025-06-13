// 1. Target Interface (what the client expects)
interface LegacyPrinter {
    void printDocument(String text);
}

// 2. Adaptee (the existing class with an incompatible interface)
class ModernPrinter {
    public void printHighQuality(String data) {
        System.out.println("Modern Printer: Printing high quality: " + data);
    }

    public void printDraft(String data) {
        System.out.println("Modern Printer: Printing draft: " + data);
    }
}

// 3. Adapter: Implements the Target interface and uses the Adaptee
class PrinterAdapter implements LegacyPrinter {
    private ModernPrinter modernPrinter;

    public PrinterAdapter(ModernPrinter modernPrinter) {
        this.modernPrinter = modernPrinter;
    }

    @Override
    public void printDocument(String text) {
        // Translate the LegacyPrinter's request into the ModernPrinter's method call
        System.out.println("Adapter: Converting legacy print request to modern.");
        modernPrinter.printHighQuality(text); // Or printDraft, depending on logic
    }
}

// 4. Client: Uses the Target interface
class AdapterPattern {
    private LegacyPrinter printer;

    public DocumentProcessor(LegacyPrinter printer) {
        this.printer = printer;
    }

    public void processAndPrint(String docContent) {
        System.out.println("Document Processor: Sending content to printer...");
        printer.printDocument(docContent);
    }

    public static void main(String[] args) {
        // Client uses the old interface
        LegacyPrinter oldPrinter = new LegacyPrinter() {
            @Override
            public void printDocument(String text) {
                System.out.println("Legacy Printer: " + text);
            }
        };
        DocumentProcessor processor1 = new DocumentProcessor(oldPrinter);
        processor1.processAndPrint("Hello from old printer!");

        System.out.println("\n--- Adapting a modern printer to a legacy interface ---");

        // Now, we have a ModernPrinter, but our DocumentProcessor expects LegacyPrinter.
        // We use the Adapter!
        ModernPrinter newModernPrinter = new ModernPrinter();
        LegacyPrinter adaptedPrinter = new PrinterAdapter(newModernPrinter); // The adapter
        DocumentProcessor processor2 = new DocumentProcessor(adaptedPrinter);
        processor2.processAndPrint("Hello from adapted modern printer!");
    }
}