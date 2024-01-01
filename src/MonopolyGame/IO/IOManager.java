package src.MonopolyGame.IO;

import java.util.Scanner;

public class IOManager {
  private boolean debug = true;
  private LanguageManager languageManager;
  private Scanner scanner = new Scanner(System.in);

  // Constructor
  public IOManager() {
    this.languageManager = new LanguageManager();
  }

  // Print a message
  public void print(String id) {
    System.out.println(this.languageManager.get(id) || id); // Print the message, if it exists, otherwise print the message id
  }

  // Read an input (integer)
  public int readInt() {
    return this.scanner.nextInt();
  }

  // Print debug messages
  public static void log(String message) {
    if (this.debug)
      System.out.println("[DEBUG] " + message);
  }
}
