package paket;

public class Etwas {
    private char smth;
    private boolean bool;


    public Etwas() {
        bool = true;
    }


    @Override
    public String toString() {
        return "My name is Aleks " + smth;
    }


    public String toString(int a) {
        return "My name is Aleks and my number is " + a;
    }
}
