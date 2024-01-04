package src.MonopolyGame.IO;

/**
 * This class builds the interactive menus of the game.
 */
public class MenuBuilder {
  private final static int MENU_WIDTH = 80;

  public static int menu(String title, String[] options) {
    int numOptions = options.length;

    // Clear the screen
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
    System.out.print("\033[3A"); // Move up 3 lines
    // Ask for the option
    IOManager
        .print(String.format("║    %s (%d-%d) >>> ", IOManager.getMsg("PROMPT_OPTION"), 1, numOptions));

    // Read the option
    int option = IOManager.readInt(1, numOptions);

    // Clear the screen
    IOManager.cls();

    return option;
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
