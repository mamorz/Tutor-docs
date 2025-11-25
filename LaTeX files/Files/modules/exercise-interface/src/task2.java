public class Milch implements Essbar {
    public int brennwert() {
        return 70;
    }
    public double eiweiss() {
        return 3.3;
    }
    public double kohlenhydrate() {
        return 4.7;
    }
    public double fett() {
        return 4.2;
    }
}

public abstract class Pflanze { ... }
public class Paprika extends Pflanze implements Essbar { ... }