package src.MonopolyGame.IO;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import src.MonopolyGame.Const;

/**
 * This class manages the input and output of the game. It contains methods to print messages, read inputs and change the language.
 * 
 * <h4>Summary</h4>
 * <p>
 * Use the methods:
 * <ul>
 *   <li>{@code print(String msg)}: Print a raw message (<em>without checking the messages map</em>)</li>
 *   <li>{@code printMsg(String id)}: Print a message in the current language</li>
 *   <li>{@code printlnMsg(String id)}: Print a message in the current language with a new line</li>
 *   <li>{@code getMsg(String id)}: Get the value of a message in the current language</li>
 *   <li>{@code readInt(String prompt)}: Read an integer input</li>
 *   <li>{@code readInt(String prompt, int min, int max)}: Read an integer input within a range</li>
 *   <li>{@code readString(String prompt)}: Read a string input (using a message id as a prompt)</li>
 *   <li>{@code readString(String prompt, Object... params)}: Read a string input (using a message id as a prompt with parameters)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The printed values are <strong>padded</strong> with {@code Const.PRINT_PADDING} to make the output more readable and stand out from the rest of the terminal lines.
 * </p>
 * 
 * <p>
 * The debug messages are printed to the standard output only if the {@code debug} flag is set to {@code true}.
 * </p>
 * 
 * <p>
 * The attributes {@code debug} and {@code languageManager} are static, so they can be accessed from any instance of this class.
 * </p>
 */
public class IOManager implements Serializable {
  private static boolean debug = true; // Enables debug mode to print debug messages (True = on, False = off)
  private static LanguageManager languageManager = new LanguageManager(); // Language manager to get the messages from the language files
  private static Scanner scanner = new Scanner(System.in);

  /**
   * Print a raw message (<em>without checking the messages map</em>), basically a wrapper for {@code System.out.print()}.
   * 
   * @param msg The message to print 
   */
  public static void print(String msg) {
    System.out.print(Const.PRINT_PADDING + msg);
  }

  /**
   * Print a message in the current language.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   * @throws RuntimeException If the message id is not found in the messages map.
   */
  public static void printMsg(String id) throws RuntimeException {
    String msg = IOManager.languageManager.get(id); // Print the message, if it exists, otherwise throw an exception
    if (msg == null)
      throw new RuntimeException("Message not found for Id '" + id + "'\n");
    print(msg);
  }

  /**
   * Print a message in the current language with a new line.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   * @throws RuntimeException If the message id is not found in the messages map.
   */
  public static void printlnMsg(String id) {
    String msg = IOManager.languageManager.get(id); // Print the message, if it exists, otherwise throw an exception
    if (msg == null)
      throw new RuntimeException("Message not found for Id '" + id + "'\n");
    print(msg + '\n');
  }

  public static void printlnMsg(String id, Object... params) {
    print(String.format(getMsg(id), (Object[]) params) + '\n');
  }

  /**
   * Returns the message in the current language based on the message id.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   * @return The message in the current language.
   */
  public static String getMsg(String id) throws RuntimeException {
    String msg = IOManager.languageManager.get(id);

    if (msg == null)
      return id;

    return msg;
  }

  /**
    * Clear the screen
    */
  public static void cls() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (IOException | InterruptedException ex) {
    }
  }

  /**
   * Move the cursot up {@code lines} lines.
   * @param lines
   */
  public static void moveCursorUp(int lines) {
    System.out.print("\033[" + lines + "A");
  }

  /**
   * Move the cursot down {@code lines} lines.
   * @param lines The number of lines to move the cursor down.
   */
  public static void moveCursorDown(int lines) {
    System.out.print("\033[" + lines + "B");
  }

  /**
    * Ask the user a yes/no question.
    *
    * @param prompt The message ID that will be printed before reading the input.
    * @return True if the user typed 1 (yes), false otherwise (no).
    */
  public static boolean askYesNo(String prompt) {
    IOManager.print("\n");

    print(String.format("%s (%s=[1] / %s=[0])?\n", getMsg(prompt), getMsg("YES"), getMsg("NO")));

    int option = readInt("PROMPT_OPTION", 0, 1);

    return option == 1;
  }

  /**
   * Pause the program until the user presses the enter key.
   */
  public static void pause() {
    printMsg("PRESS_ENTER");
    scanner.nextLine();
  }

  /**
   * Read an input (integer). This method is a wrapper for {@code readInt(prompt)}.
   * 
   * @return The number typed by the user.
   */
  public static int readInt() {
    return readInt("");
  }

  /**
   * Read an input (string). Prints a prompt before reading the input.
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @return The number typed by the user.
   */
  public static int readInt(String prompt) {
    String input = readString(prompt);
    int value = Integer.parseInt(input);

    return value;
  }

  /**
   * Read an input (integer) within a range. This method is similar to {@code readInt()}, but it prints a prompt before reading the input and checks if the input is within a range.
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @param min The minimum value of the input.
   * @param max The maximum value of the input.
   * @return The number typed by the user.
   */
  public static int readInt(String prompt, int min, int max) {
    int option = readInt(prompt);
    while (option < min || option > max) {
      printlnMsg("INVALID_OPTION");
      option = readInt(prompt);
    }
    return option;
  }

  /**
   * Read an input (integer) within a range. (unformatted prompt)
   * @param min
   * @param max
   * @return
   */
  public static int readInt(int min, int max) {
    String input = "";

    // Read the input
    do {
      input = IOManager.scanner.nextLine();
    } while (input.length() == 0);

    return Integer.parseInt(input);
  }

  /**
   * Read an input (string). 
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @return The string typed by the user.
   */
  public static String readString(String prompt) {
    String input = "";

    prompt = getMsg(prompt);

    // Print the prompt
    print(prompt);

    input = IOManager.scanner.nextLine();

    return input;
  }

  /**
   * Read an input (string). This method accepts parameters to be used in the message if it contains placeholders.
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @param params The parameters of the message.
   * @return The string typed by the user.
   */
  public static String readString(String prompt, Object... params) {
    return readString(String.format(getMsg(prompt), (Object[]) params));
  }

  /**
   * Print a debug message to the standard output.
   * 
   * @param message The message to print (<em>can be any object, so the {@code toString()} method will be called</em>).
   */
  public static void log(Object message) {
    if (IOManager.debug) {
      System.out.print("[DEBUG] ");
      System.out.println(message);
    }
  }

  /**
   * Change the language of the game. The language must be the filename of one of the language files in the {@code Const.LANGUAGES_PATH} directory.
   * 
   * @param language The language to load. Example: {@code "English"}
   */
  public static void setLanguage(String language) {
    IOManager.languageManager.load(language);
  }

  // ---------------------------------------- Getters and Setters ----------------------------------------
  public static boolean isDebug() {
    return debug;
  }

  public static LanguageManager getLanguageManager() {
    return languageManager;
  }

  public static Scanner getScanner() {
    return scanner;
  }

  public static void setDebug(boolean debug) {
    IOManager.debug = debug;
  }

  public static void setLanguageManager(LanguageManager languageManager) {
    IOManager.languageManager = languageManager;
  }

  public static void setScanner(Scanner scanner) {
    IOManager.scanner = scanner;
  }
}
