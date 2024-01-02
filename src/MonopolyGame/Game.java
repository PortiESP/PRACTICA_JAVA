package src.MonopolyGame;

import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.MonopolyCodes.MonopolyCode;
import src.MonopolyGame.MonopolyCodes.PaymentCard;
import src.MonopolyGame.MonopolyCodes.RepairsCard;
import src.MonopolyGame.MonopolyCodes.ServiceCard;
import src.MonopolyGame.MonopolyCodes.StationCard;
import src.MonopolyGame.MonopolyCodes.StreetCard;

/**
 * <p>
 * This class is responsible for managing the game. The game is started by instantiating a {@code Game} object and calling the {@code play()} method.
 * <p>
 * 
 * <p>
 * ⚠️ The {@code Game} class also contains the methods {@code newGame()} and {@code loadGame()} that are responsible for creating a new game or loading a saved game. This methods should be called before the {@code play()} method.
 * </p>
 * 
 * <p>
 * It has the main loop of the game, the operations menu, the game status, and the save game methods.
 * <p>
 */
public class Game implements Serializable {
  // Attributes
  private IOManager io; // IO Manager (print, read inputs, etc.)
  private String gameFilename; // Save game filename
  private Map<String, MonopolyCode> monopolyCodes; // Monopoly codes map (key: code, value: MonopolyCode 'or child class instance')
  private ArrayList<Player> players; // Players list

  // Methods
  // ---------------------------------------------- Game main loop ----------------------------------------------
  /**
   * <p>
   * This method is responsible for the main loop of the game. It will first check if the game has been created or loaded, otherwise it will create a new game with default name.
   * </p>
   * 
   * <p>
   * <h4>Main loop</h4>
   * The main loop will print the operations menu and execute the selected option. The available options are:
   * 
   * <ul>
   *    <li>{@code 1} <strong>Card code</strong>: Executes the operation defined for the given code</li>
   *    <li>{@code 2} <strong>Game status</strong>: Print the game status (filename, players, etc.)</li>
   *    <li>{@code 3} <strong>Save game</strong>: Save the game to a file</li>
   *    <li>{@code 4} <strong>Exit</strong>: Exit the game</li>
   * </ul>
   * </p>
   */
  public void play() {

    // Check if the game has been created or loaded, otherwise create a new game with default name
    if (this.players == null) {
      newGame("default_game");
    }

    // ======================= Main loop ========================
    while (true) {
      // Print the operations menu
      int option = operationsMenu();
      IOManager.log("Option: " + option); // DEBUG

      // Execute the selected option
      if (option == 1) { // Cod Op
        // TODO
      } else if (option == 2) { // Game status
        gameStatus();
      } else if (option == 3) { // Save game
        saveGame();
      } else if (option == 4) { // Exit
        IOManager.log("Exiting...");
        break;
      }
    }
    // ==========================================================
  }
  // -----------------------------------------------------------------------------------------------------------

  /**
   * Creates a new game with with the <strong>default values</strong>.
   * 
   * @param filename The name of the game
   */
  public void newGame(String filename) {
    this.gameFilename = filename;
    this.io = new IOManager();
    this.monopolyCodes = new HashMap<>();
    this.players = new ArrayList<>();

    // Reset the cards to their default values
    resetCards();
    // Create players
    createPlayers();
  }

  /**
   * Loads a game from a file, the file must be located in the {@code Const.SAVES_PATH} directory.
   * 
   * @param filename The name of the game
   */
  public void loadGame(String filename) {
    this.gameFilename = filename;

    try {
      // Prepare the XML decoder
      FileInputStream file = new FileInputStream(Const.SAVES_PATH + this.gameFilename + ".xml");
      XMLDecoder xmlFile = new XMLDecoder(file);

      // Read the game from the file
      Game auxGame = (Game) xmlFile.readObject();
      // Close the XML decoder
      xmlFile.close();

      // Overwrite the current game with the loaded game data
      this.io = auxGame.getIo();
      this.monopolyCodes = auxGame.getMonopolyCodes();
      this.players = auxGame.getPlayers();

    } catch (Exception e) {
      IOManager.log("Unable to load the game");
      e.printStackTrace();
    }
  }

  /**
   * Saves the game to a file with the name given to the game, the file will be located in the {@code Const.SAVES_PATH} directory.
   */
  public void saveGame() {
    IOManager.log("Save game " + gameFilename);

    try {
      // Prepare the XML encoder
      FileOutputStream file = new FileOutputStream(Const.SAVES_PATH + this.gameFilename + ".xml");
      BufferedOutputStream buffer = new BufferedOutputStream(file);
      XMLEncoder xmlFile = new XMLEncoder(buffer);

      // Write the game to the file
      xmlFile.writeObject(this);
      // Close the XML encoder
      xmlFile.close();

    } catch (Exception e) {
      IOManager.log("Unable to save the game");
      e.printStackTrace();
    }
  }

  /**
   * Prints the operations menu and returns the selected option.
   * 
   * @return The selected option
   */
  public int operationsMenu() {
    io.print("\n");
    io.printlnMsg("OPERATIONS_MENU");
    io.print("\n");
    io.print(String.format("\t[1] %s\n", io.getMsg("OPERATIONS_MENU_OPTION_COD_OP")));
    io.print(String.format("\t[2] %s\n", io.getMsg("OPERATIONS_MENU_OPTION_GAME_STATUS")));
    io.print(String.format("\t[3] %s\n", io.getMsg("OPERATIONS_MENU_OPTION_SAVE_GAME")));
    io.print(String.format("\t[4] %s\n", io.getMsg("OPERATIONS_MENU_OPTION_SAVE_EXIT")));
    io.print("\n");

    return io.readInt("PROMPT_OPTION", 1, 4);
  }

  /**
   * Prints the game status (filename, players, etc.).
   */
  public void gameStatus() {
    io.print("\n");
    // Title
    io.print(String.format("[i] %s\n", io.getMsg("GAME_STATUS")));
    io.print("\n");
    // Filename
    io.print(String.format("\t- %s: %s\n", io.getMsg("GAME_STATUS_FILENAME"), gameFilename));
    io.print("\n");
    // Players
    io.print(String.format("\t- %s:\n", io.getMsg("GAME_STATUS_PLAYERS")));
    for (int i = 0; i < players.size(); i++)
      io.print(String.format("\t\t- %s\n", players.get(i).summary()));

    io.print("\n");
    io.print("\n");

  }

  /**
   * Reads the {@code Const.CONFIG_CODES_FILE_PATH} file and resets the cards (<em>available operations based on a code</em>) to their default values.
   */
  public void resetCards() {
    try {
      // Prepare the file reader
      Reader file = new FileReader(Const.CONFIG_CODES_FILE_PATH);
      BufferedReader reader = new BufferedReader(file);

      // Read the file configuration file
      String line = reader.readLine();

      // Read each line
      while (line != null) {
        // Split the line by its fields
        String[] fields = line.split(Const.CODES_DELIMITER);
        // Extract the card code and card type (station, street, etc.)
        String cardCode = fields[0];
        String cardType = fields[1];

        // Create a new instance of the MonopolyCode based on its type
        if ("PAYMENT_CHARGE_CARD".equals(cardType))
          monopolyCodes.put(cardCode, new PaymentCard(fields[2]));
        else if ("STREET".equals(cardType))
          monopolyCodes.put(cardCode, new StreetCard(fields[2], fields[3], fields[4], fields[5], fields[6], fields[7],
              fields[8], fields[9], fields[10], fields[11]));
        else if ("SERVICE".equals(cardType))
          monopolyCodes.put(cardCode, new ServiceCard(fields[2], fields[3], fields[4]));
        else if ("TRANSPORT".equals(cardType))
          monopolyCodes.put(cardCode, new StationCard(fields[2], fields[3], fields[4], fields[5], fields[6]));
        else if ("REPAIRS_CARD".equals(cardType))
          monopolyCodes.put(cardCode, new RepairsCard(fields[2]));
        else {
          IOManager.log("Error adding card, the code " + cardCode + " has an invalid type: " + cardType);
          reader.close();
          throw new RuntimeException("Error adding card, the code " + cardCode + " has an invalid type: " + cardType);
        }

        // Read next line
        line = reader.readLine();
      }

      // DEBUG: Print all the cards
      IOManager.log("Loaded all cards from " + Const.CONFIG_CODES_FILE_PATH);
      for (MonopolyCode code : monopolyCodes.values())
        IOManager.log(code);

      reader.close();

    } catch (Exception e) {
      IOManager.log("Unable to open [" + Const.CONFIG_CODES_FILE_PATH + "], or invalid card code");
      e.printStackTrace();
    }
  }

  /**
   * Creates the players of the game. It will ask the number of players and the name of each player, the names will be used to uniquely identify the players.
   */
  public void createPlayers() {
    io.print("\n");

    // Ask the number of players
    int numPlayers = io.readInt("PROMPT_NUM_PLAYERS");
    io.print("\n");

    // Ask the name of each player
    for (int i = 0; i < numPlayers; i++) {
      // Read the name
      String name = io.readString("PROMPT_PLAYER_NAME", i + 1);
      // Check if the name is already taken
      while (players.contains(new Player(name))) {
        io.printlnMsg("NAME_ALREADY_TAKEN");
        name = io.readString("PROMPT_PLAYER_NAME", i + 1);
      }

      // Add the player to the list
      players.add(new Player(name));
    }

    // DEBUG: Print all the players
    IOManager.log("Created all players");
    for (Player player : players)
      IOManager.log(player);
  }

  // ---------------------------------------------- Getters and setters ----------------------------------------------
  public IOManager getIo() {
    return io;
  }

  public void setIo(IOManager io) {
    this.io = io;
  }

  public String getGameFilename() {
    return gameFilename;
  }

  public void setGameFilename(String gameFilename) {
    this.gameFilename = gameFilename;
  }

  public Map<String, MonopolyCode> getMonopolyCodes() {
    return monopolyCodes;
  }

  public void setMonopolyCodes(Map<String, MonopolyCode> monopolyCodes) {
    this.monopolyCodes = monopolyCodes;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

}
