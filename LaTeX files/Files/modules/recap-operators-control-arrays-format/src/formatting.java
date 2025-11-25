// Zu lang
int result = a.getA().getB().getC().getD().getE().getF();
// Nein!
int result = a.getA().getB().getC(
        ).getD().getE().getF();
// Okay
int result = a.getA().getB().getC()
    .getD().getE().getF();
// Okay
int result = a
    .getA()
    .getB()
    .getC() // und so weiter
