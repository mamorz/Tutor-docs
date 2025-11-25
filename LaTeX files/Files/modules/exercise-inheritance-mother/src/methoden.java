public class Kuchen {}
public class Marmorkuchen extends Kuchen {}
public class Mutter {
  public void backe(Kuchen k) {
    System.out.println("baeckt Kuchen.");
  }
  public void backe(Marmorkuchen m) {
    System.out.println("baeckt Marmorkuchen.");
  }
}
Mutter m = new Mutter();
Kuchen k1 = new Kuchen();
Kuchen k2 = new Marmorkuchen();
Marmorkuchen mk = new Marmorkuchen();
m.backe(k1); // Ausgabe: baeckt Kuchen.
m.backe(k2); // Ausgabe: baeckt Kuchen.
m.backe(mk); // Ausgabe: baeckt Marmorkuchen.