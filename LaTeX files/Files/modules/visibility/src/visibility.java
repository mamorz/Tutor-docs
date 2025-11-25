class Car {
    public String manufacturer; // Public
    private String name;        // nur innerhalb Klasse
}

class Test {
    Car car = new Car();        // neues Objekt erzeugen
    car.manufacturer = "Audi";  // Attributzugriff erlaubt
    car.name = "TT";            // FEHLER!
}
