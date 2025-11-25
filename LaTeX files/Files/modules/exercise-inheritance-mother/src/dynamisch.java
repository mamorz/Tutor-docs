Mutter m = new Oma();
^^^^^^         ^^^^^^
statischer Typ   dynamischer Typ

m.anstrengend(3);//Ausgabe: Nimm doch noch 3 Muffins.
                 //Code aus Klasse Oma.
m = new Uroma();
m.anstrengend(3);//Ausgabe: Nimm doch noch 3 Stueck Kuchen.
                 //Code aus Klasse Uroma.