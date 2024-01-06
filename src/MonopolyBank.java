package src;

import src.MonopolyGame.GameManager;

public class MonopolyBank {
  public static void main(String[] args) {
    // Instantiate the game manager and start the game
    GameManager gameManager = new GameManager();
    // Run the game (loop for the back to menu option, the game ends when the user selects the exit option)
    while (true) {
      gameManager.start(); // Normal mode
      // gameManager.startDebug(); // Debug mode
    }
  }
}
