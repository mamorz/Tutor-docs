public class Dog extends Pet {
    public void getName() {
        super.getName();
    }

    // Das gilt auch fuer den Konstruktor
    public Dog(String name) {
        super(name);
    }
}