try {
    FileReader fr = new FileReader(".test");
    int nextChar = fr.read();
    while (nextChar != -1) {
        nextChar = fr.read();
    }
} catch (FileNotFoundException e) {
    System.out.println("Nicht gefunden.");
} catch (IOException e) {
    System.out.println("Ooops.");
}