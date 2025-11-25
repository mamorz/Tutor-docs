public class Math {
  public void setDenominator(int number) {
      this.denominator = number;
  }
  public int divide() {
    if (this.denominator == 0) throw
        new DivisionByZeroException();
    return this.numerator / this.denominator;
  }
}