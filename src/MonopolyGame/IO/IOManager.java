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

  public String getMsg(String id) throws RuntimeException {
    String msg = this.languageManager.get(id);
    if (msg == null)
      throw new RuntimeException("Message not found for Id '" + id + "'\n");
    return msg;
  }

  // Print a message with a new line
  public void printlnMsg(String id) {
    String msg = this.languageManager.get(id); // Print the message, if it exists, otherwise throw an exception
    if (msg == null)
      throw new RuntimeException("Message not found for Id '" + id + "'\n");
    print(msg + '\n');
  }

  // Read an input (integer)
  public int readInt() {
    return this.scanner.nextInt();
  }

  // Read an input (integer)
  public int readInt(String prompt) {
    print("[>] " + getMsg(prompt) + " >>> ");
    return this.scanner.nextInt();
  }

  // Read an input (integer) with a range
  public int readInt(String prompt, int min, int max) {
    int option = readInt(prompt);
    while (option < min || option > max) {
      printlnMsg("INVALID_OPTION");
      option = readInt(prompt);
    }
    return option;
  }

  // Read an input (string)
  public String readString(String prompt) {
    print("[>] " + getMsg(prompt) + " >>> ");
    String input = this.scanner.nextLine();
    // Flush the scanner
    while (input.length() == 0) {
      input = this.scanner.nextLine();
    }
    return input;
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
