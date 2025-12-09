package edu.kit.informatik.debugger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loop {

  public static final int MAX_NUMBER = (int) 1E5;

  public static void main(String[] args) {
    //What the heck does this do?
    List<Integer> intList = Stream.iterate(0, i -> i + 1)
            .limit(MAX_NUMBER)
            .toList();

    //iterate over something
    for (int i : intList) {
      // call helper method
      Recursion.fizzbuzz(i);
    }
  }
}
