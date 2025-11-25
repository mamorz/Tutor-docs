f = openFile("input.txt");
if (f < 0) {
    System.out.println("Datei kann nicht geoeffnet werden");
    System.out.println("Grund: %s", f);
} else {
    // ...
}