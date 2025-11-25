public class Person {

    protected String name;
    protected int alter;
    protected Adresse adresse;

    public Person(String name, int alter, Adresse adresse) {
        this.name = name;
        this.alter = alter;
        this.Adresse = adresse;
    }

    public Person() {
    }
    protected void eat() {
        System.out.println("MAMPF!");
    }

    protected void sleep() {
        System.out.println("zzZZ!");
    }
}

public class Student extends Person {

    private int matriclenummer;
    private boolean isIdiot;

    public Student() {
    }
}

public class Lecturer extends Person {
    private boolean hasVorlesung = true;
    private int id;
}

public interface Adresse {
}

public class Postanschrift implements Adresse {
    private String straße;
    private String hausnummer;
    private int plz;
    private String ort;
    private String land;
}

public class Postfach implements Adresse {
    private String nummer;
    private int plz;
    private String ort;
    private String land;
}

