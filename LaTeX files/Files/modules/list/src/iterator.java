public void someMethod(List list) {
    // erzeuge neuen Iterator
    Iterator iterator = list.iterator();

    while(iterator.hasNext()) {
        // bekomme das aktuelle Element
        Type element = iterator.next();

        // Element ausgeben und aus Liste entfernen
        System.out.println(element);
        iterator.remove();
    }
}