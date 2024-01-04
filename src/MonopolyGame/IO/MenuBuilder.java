package src.MonopolyGame.IO;

/**
 * This class builds the interactive menus of the game.
 */
public class MenuBuilder {
  private final static int MENU_WIDTH = 80;
  private static boolean clean = true;

  public static int menu(String title, String[] options) {
    int numOptions = options.length;

    // Clear the screen
    if (clean)
      IOManager.cls();

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
      IOManager.print(String.format("║%s║\n",
          leftString(String.format("       - [%d] %s", i + 1, options[i]), MENU_WIDTH - 2)));
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
    IOManager
        .print(String.format("║    %s (%d-%d) >>> ", IOManager.getMsg("PROMPT_OPTION"), 1, numOptions));

    // Read the option
    int option = IOManager.readInt(1, numOptions);

    return option;
  }

  public static String readString(String prompt) {

    // Clear the screen
    if (clean)
      IOManager.cls();

    // Print frame
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.print(String.format("╔═[ %s ]%s╗\n", prompt, "═".repeat(MENU_WIDTH - 7 - prompt.length())));
    IOManager.print(String.format("║ %s ║\n", " ".repeat(MENU_WIDTH - 4)));
    IOManager.print(String.format("╚%s╝\n", "═".repeat(MENU_WIDTH - 2)));

    // Move up 2 lines
    IOManager.moveCursorUp(2);
    // Ask the option
    return IOManager.readString(String.format("║ > "));
  }

  public static int readInt(String prompt) {

    // Clear the screen
    if (clean)
      IOManager.cls();

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
      // Return the value
      return val;
    }
    // If the input is not a number, show an alert and try again
    catch (NumberFormatException e) {
      alert("WARN", "MUST_BE_NUMBER");
      return readInt(prompt);
    }
  }

  public static void alert(String title, String msg) {
    // Clear the screen
    if (clean)
      IOManager.cls();

    // Translate the title if possible
    try {
      title = IOManager.getMsg(title);
    } catch (Exception e) {
      // Do nothing
    }

    // Translate the message if possible
    try {
      msg = IOManager.getMsg(msg);
    } catch (Exception e) {
      // Do nothing
    }

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

  public static String[] form(String title, String[] labels) {
    // Clear the screen
    if (clean)
      IOManager.cls();

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
    String[] values = new String[labels.length];

    // Ask for each field
    for (int i = 0; i < labels.length; i++)
      values[i] = IOManager.readString(String.format("║     %s: ", labels[i]));

    return values;

  }

  public static void setClean(boolean clean) {
    MenuBuilder.clean = clean;
  }

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
}
