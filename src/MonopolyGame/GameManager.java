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
    IOManager.setLanguage(Const.DEFAULT_LANG);

    // Print the MONOPOLY logo
    IOManager.print(Const.MONOPOLY_LOGO);
    // Print the language selection menu
    String langFileName = languageSelectionMenu();
    // Load the language from the file
    IOManager.setLanguage(langFileName);

    // Print the welcome message
    IOManager.print("\n");
    IOManager.print("\n");
    IOManager.printlnMsg("WELCOME");
    IOManager.print("\n");

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
        IOManager.printlnMsg("NO_SAVED_GAMES");
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
  // THIS IS FOR DEBUGGING PURPOSES ONLY
  public void startDebug() {
    this.game = new Game();

    IOManager.setLanguage("English");
    this.game.loadGame("default_game");
    this.game.setAutosave(false);

    IOManager.log("[!!!] Playing the default game");

    this.game.play(); // Default game
  }

  /**
   * This method prints the language selection menu, the options are based on the files available at the {@code /config/languages} directory.
   * 
   * @return The selected language. E.G.: "English" for English, "Spanish" for Spanish, etc.
   */
  public String languageSelectionMenu() {
    ArrayList<String> languages = getSavedGamesFilesList(Const.LANGUAGES_PATH);

    // Language selection menu
    IOManager.print("[i] Language selection:\n");
    IOManager.print("\n");
    for (int i = 0; i < languages.size(); i++)
      IOManager.print(String.format("\t- [%d] %s\n", i + 1, languages.get(i)));
    IOManager.print("\n");
    int option = IOManager.readInt("PROMPT_OPTION", 1, languages.size());

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
    IOManager.print(String.format("[i] %s:\n", IOManager.getMsg("MAIN_MENU")));
    IOManager.print("\n");
    IOManager.print("\t- [1] " + IOManager.getMsg("NEW_GAME") + "\n");
    IOManager.print("\t- [2] " + IOManager.getMsg("LOAD_GAME") + "\n");
    IOManager.print("\t- [3] " + IOManager.getMsg("CHANGE_LANGUAGE") + "\n");
    IOManager.print("\t- [4] " + IOManager.getMsg("EXIT") + "\n");
    IOManager.print("\n");
    return IOManager.readInt("PROMPT_OPTION", 1, 4);
  }

  /**
   * This method asks the user for a name for the new game, if the name already exists, it will ask the user to select a different name.
   * 
   * @return The name typed by the user.
   */
  public String askNewFileName() {
    String filename = IOManager.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
    while (savedGameExists(filename)) {
      IOManager.printlnMsg("FILE_EXISTS");
      filename = IOManager.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
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
    IOManager.print("\n");
    IOManager.print(String.format("[i] %s:\n", IOManager.getMsg("SAVED_GAMES_LIST")));
    IOManager.print("\n");
    for (int i = 0; i < files.size(); i++)
      IOManager.print(String.format("\t- [%d] %s\n", i + 1, files.get(i).split(".xml")[0]));

    IOManager.print("\n");

    // Ask the user to select a game
    int option = IOManager.readInt("PROMPT_OPTION", 1, files.size());
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
    IOManager.print("\n");
    IOManager.printlnMsg("GOODBYE");
    IOManager.print("\n");
    System.exit(0);
  }
}
