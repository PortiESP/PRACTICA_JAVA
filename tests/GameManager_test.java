package tests;

import org.junit.Test;

import src.MonopolyGame.Const;
import src.MonopolyGame.GameManager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameManager_test {

  /*
   * Test if the saved files list is retrieved correctly
   */
  @Test
  public void savedFilesList() {
    // Create a mock file
    File file = new File(Const.SAVES_PATH + "unit_testing_saved_file" + ".xml");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Get the list of saved files
    GameManager gameManager = new GameManager();
    ArrayList<String> files = gameManager.getSavedGamesFilesList(Const.SAVES_PATH);

    // Check if the file is in the list
    assertTrue(files.contains("unit_testing_saved_file.xml"));

    // Delete the mock file
    file.delete();
  }

  /*
   * Test if the `savedGameExists` method works correctly
   */
  @Test
  public void savedGameExists() {
    // Create a mock file
    File file = new File(Const.SAVES_PATH + "unit_testing_saved_file" + ".xml");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Check if the file exists
    GameManager gameManager = new GameManager();
    assertTrue(gameManager.savedGameExists("unit_testing_saved_file"));

    // Delete the mock file
    file.delete();
  }

  /*
   * Test if the `deleteSavedGame` method works correctly
   */
  @Test
  public void deleteSavedGame() {
    // Create a mock file
    File file = new File(Const.SAVES_PATH + "unit_testing_saved_file" + ".xml");
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Delete the file
    GameManager gameManager = new GameManager();
    gameManager.deleteSavedGame("unit_testing_saved_file");

    // Check if the file exists
    assertFalse(gameManager.savedGameExists("unit_testing_saved_file"));
  }
}
