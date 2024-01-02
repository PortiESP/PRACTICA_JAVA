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
          monopolyCodes.put(cardCode, new StreetCard());
        else if ("SERVICE".equals(cardType))
          monopolyCodes.put(cardCode, new ServiceCard());
        else if ("STATION".equals(cardType))
          monopolyCodes.put(cardCode, new StationCard());
        else if ("REPAIRS_CARD".equals(cardType))
          monopolyCodes.put(cardCode, new RepairsCard());
        else
          IOManager.log("Error adding card, the code " + cardCode + " has an invalid type: " + cardType);

        // Read next line
        line = reader.readLine();
      }

      reader.close();

    } catch (Exception e) {
      IOManager.log(filename + " not found, or invalid card code readed");
      e.printStackTrace();
    }
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

}
