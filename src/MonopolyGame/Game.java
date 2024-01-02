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

public class Game implements Serializable {
  // Attributes
  private IOManager io; // IO Manager (print, read inputs, etc.)
  private String gameFilename; // Save game filename
  private Map<String, MonopolyCode> monopolyCodes; // Monopoly codes map (key: code, value: MonopolyCode 'or child class instance')
  private ArrayList<Player> players; // Players list

  // Methods
  // ---------------------------------------------- Game main loop ----------------------------------------------
  public void play() {
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
  }
  // -----------------------------------------------------------------------------------------------------------

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

  public void loadGame(String filename) {
    this.gameFilename = filename;

    // Read XML Game file
    try {
      // Deserialize the game
      FileInputStream file = new FileInputStream(Const.SAVES_PATH + this.gameFilename + ".xml");
      XMLDecoder xmlFile = new XMLDecoder(file);

      Game auxGame = (Game) xmlFile.readObject();

      // Set the game attributes
      this.io = auxGame.getIo();
      this.monopolyCodes = auxGame.getMonopolyCodes();
      this.players = auxGame.getPlayers();

      xmlFile.close();

    } catch (Exception e) {
      IOManager.log("Unable to load the game");
      e.printStackTrace();
    }
  }

  public void saveGame() {
    IOManager.log("Save game " + gameFilename);

    try {
      // Serialize the game
      FileOutputStream file = new FileOutputStream(Const.SAVES_PATH + this.gameFilename + ".xml");
      BufferedOutputStream buffer = new BufferedOutputStream(file);
      XMLEncoder xmlFile = new XMLEncoder(buffer);

      xmlFile.writeObject(this);

      xmlFile.close();

    } catch (Exception e) {
      IOManager.log("Unable to save the game");
      e.printStackTrace();
    }
  }

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

  public void gameStatus() {
    io.print("\n");
    io.print(String.format("[i] %s\n", io.getMsg("GAME_STATUS")));
    io.print("\n");
    io.print(String.format("\t- %s: %s\n", io.getMsg("GAME_STATUS_FILENAME"), gameFilename));
    io.print("\n");
    io.print(String.format("\t- %s:\n", io.getMsg("GAME_STATUS_PLAYERS")));
    for (int i = 0; i < players.size(); i++)
      io.print(String.format("\t\t- %s\n", players.get(i).summary()));
    io.print("\n");
    io.print("\n");

  }

  public void resetCards() {
    // Load monopoly codes from the config file
    try {
      Reader file = new FileReader(Const.CONFIG_CODES_FILE_PATH);
      BufferedReader reader = new BufferedReader(file);

      // Read the file configuration file
      String line = reader.readLine();
      while (line != null) {
        // Split the line in fields
        String[] fields = line.split(";");
        // Extract the code and type
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

  public void createPlayers() {
    // Ask the number of players
    io.print("\n");
    int numPlayers = io.readInt("PROMPT_NUM_PLAYERS");
    io.print("\n");

    // Ask the name of each player
    for (int i = 0; i < numPlayers; i++) {
      String name = io.readString("PROMPT_PLAYER_NAME", i + 1);
      // Check if the name is already taken
      while (players.contains(new Player(name))) {
        io.printlnMsg("NAME_ALREADY_TAKEN");
        name = io.readString("PROMPT_PLAYER_NAME", i + 1);
      }
      players.add(new Player(name));
    }

    // DEBUG: Print all the players
    IOManager.log("Created all players");
    for (Player player : players)
      IOManager.log(player);
  }

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
