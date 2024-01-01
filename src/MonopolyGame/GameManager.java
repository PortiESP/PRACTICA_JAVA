package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;

public class GameManager {
  // Attributes
  private IOManager io = new IOManager(); // IO Manager (print, read inputs, etc.)

  // Constructor
  public GameManager() {
    this.languageManager = new LanguageManager();
  }

  // Start the game
  public void start() {
    io.print("WELCOME");
  }
}
