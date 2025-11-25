w: ...
    if(...) {
        ...
        if(...) { // Fehler
            goto e
        }
        ...
        if(...) { // Schleifenende
            goto l
        }
    }
    ...
    goto w
l: ...
    goto x
e: ... //  Fehlerbehandlung
x: