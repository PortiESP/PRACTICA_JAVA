package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.MenuBuilder;

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

    // MenuBuilder.askYesNo("Test yes no");

    // Load the default game
    // this.game.loadGame("winner"); // Bankrupt test game
    // this.game.loadGame("bankrupt"); // Bankrupt test game
    this.game.loadGame("default_game"); // Default test game
    // Reset the cards
    // this.game.resetCards();
    // Reset the players (template players)
    // ArrayList<Player> players = new ArrayList<>();
    // players.add(new Player("Player 1"));
    // players.add(new Player("Player 2"));
    // players.add(new Player("Player 3"));
    // players.add(new Player("Player 4"));
    // this.game.setPlayers(players);

    // Disable autosave
    this.game.setAutosave(false);

    IOManager.log("[!!!] Playing a test game");

    this.game.play(); // Default game
  }

  public String languageSelectionMenu() {
    // Language selection menu
    String title = IOManager.getMsg("LANGUAGE_SELECTION_TITLE");
    String[] languages = getSavedGamesFilesList(Const.LANGUAGES_PATH).toArray(String[]::new);
    int opt = MenuBuilder.menu(title, languages);
    // Return the selected language
    return languages[opt - 1];
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
    String title = IOManager.getMsg("MAIN_MENU_TITLE");
    String[] options = {
        IOManager.getMsg("NEW_GAME"),
        IOManager.getMsg("LOAD_GAME"),
        IOManager.getMsg("CHANGE_LANGUAGE"),
        IOManager.getMsg("EXIT")
    };

    return MenuBuilder.menu(title, options);
  }

  /**
   * This method asks the user for a name for the new game, if the name already exists, it will ask the user to select a different name.
   * 
   * @return The name typed by the user.
   */
  public String askNewFileName() {
    String filename = MenuBuilder.readString(IOManager.getMsg("PROMPT_GAME_NAME")); // Ask the user for a file name
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
