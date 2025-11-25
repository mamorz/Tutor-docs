if ((month < 1) || (month > 12)) {
    throw new IllegalArgumentException("Wrong month: %s", month);
}
switch (month) {
    case 1: break; // ...
    default: throw new Error();
}