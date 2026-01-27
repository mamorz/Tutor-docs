package edu.kit.informatik.debugger;


public class Loop {


        public static void main(String[] args) {
            Parent parent = new Child();
            parent.g();
        }
  }
class Parent {
    protected int x = 41;
    void f() {
        System.out.println(x);
        h();
    }
    void g() {
        x = 20;
    }
    void h() {
        System.out.println(x);
    }
}

class Child extends Parent {
    protected int x = 17;
    void g() {
        f();
    }
    void h() {
        System.out.println(x);
    }
}
