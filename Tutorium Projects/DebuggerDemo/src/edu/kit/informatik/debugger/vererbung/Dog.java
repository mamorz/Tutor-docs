package edu.kit.informatik.debugger.vererbung;

public class Dog extends Animal{

    @Override
    public Animal sagWas() {
        System.out.println("Woof");
        return this;
    }

    public void foo() {

    }
}
