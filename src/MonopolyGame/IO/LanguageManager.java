package src.MonopolyGame.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import src.MonopolyGame.Const;

/**
 * This class manages the language loaded in the game. It will read a language and store the strings in a map. The strings can be retrieved using the ID.
 * 
 * <p>
 * The language files are plain text files with the following format: {@code ID=STRING}. The ID is the key to get the string from the map.
 * </p>
 * 
 * <p>
 * It contains the method {@code load(String filename)} to load a language and get a string from the map.
 * </p>
 */
public class LanguageManager {
  // Map of the game strings (ID=STRING)
  private Map<String, String> gameStrings = new HashMap<>();

  /**
   * Load a language from its the language file. The language file must be in the {@code Const.LANGUAGES_PATH} directory.
   * 
   * @param filename The filename of the language file (without the extension). Example: {@code "English"} (<em>the file must be {@code "English.txt"}</em>).
   */
  public void load(String filename) {
    try {
      // Prepare the reader
      Reader file = new FileReader(Const.LANGUAGES_PATH + filename + ".txt");
      BufferedReader reader = new BufferedReader(file);

      // Read the file
      String line = reader.readLine();
      while (line != null) {
        // Parse the line
        String[] parts = line.split("=", 2); // Split the line in ID and STRING (values can contain the character `=` since the splitter limits the split to 2 parts)
        gameStrings.put(parts[0], parts[1]); // Add key-value pair to the map

        IOManager.log("Added string  [" + parts[0] + "] with value [" + parts[1] + "]"); // DEBUG

        // Read the next line
        line = reader.readLine();
      }

      // Close the reader
      reader.close();

    } catch (Exception e) {
      IOManager.log(filename + " not found");
      e.printStackTrace();
    }
  }

  /**
   * Get a string from the map using its ID.
   * 
   * @param id The ID of the string to get. Example: {@code "NEW_GAME"}
   * @return The string with the given ID or {@code null} if the ID is not found in the map. Example: {@code "New Game"} (<em>from the English language file</em>)
   */
  public String get(String id) {
    return gameStrings.get(id);
  }
}
