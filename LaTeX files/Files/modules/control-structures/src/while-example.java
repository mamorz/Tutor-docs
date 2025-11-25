int sum(int from, int to) {
    int i = from;       // Vorbereitung
    int sum = 0;
    while (i <= to) {   // Schleifenkopf
        sum += i;       // Schleifenkoerper
        i++;
    }
    return sum;         // Nachbereitung
}
