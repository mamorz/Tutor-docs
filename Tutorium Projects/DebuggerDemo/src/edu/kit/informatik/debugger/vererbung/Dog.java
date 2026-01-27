package edu.kit.informatik.debugger.vererbung;

public class Dog extends Animal{

    public Dog(String optionalName) {
        super();
        this.name += " " + optionalName;
    }

    @Override
    public Animal sagWas() {
        System.out.println("Woof");
        return this;
    }

    public void foo() {

    }
}
