package src.MonopolyGame.IO;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import src.MonopolyGame.Const;

/**
 * This class manages the input and output of the game. It contains methods to print messages, read inputs and change the language.
 * 
 * <h4>Language</h4>
 * <p>
 * The language of the game is loaded from the {@code Const.LANGUAGES_PATH} directory. The default language is {@code "English"}.
 * </p>
 * <p>
 * Every message will be tried to be loaded from the language file. If the message is not found, the message id will be printed instead.
 * </p>
 * <p>
 * The language of the game can be changed with the method {@code setLanguage(String language)}. The language must be the filename of one of the language files in the {@code Const.LANGUAGES_PATH} directory.
 * </p>
 * 
 * <h4>Message padding</h4>
 * <p>
 * The printed values are <strong>padded</strong> with {@code Const.PRINT_PADDING} to make the output more readable and stand out from the rest of the terminal lines.
 * </p>
 * 
 * <h4>Debug messages</h4>
 * <p>
 * The debug messages are printed to the standard output only if the {@code debug} flag is set to {@code true}.
 * </p>
 * 
 * <h4>Class foundations</h4>
 * <p>
 *  The class uses the the method {@code print(String msg)} as the foundation of all the other methods. This method is a wrapper for {@code System.out.print()} and prints a message to the standard output.
 * </p>
 * 
 */
public class IOManager implements Serializable {
  private static boolean debug = false; // Enables debug mode to print debug messages (True = on, False = off)
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
   * Print a message in the current language. If the message is not found, the message id will be printed instead.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   */
  public static void printMsg(String id) {
    String msg = IOManager.languageManager.get(id); // Print the message, if it exists, otherwise use the id as the message
    if (msg == null)
      msg = id;
    print(msg);
  }

  /**
   * Print a message in the current language with a new line. If the message is not found, the message id will be printed instead.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   */
  public static void printlnMsg(String id) {
    String msg = IOManager.languageManager.get(id); // Print the message, if it exists, otherwise use the id as the message
    if (msg == null)
      msg = id;
    print(msg + '\n');
  }

  /**
   * Print a formatted message in the current language with a new line. If the message is not found, the message id will first format the id and then print the result printed.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   * @param params The parameters of the message.
   */
  public static void printlnMsg(String id, Object... params) {
    print(String.format(getMsg(id), (Object[]) params) + '\n');
  }

  /**
   * Returns the message in the current language based on the message id. If the message is not found, the message id will be returned instead.
   * 
   * @param id The message id. Example: {@code "NEW_GAME"}
   * @return The message in the current language, or the message id if the message is not found.
   */
  public static String getMsg(String id) throws RuntimeException {
    String msg = IOManager.languageManager.get(id);

    if (msg == null)
      return id;

    return msg;
  }

  /**
   * Clears the screen.
   * 
   * <p>
   * <strong>NOTE:</strong> This method uses the {@code "os.name"} property to check if the operating system is Windows. If it is, it will use the {@code "cls"} command to clear the screen. Otherwise, it will use the ANSI escape code {@code "\033[H\033[2J"}.
   * </p>
   */
  public static void cls() {
    // Print a few new lines to clear the screen
    for (int i = 0; i < 100; i++)
      print("\n");

    // Clear the screen (moves the cursor to the top left corner)
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
   * Move the cursor up {@code lines} lines. 
   * 
   * <p>
   * <strong>NOTE:</strong> This method uses the ANSI escape code {@code "\033[A"} to move the cursor up.
   * </p>
   * 
   * @param lines
   */
  public static void moveCursorUp(int lines) {
    System.out.print("\033[" + lines + "A");
  }

  /**
   * Move the cursor down {@code lines} lines.
   * 
   * <p>
   * <strong>NOTE:</strong> This method uses the ANSI escape code {@code "\033[B"} to move the cursor down.
   * </p>
   * 
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
   * 
   * <p>
   * <strong>NOTE:</strong> This method uses the ANSI escape code {@code "\033[8m\033[?25l"} to hide the cursor and disable echo. After the user presses the enter key, it will use the ANSI escape code {@code "\033[28m\033[?25h"} to reset the cursor and enable echo.
   * </p>
   * 
   */
  public static void pause() {

    printMsg("PRESS_ENTER");
    System.out.print("\033[8m\033[?25l"); // Hide the cursor and disable echo 
    scanner.nextLine();
    System.out.print("\033[28m\033[?25h"); // Reset the cursor and enable echo 
  }

  /**
   * Read an input (integer). This method is a wrapper for {@code readInt(prompt)}.
   * 
   * @return The number typed by the user.
   * @throws NumberFormatException If the input is not a number.
   */
  public static int readInt() throws NumberFormatException {
    return readInt("");
  }

  /**
   * Read an input (string). Prints a prompt before reading the input.
   * 
   * <p>
   * <strong>NOTE:</strong> This method uses the readString() method as a single source of input and to avoid retrieving data from the buffer leaving the '\n' character in the buffer.
   * </p>
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @return The number typed by the user.
   * @throws NumberFormatException If the input is not a number.
   */
  public static int readInt(String prompt) throws NumberFormatException {
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
   * @throws NumberFormatException If the input is not a number.
   */
  public static int readInt(String prompt, int min, int max) throws NumberFormatException {
    int option = readInt(prompt);
    while (option < min || option > max) {
      printlnMsg("INVALID_OPTION");
      option = readInt(prompt);
    }
    return option;
  }

  /**
   * Read an input (string). 
   * 
   * @param prompt The message ID that will be printed before reading the input.
   * @return The string typed by the user.
   */
  public static String readString(String prompt) {
    // Try to translate the prompt if it exists, otherwise use the prompt as the message
    prompt = getMsg(prompt);
    // Print the prompt
    print(prompt);

    return IOManager.scanner.nextLine();
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
   * <p>
   * <strong>NOTE 1:</strong> This method will only print the message if the {@code debug} flag is set to {@code true}.
   * </p>
   * <p>
   * <strong>NOTE 2:</strong> This method doesn't use the {@code print()} method.
   * </p>
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
   * Change the language loaded in the map of messages. 
   * 
   * <p>
   * The language must be the filename of one of the language files in the {@code Const.LANGUAGES_PATH} directory.
   * </p>
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
