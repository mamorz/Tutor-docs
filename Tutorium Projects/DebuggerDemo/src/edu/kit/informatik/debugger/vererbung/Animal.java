package edu.kit.informatik.debugger.vererbung;

public class Animal {
    protected String name;


    public Animal() {
        this.name = "pizza";
    }

    public Animal sagWas() {
        System.out.println("hi");
        return this;
    }
}
