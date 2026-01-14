class A {
public void print() {System.out.println("A");}
}
class B extends A {
public void print() {System.out.println("B");}
}
public class Main {
public static void doPrint(A a) {
System.out.print("A ");
a.print();
}
public static void doPrint(B b) {
System.out.print("B ");
b.print();
}
public static void main(String[] args) {
A x = new A();
B y = new B();
A z = new B();
doPrint(x);
doPrint(y);
doPrint(z);
}
}