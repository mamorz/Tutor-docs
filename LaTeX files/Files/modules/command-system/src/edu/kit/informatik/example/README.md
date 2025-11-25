# Beispiel

Kleines Beispielprojekt für den Aufbau einer Abschlussaufgabe in Programmieren
mit kleiner Benutzerschnittstelle und Ansatz einer Datenhaltung.

## Hinweis

Das gezeigte Projekt ist nur ein Vorschlag und erhebt **nicht** den Anspruch
vollständig oder in irgendeiner Form ideal zu sein. Es sollte nur als Grundlage
im Programmieren-Modul benutzt werden, die Welt der Entwurfsmuster ist deutlich
größer und schöner!

Bei Fragen, Anregungen & sonstigen Kommentaren darfst du mir sehr gerne
schreiben!

## Aufbau

Im Wesentlichen sind fünf Bestandteile zu finden: Start, Benutzerschnittstelle,
Ausnahmen, Hilfsklassen & die eigentliche Implementierung.

Der Start in Form der main-Methode ist in der Klasse zu finden, die sich ganz
oben im Paket befindet und den Namen des Paketes trägt; hier also Example. Es
bietet sich meist an eine Utility-Klasse zu verwenden.

Die Benutzerschnittstelle liegt in UI, dort sind alle Zeichenketten des
Programms zu finden (*InteractionString*), was eine Benutzerschnittstelle können
sollte (*UserInterface*) und die eigentliche Benutzerschnittstelle im Unterpaket
— stattdessen könnte es auch ein Unterpaket für eine grafische Implementierung
oder einen Logger geben. Darauf hin folgt der Kern des Ganzen, das
Enum-Command-Pattern, wie sie in vielen Musterlösungen diesen Moduls zu finden
sind. Letzteres bitte ganz genau anschauen! Im *CommandlineInterface* ist auch
der einzige Ort für die Verwendung der Terminal-Klasse, alles andere wäre
Vermischung zwischen Logik & Implementierung.

Ausnahmen (engl. Exception) sind recht selbsterklärend und doch der Hinweis,
eigene Ausnahmen zu erstellen und nicht die der Java-API zu verwenden, schon gar
nicht Throwable, RuntimeException o.ä.

Hilfsklassen für bspw. Zeichenketten sind immer wieder notwendig und verdienen
dementsprechend ihr eigenes Unterpaket. Alternativ hier noch eine Klasse zur
Konvertierung verschiedener Bausteine (*Entity*) usw. Achtung, immer alles
Utility-Klassen!

Obiges ist der Grundbaustein, der Rahmen, für die nun kommende Implementierung,
die Logik, die Datenhaltung, das Spiel… Es lohnt sich eine eigene Schnittstelle
(engl. Interface) zu definieren, so ist gleich ersichtlich welche Teile
öffentlich sind und durch die Benutzerschnittstelle zu benutzen sind, zudem
abstrahiert sie und wir könnten verschiedene Datenimplementierung benutzen,
gerade zu Beginn unserer Entwicklung.

## Autor

Liebliches Beep Boop
Aurelia Hüll <aurelia.huell@fsmi.uni-karlsruhe.de>
