package edu.kit.informatik.debugger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loop {

  public static final int MAX_NUMBER = (int) 1E5;

  private void readFile(String path) {
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
          String lineToCheck;
          while ((lineToCheck = bufferedReader.readLine()) != null) {

          }
      } catch (IOException e) {
          System.err.println("There was an exception with message: " + e.getMessage());
      }


//    try { // Environment where exceptions are treated, without instantly crashing the program
//        readLines = Files.readAllLines(Path.of(path));
//    } catch (IOException exception) { // If the specified exception is thrown, then go here
//        System.err.println("Could not read file in " + path);
//    }

  }


  public static void main(String[] args) {
//    What the heck does this do?
    List<Integer> intList = Stream.iterate(0, i -> i + 1)
            .limit(MAX_NUMBER)
            .toList();

    //iterate over something
    for (int i : intList) {
      // call helper method
      Recursion.fizzbuzz(i);
    }


    System.out.println(new Loop().toString());
  }

  @Override
  public String toString() {
    return "My name is Aleks";
  }
}
