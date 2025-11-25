int sum (int from, int to) {
    int sum = 0;                        // Vorbereitung
    for (int i = from; i <= to; i++) {  // Schleifenkopf
        sum += i;                       // Schleifenkoerper
    }
    return sum;                         // Nachbereitung
}
