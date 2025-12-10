public class Mutter {
   public void anstrengend(int k) {
      System.out.println(k + "Stueck Kuchen sind genug!");
   }
}

public class Oma extends Mutter {
   @Override
   public void anstrengend(int j) {
      System.out.println("Nimm doch noch " + j + " Muffins.");
   }
}

public class Uroma extends Oma {
   @Override
   public void anstrengend(int i) {
      System.out.println("Nimm doch noch "+ i +" Stueck Kuchen.");
   }
}
