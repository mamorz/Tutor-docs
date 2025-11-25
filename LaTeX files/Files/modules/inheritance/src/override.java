public class @@cMutter@@ {
   public void anstrengend(int k) {
      System.out.println(k + "Stueck Kuchen sind genug!");
   }
}

public class @@cOma@@ extends @@cMutter@@ {
   @@c@Override@@
   public void anstrengend(int j) {
      @@cSystem@@.out.println("Nimm doch noch " + j + " Muffins.");
   }
}

public class @@cUroma@@ extends @@cOma@@ {
   @@c@Override@@
   public void anstrengend(int i) {
      System.out.println("Nimm doch noch "+ i +" Stueck Kuchen.");
   }
}
