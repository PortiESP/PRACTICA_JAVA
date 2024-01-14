package tests;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import src.MonopolyGame.IO.IOManager;

public class TestUtils {
  // Function to simulate the user typing a string in the console
  public static void stdinBuffer(String input) {
    // Set the input stream to the input string
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    IOManager.setScanner(new Scanner(System.in));
  }
}
