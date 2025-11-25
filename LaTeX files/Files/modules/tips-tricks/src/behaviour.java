public class Article {
  public boolean sell(int quantity) {
    if (quantity > 0 && this.stock >= quantity) {
      this.stock -= quantity;
      return true;
    }
    return false;
  }
}

if (!article.sell(quantity)) {
  Terminal.printLine("SUCCESS!");
}