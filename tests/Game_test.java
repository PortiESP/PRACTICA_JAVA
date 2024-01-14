package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import src.MonopolyGame.Game;
import src.MonopolyGame.GameManager;
import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;

public class Game_test {

  @BeforeAll
  public void setUp() {
    // Set the language to English
    IOManager.setLanguage("English");
  }

  /*
   * Test the `saveGame` method
   */
  @Test
  public void saveGame() {
    // Create a new game
    Game game = new Game();
    // Set the game state
    game.setGameFilename("unit_testing_saved_file");
    // Save the game
    game.saveGame();

    // Check if the file exists
    GameManager gameManager = new GameManager();
    assertTrue(gameManager.savedGameExists("unit_testing_saved_file"));

    // Delete the mock file
    gameManager.deleteSavedGame("unit_testing_saved_file");
  }

  /*
   * Test the `eliminatePlayers` method (no players eliminated)
   */
  @Test
  public void eliminatePlayers() {
    // Create a new game
    Game game = new Game();
    // Use the template players (4 players)
    useTemplatePlayers(game);

    // Eliminate the players
    game.eliminatePlayers();

    // Check if the players are eliminated
    ArrayList<Player> players = game.getPlayers();
    assertEquals(4, players.size());
  }

  /*
   * Test the `eliminatePlayers` method (1 player eliminated)
   */
  @Test
  public void eliminatePlayers1() {
    // Create a new game
    Game game = new Game();
    // Use the template players (4 players)
    useTemplatePlayers(game);

    // Set the player's balance to 0
    ArrayList<Player> players = game.getPlayers();
    players.get(0).setBroke(true);

    // Eliminate the players
    game.eliminatePlayers();

    // Check if the players are eliminated
    assertEquals(3, players.size());
  }

  /*
  * Test the `checkWinner` method (2 players eliminated)
  */
  @Test
  public void checkWinnerTrue() {
    // Initialize the stdin buffer
    stdinBuffer("\n");

    // Create a new game
    Game game = new Game();
    IOManager.setLanguage("English");

    // Use the template players (4 players)
    useTemplatePlayers(game);

    // Set the player's balance to 0
    ArrayList<Player> players = game.getPlayers();
    players.get(0).setBroke(true);
    players.get(1).setBroke(true);
    players.get(2).setBroke(true);

    // Eliminate the players
    game.eliminatePlayers();

    // Check if the players are eliminated
    assertTrue(game.checkWinner());
  }

  /*
   * Test the `checkWinner` method (should not be a winner)
   */
  @Test
  public void checkWinnerFalse() {
    // Initialize the stdin buffer
    stdinBuffer("\n");

    // Create a new game
    Game game = new Game();
    IOManager.setLanguage("English");

    // Use the template players (4 players)
    useTemplatePlayers(game);

    // Set the player's balance to 0
    ArrayList<Player> players = game.getPlayers();
    players.get(0).setBroke(true);
    players.get(1).setBroke(true);

    // Eliminate the players
    game.eliminatePlayers();

    // Check if the players are eliminated
    assertFalse(game.checkWinner());
  }

  // ==============================[ Functions ]===============================>>>
  private void useTemplatePlayers(Game game) {

    // Create the ArrayList
    ArrayList<Player> players = new ArrayList<>();

    // Create the players
    players.add(new Player("Anthony"));
    players.add(new Player("Barbara"));
    players.add(new Player("Charles"));
    players.add(new Player("Diana"));

    // Set the players
    game.setPlayers(players);
  }

  // Function to simulate the user typing a string in the console
  private void stdinBuffer(String input) {
    // Set the input stream to the input string
    System.setIn(new ByteArrayInputStream(input.getBytes()));
  }
}
