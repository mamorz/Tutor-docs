boolean quit, err;
while(!quit) {
    if(...) {
        ...
        if(...) { // Fehler
            quit, err = true;
        } else {
        ...
            if(...) { // Schleifenende
                quit = true;
            }
        }
    }
    if(!quit) {
        ...
    }
}
if(!err) {
    ...
} else {
    ... // Fehlerbehandlung
}