package src.MonopolyGame.IO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
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
  private Map<String, String> gameStrings;

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

      // Initialize the map
      gameStrings = new HashMap<String, String>();

      // Read the file
      String line = reader.readLine();
      while (line != null) {
        // Parse the line
        String[] parts = line.split("=", 2); // Split the line in ID and STRING (values can contain the character `=` since the splitter limits the split to 2 parts)
        gameStrings.put(parts[0].trim(), parts[1].trim()); // Add key-value pair to the map

        IOManager.log("Added string  [" + parts[0].trim() + "] with value [" + parts[1].trim() + "]"); // DEBUG

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
  public String get(String id) throws RuntimeException {
    if (gameStrings == null) {
      throw new RuntimeException("[!] There is no dictionary loaded");
    }
    return gameStrings.get(id);
  }

  // DEBUG
  public void readFile() {

    ArrayList<Integer> dataList = new ArrayList<Integer>();

    // Read input  
    try {
      InputStream in = new FileInputStream("data.txt");

      int data = -1;

      while (data != -1) {
        data = in.read();
        dataList.add(data);
      }

      // Close the reader
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Write output
    try {
      OutputStream os = new FileOutputStream(Const.LANGUAGES_PATH + "prueba.txt");
      os.write(dataList.toString().getBytes());
      os.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void readFileStrings() {

    try (Reader in = new FileReader(Const.LANGUAGES_PATH + "prueba.txt")) {
      BufferedReader buf = new BufferedReader(in, 10);
      String s;

      do {
        s = buf.readLine();
        System.out.print(s);
      } while (s != null);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  // ---------------------------------------- GETTERS AND SETTERS ----------------------------------------

  public Map<String, String> getGameStrings() {
    return gameStrings;
  }

  public void setGameStrings(Map<String, String> gameStrings) {
    this.gameStrings = gameStrings;
  }

}
