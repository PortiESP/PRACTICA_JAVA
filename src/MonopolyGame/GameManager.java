package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;
import java.io.File;
import java.util.ArrayList;

public class GameManager {
  // Attributes
  private IOManager io = new IOManager(); // IO Manager (print, read inputs, etc.)
  private Game game = new Game(); // Game instance

  // ---------------------------------------------- Start the game ----------------------------------------------
  public void start() {
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

    // Game configuration ----------------------------
    // Print the main menu (load game, new game, exit)
    int option = mainMenu();
    // Execute the selected option
    if (option == 1) // New game
      this.game.newGame(askNewFileName());

    else if (option == 2) { // Load game
      String filename = askExistingFileName();
      // If there are no saved games, start a new game
      if (filename == null) {
        io.printlnMsg("NO_SAVED_GAMES");
        this.game.newGame(askNewFileName());
      } else {
        this.game.loadGame(filename);
      }

    } else if (option == 3)
      exit();

    // Start the game ----------------------------
    this.game.play();
  }
  // -----------------------------------------------------------------------------------------------------------

  // Language selection menu
  public String languageSelectionMenu() {
    ArrayList<String> languages = getFilesList(Const.LANGUAGES_PATH);

    // Language selection menu
    io.print("[i] Language selection:\n");
    io.print("\n");
    for (int i = 0; i < languages.size(); i++)
      io.print(String.format("\t- [%d] %s\n", i + 1, languages.get(i)));
    io.print("\n");
    int option = io.readInt("PROMPT_OPTION", 1, languages.size());

    return languages.get(option - 1);
  }

  public ArrayList<String> getFilesList(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();

    ArrayList<String> fileNames = new ArrayList<>();

    for (File file : listOfFiles) {
      if (file.isFile())
        fileNames.add(file.getName().split(".txt")[0]);
    }

    return fileNames;
  }

  // Main menu
  public int mainMenu() {
    // Main menu
    io.print(String.format("[i] %s:\n", io.getMsg("MAIN_MENU")));
    io.print("\n");
    io.print("\t- [1] " + io.getMsg("NEW_GAME") + "\n");
    io.print("\t- [2] " + io.getMsg("LOAD_GAME") + "\n");
    io.print("\t- [3] " + io.getMsg("EXIT") + "\n");
    io.print("\n");
    return io.readInt("PROMPT_OPTION", 1, 3);
  }

  // Ask file name and check if it exists
  public String askNewFileName() {
    String filename = io.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
    while (fileExists(filename)) {
      io.printlnMsg("FILE_EXISTS");
      filename = io.readString("PROMPT_GAME_NAME"); // Ask the user for a file name
    }
    return filename;
  }

  // Prints a list of existing games and asks the user to select one
  public String askExistingFileName() {
    ArrayList<String> files = getFilesList(Const.SAVES_PATH);

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
    return files.get(option - 1);
  }

  // Check if a file exists
  public boolean fileExists(String filename) {
    File file = new File(Const.SAVES_PATH + filename + ".xml");
    return file.exists();
  }

  // Exit
  public void exit() {
    io.print("\n");
    io.printlnMsg("GOODBYE");
    io.print("\n");
    System.exit(0);
  }
}
