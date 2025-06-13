// 1. Prototype Interface
interface Prototype {
    Prototype clone();
}

// 2. Concrete Prototype
class Document implements Prototype {
    private String content;
    private String author; // Example of a complex/referenced field

    public Document(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }

    @Override
    public Prototype clone() {
        // This is a shallow copy. For a deep copy, 'author' object would also need cloning.
        try {
            return (Document) super.clone(); // Uses default Object.clone()
        } catch (CloneNotSupportedException e) {
            // This should not happen if Cloneable is implemented
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Document [Content: '" + content + "', Author: '" + author + "']";
    }
}

// 3. Client
class PrototypePattern {
    public static void main(String[] args) {
        // Create a prototype document
        Document originalDoc = new Document("Initial draft content.", "Alice");
        System.out.println("Original: " + originalDoc);

        // Clone the original to create a new document
        Document revision1 = (Document) originalDoc.clone();
        revision1.setContent("Revised content for review.");
        System.out.println("Revision 1: " + revision1);

        // Clone again for another version
        Document finalVersion = (Document) originalDoc.clone();
        finalVersion.setContent("Final approved content.");
        System.out.println("Final Version: " + finalVersion);

        System.out.println("\nOriginal after cloning: " + originalDoc);
        // Note: originalDoc remains unchanged if cloning is done correctly.
    }
}