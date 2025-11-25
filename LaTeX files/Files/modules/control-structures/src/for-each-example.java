int sum (int[] a) {
    int sum = 0;        // Vorbereitung
    for (int i : a) {   // Schleifenkopf
        sum += i;       // Schleifenkoerper
    }
    return sum;         // Nachbereitung
}