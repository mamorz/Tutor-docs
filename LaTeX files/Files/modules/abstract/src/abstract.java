public abstract class @@cFruit@@ {
    // Das geht auch in Interfaces!
    public abstract void consume();
}

public class @@cOrange@@ extends @@cFruit@@ {
    @@c@Override@@
    public void consume() {
        System.out.println("Peel first, then eat.");
    }
}
