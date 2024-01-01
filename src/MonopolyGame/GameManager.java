package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;
import java.io.File;
import java.util.ArrayList;

public class GameManager {
  // Attributes
  private IOManager io; // IO Manager (print, read inputs, etc.)

  // Constructor
  public GameManager() {
    this.io = new IOManager();
  }

  // ---------------------------------------------- Start the game ----------------------------------------------
  public void start() {
    // Print the MONOPOLY logo
    io.printRaw(Const.MONOPOLY_LOGO);
    // Print the language selection menu
    String langFileName = languageSelectionMenu();
    // Load the language from the file
    io.setLanguage(langFileName);

    // Print the welcome message
    io.printRaw("\n");
    io.printRaw("\n");
    io.print("WELCOME");
  }
  // -----------------------------------------------------------------------------------------------------------

  // Language selection menu
  public String languageSelectionMenu() {
    ArrayList<String> languages = getFilesList("./config/Languages");

    // Language selection menu
    io.printRaw("[i] Language selection:\n");
    io.printRaw("\n");
    for (int i = 0; i < languages.size(); i++)
      io.printRaw(String.format("\t- [%d] %s\n", i + 1, languages.get(i)));
    io.printRaw("\n");
    int option = io.readInt("Select an option");

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
}
