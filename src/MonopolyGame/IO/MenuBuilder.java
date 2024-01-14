package src.MonopolyGame.IO;

import java.util.Arrays;

/**
 * Build pretty CLIs.
 * 
 * <p>
 * This class is used to build pretty CLIs. It has methods to build menus, forms, alerts, docs, read inputs, etc.
 * <p>
 * 
 * <p>
 * <h4>Attributes</h4>
 * <ul>
 * <li>{@code clean}: If true, the screen will be cleared before printing the menu.</li>
 * <li>{@code MENU_WIDTH}: Width of the menus.</li>
 * <li>{@code formValues}: Used to store the values of the last form.</li>
 * <li>{@code configFormUniqueValues}: If true, the form will not allow repeated values.</li>
 * <li>{@code configLastAsZero}: If true, the last option of the menus will be 0 instead of N.</li>
 * </ul>
 * <p>
 */
public class MenuBuilder {

  private static boolean clean = true; // Clean the screen before printing the menu (DEBUG: Set to false to debug the menus (Every method will reset this value at the end))
  private final static int MENU_WIDTH = 100; // Width of the menus
  private static String[] formValues; // Used to store the values of the last form
  private static boolean configFormUniqueValues = false; // If true, the form will not allow repeated values
  private static boolean configLastAsZero = false; // If true, the last option of the menus will be 0 instead of N

  /**
   * Build a menu.
   * 
   * <p>
   * This method builds a menu with the given title and options. It will return the number selected option.
   * <p>
   * 
   * <p>
   * The options will be numbered from 1 to N, where N is the number of options. If the setting {@code configLastAsZero} is set to true, then the last option will be 0 instead of N.
   * <p>
   * 
   * @param title The title of the menu (translated if posible).
   * @param options Array of options of the menu (translated if posible).
   * @return The number of the selected option.
   */
  public static int menu(String title, String[] options) {
    int numOptions = options.length;

    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the title and options if possible
    title = IOManager.getMsg(title);
    // Translate the options if possible
    for (int i = 0; i < options.length; i++)
      options[i] = IOManager.getMsg(options[i]);

    // Print the top
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔%s╗\n", "═".repeat(MENU_WIDTH - 2)));

    // Print the title
    IOManager.print(String.format("║%s║\n", centerString("~ " + title + " ~", MENU_WIDTH - 2)));
    IOManager.print(String.format("╠%s╣\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));

    // Print the hint
    IOManager.print(String.format("║%s║\n",
        leftString("[i] " + String.format(IOManager.getMsg("MENU_HINT"), 1, numOptions), MENU_WIDTH - 2, 4)));
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));

    // Print the options
    for (int i = 0; i < options.length; i++) {
      int index = (configLastAsZero && i == options.length - 1) ? 0 : i + 1;
      String eIn = String.format("- [%d] %s", index, options[i]);
      IOManager.print(String.format("║%s║\n", leftString(eIn, MENU_WIDTH - 2, 7)));
    }
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));

    // Space here (later we will print the prompt here)
    IOManager
        .print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));

    // Print the bottom
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Read the option
    IOManager.moveCursorUp(2);

    // Ask for the option
    try {
      String prompt;
      // Range from 1 to N
      if (configLastAsZero == false)
        prompt = String.format("║    %s (%d-%d) >>> ", IOManager.getMsg("PROMPT_OPTION"), 1, numOptions);
      // Range from 0 to (N-1)
      else
        prompt = String.format("║    %s (%d-%d) >>> ", IOManager.getMsg("PROMPT_OPTION"), 0, numOptions - 1);
      // Ask for the option
      setClean(true);
      int option = IOManager.readInt(prompt);

      // Try again if the option is invalid
      if (option < (configLastAsZero ? 0 : 1) || option > numOptions) {
        alert("WARN", "INVALID_OPTION");
        return menu(title, options);
      }

      // Reset the settings
      resetSettings();
      // Return the option
      return option;

    } catch (NumberFormatException e) {
      alert("WARN", "MUST_BE_NUMBER");
      return menu(title, options);
    }

  }

  /**
   * Ask the user to enter a string.
   * 
   * @param prompt The prompt to show (translated if possible).
   * @return The string entered by the user.
   */
  public static String readString(String prompt) {

    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the prompt if possible
    prompt = IOManager.getMsg(prompt);

    // Print frame
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔═[ %s ]%s╗\n", prompt, "═".repeat(MENU_WIDTH - 7 - prompt.length())));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Move up 2 lines
    IOManager.moveCursorUp(2);
    // Ask the option
    String input = IOManager.readString(String.format("║ > "));
    // Try again if the input is empty
    if (input == null || input.length() == 0) {
      alert("WARN", "MUST_NOT_BE_EMPTY");
      return readString(prompt);
    }
    // Valid the input
    else {
      // Reset the settings
      resetSettings();
      return input;
    }
  }

  /**
   * Ask the user a yes/no question.
   * 
   * @param prompt The prompt to show (translated if possible).
   * @return True if the user answered yes, false if the user answered no.
   */
  public static boolean askYesNo(String prompt) {

    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the prompt if possible
    prompt = IOManager.getMsg(prompt);

    // Print frame
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔%s╗\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("║%s║\n", centerString("~ " + prompt + " ~", MENU_WIDTH - 2)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╠%s╣\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("║%s║\n", centerString(IOManager.getMsg("ASK_YES_NO"), MENU_WIDTH - 2)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("║%s║\n", centerString("[   ]", MENU_WIDTH - 2)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Move up 2 lines
    IOManager.moveCursorUp(3);
    // Ask the option
    String input = IOManager.readString(String.format("║%s", " ".repeat(MENU_WIDTH / 2 - 3) + "[ "));
    // Try again if the input is empty
    if (input == null || input.length() == 0) {
      alert("WARN", "MUST_NOT_BE_EMPTY");
      return askYesNo(prompt);
    }

    // Validate the input
    else {
      // YES
      if (input.equals("1")) {
        // Reset the settings
        resetSettings();
        return true;
      }
      // NO
      else if (input.equals("0")) {
        // Reset the settings
        resetSettings();
        return false;
      }
      // INVALID
      else {
        alert("WARN", "MUST_BE_YES_NO");
        return askYesNo(prompt);
      }
    }
  }

  /**
   * Ask the user to enter an integer.
   * 
   * @param prompt The prompt to show (translated if possible).
   * @return The integer entered by the user.
   */
  public static int readInt(String prompt) {

    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the prompt if possible
    prompt = IOManager.getMsg(prompt);

    // Print frame
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔═[ %s ]%s╗\n", prompt, "═".repeat(MENU_WIDTH - 7 - prompt.length())));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Move up 2 lines
    IOManager.moveCursorUp(2);
    // Ask the option
    String input = IOManager.readString(String.format("║ > "));
    try {
      int val = Integer.parseInt(input);
      // Try again if the value is negative
      if (val < 0) {
        alert("WARN", "MUST_BE_POSITIVE");
        return readInt(prompt);
      }
      // Reset the settings
      resetSettings();
      // Return the value
      return val;
    }
    // If the input is not a number, show an alert and try again
    catch (NumberFormatException e) {
      alert("WARN", "MUST_BE_NUMBER");
      return readInt(prompt);
    }
  }

  /**
   * Ask the user to enter an integer in a range.
   * 
   * @param prompt The prompt to show (translated if possible).
   * @param min    The minimum value.
   * @param max    The maximum value.
   * @return The integer entered by the user.
   */
  public static int readInt(String prompt, int min, int max) {
    int val = readInt(prompt);
    if (val < min || val > max) {
      alert("WARN", "INVALID_OPTION");
      return readInt(prompt, min, max);
    }
    return val;
  }

  /**
   * Show an alert to the user.
   * 
   * @param title The title of the alert (translated if possible).
   * @param msg  The message of the alert (translated if possible).
   */
  public static void alert(String title, String msg) {
    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the title if possible
    title = IOManager.getMsg(title);
    // Translate the message if possible
    msg = IOManager.getMsg(msg);

    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔═[ %s ]%s╗\n", title, "═".repeat(MENU_WIDTH - 7 - title.length())));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("║ %s ║\n", centerString(msg, MENU_WIDTH - 4)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print("\n");
    IOManager.pause();

  }

  /**
   * Print a document.
   * 
   * <p>
   * A document is an array of strings printed in a pretty way. The document will be printed until the user presses enter.
   * <p>
   * 
   * @param title The title of the documentation (translated if possible).
   * @param lines The lines of the documentation (translated if possible).
   */
  public static void doc(String title, String[] lines) {
    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the title if possible
    title = IOManager.getMsg(title);

    IOManager.print(String.format("╔%s╗\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("║%s║\n", leftString(title, MENU_WIDTH - 2, 4)));
    IOManager.print(String.format("╠%s╣\n", "═".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));
    for (String line : lines)
      IOManager.print(String.format("║%s║\n", leftString(line, MENU_WIDTH - 2, 4)));
    IOManager.print(String.format("║%s║\n", " ".repeat(MENU_WIDTH - 2)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Reset the settings
    resetSettings();
  }

  /**
   * Build a form.
   * 
   * <p>
   * This method prints a form with the given the labels for the necessary fields. It will return an array with the values entered by the user in the same order as their labels in the labels array.
   * <p>
   * 
   * @param title  The title of the form (translated if possible).
   * @param labels The labels of the form fields.
   * @return An array with the values entered by the user.
   */
  public static String[] form(String title, String[] labels) {
    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the title if possible
    title = IOManager.getMsg(title);

    // Print the top
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔═[ %s ]%s╗\n", title, "═".repeat(MENU_WIDTH - 7 - title.length())));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    for (String label : labels)
      IOManager.print(String.format("║ %s ║\n", leftString(label + ": ", MENU_WIDTH - 4, 4)));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Move up N lines
    IOManager.moveCursorUp(labels.length + 2);

    // Create an array to store the values
    if (MenuBuilder.formValues == null)
      MenuBuilder.formValues = new String[labels.length];

    // Ask for each field
    for (int i = 0; i < labels.length; i++) {

      // Skip if the value is already set (previously asked, but the form was not completed)
      if (MenuBuilder.formValues[i] != null) {
        IOManager.print(String.format("║     %s: %s\n", labels[i], MenuBuilder.formValues[i]));
        continue;
      }

      // Ask for the value  
      String name = IOManager.readString(String.format("║     %s: ", labels[i]));

      // --- Validate input ---
      // If the value is empty, try again
      if (name == null || name.length() == 0) {
        alert("WARN", "MUST_NOT_BE_EMPTY");
        return form(title, labels);
      }
      // If the value is not unique, try again
      else if (configFormUniqueValues && Arrays.asList(MenuBuilder.formValues).contains(name)) {
        alert("WARN", "NAME_ALREADY_TAKEN");
        return form(title, labels);
      }

      // Input is valid, store the value
      MenuBuilder.formValues[i] = name;

    }

    // Copy the values
    String[] values = MenuBuilder.formValues.clone();

    // Reset the values
    resetSettings();

    // Return the values
    return values;

  }

  // --------------------------------------------- UTILS ---------------------------------------------
  public static void resetSettings() {
    MenuBuilder.clean = true; // DEBUG (Set to false to debug the menus)
    MenuBuilder.formValues = null;
    MenuBuilder.configLastAsZero = false;
    MenuBuilder.configFormUniqueValues = false;
  }

  // --------------------------------------------- STRING UTILS ---------------------------------------------
  public static String centerString(String text, int len) {
    String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
    float mid = (out.length() / 2);
    float start = mid - (len / 2);
    float end = start + len;
    return out.substring((int) start, (int) end);
  }

  public static String leftString(String text, int len) {
    return String.format("%-" + len + "s", text);
  }

  public static String leftString(String text, int len, int padding) {
    return String.format("%-" + len + "s", " ".repeat(padding) + text);
  }

  public static String rightString(String text, int len) {
    return String.format("%" + len + "s", text);
  }

  public static String rightString(String text, int len, int padding) {
    return String.format("%" + len + "s", text + " ".repeat(padding));
  }

  // --------------------------------------------- GETTERS AND SETTERS ---------------------------------------------
  public static void setClean(boolean clean) {
    MenuBuilder.clean = clean;
  }

  public static int getMenuWidth() {
    return MENU_WIDTH;
  }

  public static boolean isClean() {
    return clean;
  }

  public static String[] getFormValues() {
    return formValues;
  }

  public static void setFormValues(String[] formValues) {
    MenuBuilder.formValues = formValues;
  }

  public static boolean isConfigFormUniqueValues() {
    return configFormUniqueValues;
  }

  public static void setConfigFormUniqueValues(boolean configFormUniqueValues) {
    MenuBuilder.configFormUniqueValues = configFormUniqueValues;
  }

  public static boolean isConfigLastAsZero() {
    return configLastAsZero;
  }

  public static void setConfigLastAsZero(boolean configLastAsZero) {
    MenuBuilder.configLastAsZero = configLastAsZero;
  }
}
