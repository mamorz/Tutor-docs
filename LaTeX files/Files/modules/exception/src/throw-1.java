public void setMonth(int month) throws IllegalArgumentException {
    if ((month < 1) || (month > 12)) {
        throw new IllegalArgumentException("Wrong month: " + month);
    }
    this.month = month;
}
