package edu.kit.informatik.debugger;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Recursion {
  public static final int MAX_NUMBER = 1000;

  public static void main(String[] args) {
    //What the heck does this do?
    List<Integer> intList = Stream.iterate(0, i -> i + 1)
            .limit(MAX_NUMBER)
            .toList();

    recurse(intList);

  }

  private static void recurse(List<Integer> intList) {
    //check if List is empty
    if (intList.isEmpty()) return;

    //pop first element
    int i = intList.remove(0);

    //print appropriate message
    fizzbuzz(i);

    //do recursion
    recurse(intList);
  }

  /**
   * Method to print fizz iff a number is divisible by 3 evenly, buzz if a number is divisible by
   * 5 evenly and FizzBuzz if both conditions apply.
   *
   * @param i The number to check
   */
  static void fizzbuzz(int i) {
    //do appropriate checks
    if (i % 3 == 0 && i % 5 == 0) {
      System.out.println(i + ": FizzBuzz");
    } else if (i % 3 == 0) {
      System.out.println(i + ": Fizz");
    } else if (i % 5 == 0) {
      System.out.println(i + ": Buzz");
    }
  }
}
