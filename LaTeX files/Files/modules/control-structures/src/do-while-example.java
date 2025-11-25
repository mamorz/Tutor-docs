void sum() {
    int i;                             // Vorbereitung
    int sum = 0;
    do {                               // Schleife
        i = Terminal.askInt("i = ");   // Schleifenkoerper
        if (i != 0) {
            sum += i;
        }
    } while (i != 0);
    Terminal.println("sum = " + sum);  // Nachbereitung
}