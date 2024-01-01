package src.MonopolyGame.IO;

import java.util.Scanner;

import src.MonopolyGame.Const;

public class IOManager {
  private static boolean debug = true;
  private LanguageManager languageManager;
  private Scanner scanner = new Scanner(System.in);

  // Constructor
  public IOManager() {
    this.languageManager = new LanguageManager();
  }

  // Print a message without checking the messages map
  public void print(String msg) {
    System.out.print(Const.PRINT_PADDING + msg);
  }

  // Print a message
  public void printMsg(String id) throws RuntimeException {
    String msg = this.languageManager.get(id); // Print the message, if it exists, otherwise throw an exception
    if (msg == null)
      throw new RuntimeException("Message not found for Id '" + id + "'\n");
    print(msg);
  }

  // Print a message with a new line
  public void println(String id) {
    printMsg(id + "\n");
  }

  // Read an input (integer)
  public int readInt() {
    return this.scanner.nextInt();
  }

  // Read an input (integer)
  public int readInt(String prompt) {
    print("[>] " + prompt + " >>> ");
    return this.scanner.nextInt();
  }

  // Print debug messages
  public static void log(String message) {
    if (IOManager.debug)
      System.out.println("[DEBUG] " + message);
  }

  // Change the language
  public void setLanguage(String language) {
    this.languageManager.load(language);
  }
}
