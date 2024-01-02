package src.MonopolyGame;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.MonopolyCodes.MonopolyCode;
import src.MonopolyGame.MonopolyCodes.PaymentCard;
import src.MonopolyGame.MonopolyCodes.RepairsCard;
import src.MonopolyGame.MonopolyCodes.ServiceCard;
import src.MonopolyGame.MonopolyCodes.StationCard;
import src.MonopolyGame.MonopolyCodes.StreetCard;

public class Game {
  // Attributes
  private IOManager io = new IOManager(); // IO Manager (print, read inputs, etc.)
  private String gameFilename; // Save game filename
  private Map<String, MonopolyCode> monopolyCodes = new HashMap<>(); // Monopoly codes map (key: code, value: MonopolyCode 'or child class instance')

  // Methods
  // ---------------------------------------------- Game main loop ----------------------------------------------
  public void play() {
    // TODO
  }
  // -----------------------------------------------------------------------------------------------------------

  public void newGame(String filename) {
    this.gameFilename = filename;

    // Reset the cards to their default values
    resetCards();
    // Create players
    createPlayers();

  }

  public void loadGame(String filename) {
    this.gameFilename = filename;
    io.print("Load game " + gameFilename);
    // TODO
  }

  public void saveGame() {
    io.print("Save game " + gameFilename);
    // TODO
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

  }

}
