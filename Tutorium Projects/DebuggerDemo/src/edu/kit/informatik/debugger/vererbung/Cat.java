package edu.kit.informatik.debugger.vererbung;

public class Cat extends Animal {

    @Override
    public Animal sagWas() {
        System.out.println("Meow");
        return this;
    }
}
