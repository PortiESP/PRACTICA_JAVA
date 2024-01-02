package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;
import java.io.File;
import java.util.ArrayList;

/**
 * This class is responsible for printing the language selection menu, the main menu.
 * The game is started by instantiating a {@code GameManager} object and calling the {@code start()} method.
 */
public class GameManager {
  // Attributes
  private IOManager io = new IOManager(); // IO Manager (print, read inputs, etc.)
  private Game game; // Game instance

  // ========================================= Start the game =========================================
  /**
   * This method is responsible creating/loading a game and starting it.
   * 
   * <p>
   * It will create a new game instance in order to restart the game when the user selects the back to menu option.
   * Then it will print the language selection menu and set the chosen language for the game strings.
   * After that, it will print a welcome message and the main menu. 
   * </p>
   * 
   * <p>
   * The main menu has 4 options:
   * 
   * <ul>
   *    <li>New game: Create a new game and ask the user for a name for the game.</li>
   *    <li>Load game: Load a saved game from a given list.</li>
   *    <li>Change language: Returns to the previous screen with the language selection menu.</li>
   *    <li>Exit: Exits the game.</li>
   * </ul>
   * </p>
   * 
   * Finally, it will start the game by calling the {@code play()}.
   */
  public void start() {
    // Instantiate the game or reset it
    this.game = new Game();
    // Load default language
    io.setLanguage(Const.DEFAULT_LANG);

    // Print the MONOPOLY logo
    io.print(Const.MONOPOLY_LOGO);
    // Print the language selection menu
    String langFileName = languageSelectionMenu();
    // Load the language from the file
    io.setLanguage(langFileName);

    // Print the welcome message
    io.print("\n");
    io.print("\n");
    io.printlnMsg("WELCOME");
    io.print("\n");

    // Game configuration ========================
    // Print the main menu (load game, new game, exit)
    int option = mainMenu();

    // Execute the selected option
    if (option == 1) // ------------------------ New game
      this.game.newGame(askNewFileName());

    else if (option == 2) { // ----------------- Load game
      String filename = askExistingFileName();
      // If there are no saved games, start a new game
      if (filename == null) {
        io.printlnMsg("NO_SAVED_GAMES");
        this.game.newGame(askNewFileName());
      } else {
        this.game.loadGame(filename);
      }

    } else if (option == 3) // ----------------- Change language
      return;
    else if (option == 4) // ------------------- Exit
      exit();

    // Start the game ============================
    this.game.play();

  }
  // ========================================================================================================

  /**
   * This method prints the language selection menu, the options are based on the files available at the {@code /config/languages} directory.
   * 
   * @return The selected language. E.G.: "English" for English, "Spanish" for Spanish, etc.
   */
  public String languageSelectionMenu() {
    ArrayList<String> languages = getSavedGamesFilesList(Const.LANGUAGES_PATH);

    // Language selection menu
    io.print("[i] Language selection:\n");
    io.print("\n");
    for (int i = 0; i < languages.size(); i++)
      io.print(String.format("\t- [%d] %s\n", i + 1, languages.get(i)));
    io.print("\n");
    int option = io.readInt("PROMPT_OPTION", 1, languages.size());

    return languages.get(option - 1);
  }

  /**
   * This method returns a list of files in a given directory.
   * 
   * @param path The path to the directory.
   * @return An array list with the names of the files in the directory (without extension).
   */
  public ArrayList<String> getSavedGamesFilesList(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();

    ArrayList<String> fileNames = new ArrayList<>();

    for (File file : listOfFiles) {
      if (file.isFile())
        fileNames.add(file.getName().split(".txt")[0]);
    }

    return fileNames;
  }

  /**
   * This method prints the main menu and asks the user to select an option.
   * 
   * <ul>
   *    <li>New game: Create a new game and ask the user for a name for the game.</li>
   *    <li>Load game: Load a saved game from a given list.</li>
   *    <li>Change language: Returns to the previous screen with the language selection menu.</li>
   *    <li>Exit: Exits the game.</li>
   * </ul>
   * 
   * @return The selected option.
   */
  public int mainMenu() {
    // Main menu
    io.print(String.format("[i] %s:\n", io.getMsg("MAIN_MENU")));
    io.print("\n");
    io.print("\t- [1] " + io.getMsg("NEW_GAME") + "\n");
    io.print("\t- [2] " + io.getMsg("LOAD_GAME") + "\n");
    io.print("\t- [3] " + io.getMsg("CHANGE_LANGUAGE") + "\n");
    io.print("\t- [4] " + io.getMsg("EXIT") + "\n");
    io.print("\n");
    return io.readInt("PROMPT_OPTION", 1, 4);
  }

  /**
   * This method asks the user for a name for the new game, if the name already exists, it will ask the user to select a different name.
   * 
   * @return The name typed by the user.
   */
  public String askNewFileName() {
    String filename = io.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
    while (savedGameExists(filename)) {
      io.printlnMsg("FILE_EXISTS");
      filename = io.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
    }
    return filename;
  }

  /**
   * This method asks the user to select a saved game from a list. If there are no saved games, it will return null.
   * 
   * @return The name of the selected file (without extension).
   */
  public String askExistingFileName() {
    ArrayList<String> files = getSavedGamesFilesList(Const.SAVES_PATH);

    // If there are no saved games, return null
    if (files.size() == 0) {
      return null;
    }

    // Print the list of existing games
    io.print("\n");
    io.print(String.format("[i] %s:\n", io.getMsg("SAVED_GAMES_LIST")));
    io.print("\n");
    for (int i = 0; i < files.size(); i++)
      io.print(String.format("\t- [%d] %s\n", i + 1, files.get(i).split(".xml")[0]));

    io.print("\n");

    // Ask the user to select a game
    int option = io.readInt("PROMPT_OPTION", 1, files.size());
    return files.get(option - 1).split(".xml")[0];
  }

  /**
   * This method checks if a saved game exists in the {@code /config/oldGames} directory.
   * 
   * @param filename The name of the file to check.
   * @return True if the file exists, false otherwise.
   */
  public boolean savedGameExists(String filename) {
    File file = new File(Const.SAVES_PATH + filename + ".xml");
    return file.exists();
  }

  /**
   * This method prints a goodbye message and exits the program.
   */
  public void exit() {
    io.print("\n");
    io.printlnMsg("GOODBYE");
    io.print("\n");
    System.exit(0);
  }
}
