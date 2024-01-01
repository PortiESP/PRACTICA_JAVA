package src.MonopolyGame.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import src.MonopolyGame.IO.IOManager;

public class LanguageManager {
  // Map of the game strings (ID=STRING)
  private Map<String, String> gameStrings = new HashMap<>();

  // Load a new language in the map
  public void load(String filename) {
    try {
      // Read the language file (plain text)
      Reader file = new FileReader("./config/languages/" + filename + ".txt");
      BufferedReader reader = new BufferedReader(file);

      // Read the file
      String line = reader.readLine();
      while (line != null) {
        String[] parts = line.split("=", 2); // Split the line in ID=STRING (values can contain the character `=` since
                                             // the splitter limits the split to 2 parts)
        gameStrings.put(parts[0], parts[1]); // Add key-value pair to the map
        line = reader.readLine(); // Read the next line
      }

      reader.close(); // Close the reader

    } catch (Exception e) {
      IOManager.log(filename + " not found");
      e.printStackTrace();
    }
  }

  // Get a string from the map
  public String get(String id) {
    return gameStrings.get(id);
  }
}
