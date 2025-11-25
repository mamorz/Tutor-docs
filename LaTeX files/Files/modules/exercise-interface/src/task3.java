public static void main(String... args) {
    Essbar[] essbares = new Essbar[3];
    essbares[0] = new Milch();
    essbares[1] = new Schweinesteak();
    essbares[2] = new Paprika();
    for (Essbar e : essbares) {
        System.out.println(e.fett());
    }
}