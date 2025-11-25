void setBalance(double b) {
    assert (b >= 0);
    this.balance = b;
}

void setBalance(double b) throws IllegalArgumentException {
    if (b < 0) {
        throw new IllegalArgumentException();
    }
    this.balance = b;
}