package src.MonopolyGame;

import src.MonopolyGame.IO.IOManager;

public class Game {
  // Attributes
  private IOManager io = new IOManager(); // IO Manager (print, read inputs, etc.)
  private String gameFilename; // Save game filename

  // Methods
  // ---------------------------------------------- Game main loop ----------------------------------------------
  public void play() {
    // TODO
  }
  // -----------------------------------------------------------------------------------------------------------

  public void newGame(String filename) {
    this.gameFilename = filename;
    io.print("New game " + gameFilename);
    // TODO
  }

  public void loadGame(String filename) {
    this.gameFilename = filename;
    io.print("Load game " + gameFilename);
    // TODO
  }
}
