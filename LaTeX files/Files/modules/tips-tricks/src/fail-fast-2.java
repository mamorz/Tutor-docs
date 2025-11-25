public class Math {
  public void setDenominator(int number) {
      if (number == 0) throw new DivisionByZeroException();
      this.denominator = number;
  }
  public int divide() {
    return this.numerator / this.denominator;
  }
}