class Whatever {
    static int nextNumber = 0; // Bezug zur Klasse
    int myNumber; // Bezug zum Objekt

    public Whatever() {
        this.myNumber = nextNumber;
        nextNumber++;
        // Oder Whatever.nextNumber++; als direkte
        // Referenz der Attribute
        // DON'T: nextNumber = nextNumber + 1;
        // oder nextNumber +=1;
    }
}