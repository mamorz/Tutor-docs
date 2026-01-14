class Main {
    public static void main(String[] args) {
        Parent parent = new Child();
        parent.g();
    }
}

class Parent {
    static int x = 41;
    void f() { System.out.println(x); h(); }
    void g() { x = 20; }
    void h() { System.out.println(x); }
}

class Child extends Parent {
    static int x = 17;
    void g() { f(); }
    void h() { System.out.println(x); }
}
