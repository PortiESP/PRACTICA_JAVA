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
import src.MonopolyGame.IO.MenuBuilder;
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
  private String gameFilename; // Save game filename
  private ArrayList<Player> players; // Players list
  private Map<String, MonopolyCode> monopolyCodes; // Monopoly codes map (key: code, value: MonopolyCode 'or child class instance')
  private boolean autosave = true; // Autosave flag

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
   * 
   * @return {@code true} if the game has ended, {@code false} otherwise (Saved and exited)
   */
  public boolean play() {

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
      // Operation code
      if (option == 1) {
        String opCode = operationCodeMenu();
        Player player = askCurrentPlayer();

        IOManager.log("Running OpCode: " + opCode + " for player " + player.getName()
            + " -> " + this.monopolyCodes.get(opCode).toString());

        // Do the operation
        this.monopolyCodes.get(opCode).doOperation(player);
      }
      // Game status
      else if (option == 2) {
        gameStatus();
      }
      // Save & Exit
      else if (option == 3) {
        saveGame();
        IOManager.log("Saving and returning to the main menu...");
        return false;
      }

      // Eliminate players that are broke
      eliminatePlayers();

      // Check if there is only one player left (winner)
      if (checkWinner())
        return true;

      // Autosave
      if (autosave)
        saveGame();
    }
    // ==========================================================
  }
  // -----------------------------------------------------------------------------------------------------------

  // ---------------------------------------------- Game creation and loading ----------------------------------------------
  /**
   * Creates a new game with with the <strong>default values</strong>.
   * 
   * @param filename The name of the game
   */
  public void newGame(String filename) {
    this.gameFilename = filename;
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
      this.monopolyCodes = auxGame.getMonopolyCodes();
      this.players = auxGame.getPlayers();
      this.autosave = auxGame.isAutosave();

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
  
   * 
   * @return The selected option
   */
  public int operationsMenu() {
    int opt = MenuBuilder.menu("OPERATIONS_MENU", new String[] { "OPERATIONS_MENU_OPTION_COD_OP",
        "OPERATIONS_MENU_OPTION_GAME_STATUS", "OPERATIONS_MENU_OPTION_SAVE_EXIT" });

    return opt;
  }

  /**
   * Prints the game status (filename, players, etc.).
   */
  public void gameStatus() {

    IOManager.cls();
    for (Player player : players) {
      MenuBuilder.setClean(false);
      MenuBuilder.doc(String.format(IOManager.getMsg("GAME_STATUS_PLAYER") + ": ", player.getName()), player.summary());
    }

    IOManager.print("\n");
    IOManager.pause();

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
          monopolyCodes.put(cardCode, new PaymentCard(fields[2] + (fields.length > 3 ? " -" + fields[3] + "€" : ""))); // Handle the case "IMPUESTO LUJO" where the amount is a 4th field
        else if ("STREET".equals(cardType))
          monopolyCodes.put(cardCode, new StreetCard(fields[2], fields[3], fields[4], fields[5], fields[6], fields[7],
              fields[8], fields[9], fields[10], fields[11]));
        else if ("SERVICE".equals(cardType))
          monopolyCodes.put(cardCode, new ServiceCard(fields[2], fields[3], fields[4], fields[5]));
        else if ("TRANSPORT".equals(cardType))
          monopolyCodes.put(cardCode,
              new StationCard(fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]));
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
    // Ask the number of players
    int numPlayers = MenuBuilder.readInt(IOManager.getMsg("PROMPT_NUM_PLAYERS"));
    IOManager.print("\n");

    // Ask the name of each player
    String[] labels = new String[numPlayers];
    for (int i = 0; i < numPlayers; i++)
      labels[i] = String.format(IOManager.getMsg("PROMPT_PLAYER_NAME"), (i + 1));
    // Set the form configuration
    MenuBuilder.setConfigFormUniqueValues(true);
    String[] playerNames = MenuBuilder.form("PROMPT_PLAYERS_NAMES_TITLE", labels);

    // Create players
    for (String name : playerNames)
      players.add(new Player(name));

    // DEBUG: Print all the players
    IOManager.log("Created all players");
    for (Player player : players)
      IOManager.log(player);
  }

  // ---------------------------------------------- Game operations ----------------------------------------------
  /**
   * Prints the operations menu and returns the operation code types by the user.
   * 
   * @return The typed operation code (string)
   */
  public String operationCodeMenu() {

    // Print the operations menu
    String opCode = MenuBuilder.readString("PROMPT_OP_CODE");

    if (!monopolyCodes.containsKey(opCode)) {
      MenuBuilder.alert("WARN", "INVALID_CODE");
      return operationCodeMenu();
    }

    IOManager.log("Running OpCode: " + opCode);
    return opCode;
  }

  /**
   * Asks the user to select a player from the list of players.
   * 
   * @return The selected player
   */
  public Player askCurrentPlayer() {
    IOManager.print("\n");

    // Create a list with the names of the players
    String[] names = new String[players.size()];
    for (int i = 0; i < players.size(); i++)
      names[i] = players.get(i).getName();

    // Ask the user to select a player
    int opt = MenuBuilder.menu("PLAYER_TURN", names);

    return this.players.get(opt - 1);
  }

  /**
   * Eliminates a player from the game.
   * 
   * This method will iterate over the players list, if a player is broke it will save the player in a variable, exit the loop, and remove the player from the list. Then it will repeat the process until there are no broke players.
   * 
   * We need to do it this way to avoid the {@code ConcurrentModificationException} when removing an element from a list while iterating over it.
   * 
   * @param player The player to eliminate
   */
  public void eliminatePlayers() {
    // Store if there was a player eliminated in the previous loop
    boolean hadLoser = false;
    // Loop until any broke player had been eliminated in the previous loop
    do {
      // Reset the flag
      Player loser = null; // No loser yet
      hadLoser = false; // No loser in this loop yet

      // Iterate over the players list in order to find a broke player
      for (Player p : players) {
        // If the player is broke, save it and exit the loop
        if (p.isBroke()) {
          loser = p;
          break;
        }
      }

      // If there was a loser, remove it from the list
      if (loser != null) {
        this.players.remove(loser);
        hadLoser = true;
      }

    } while (hadLoser); // Repeat until there are no broke players detected
  }

  /**
   * Checks if there is only one player left in the game, if so, it will print a message and exit the game.
   * 
   * @return True if there is only one player left, false otherwise.
   */
  public boolean checkWinner() {
    // If there is only one player left, print a message and exit the game
    if (players.size() == 1) {
      MenuBuilder.alert("PLAYER_INFO_TITLE",
          String.format(IOManager.getMsg("PLAYER_WINNER"), players.get(0).getName()));
      return true;
    }

    return false;
  }

  // ---------------------------------------------- Getters and setters ----------------------------------------------

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

  public boolean isAutosave() {
    return autosave;
  }

  public void setAutosave(boolean autosave) {
    this.autosave = autosave;
  }

}
