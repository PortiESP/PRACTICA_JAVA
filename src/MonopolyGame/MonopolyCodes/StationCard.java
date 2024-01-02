package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class StationCard extends MonopolyCode {
  // Attributes
  private int[] rent;
  private Player owner;

  // Constructor
  public StationCard(String description, String rent1, String rent2, String rent3, String rent4) {
    this.description = description;
    this.rent = new int[] { Integer.parseInt(rent1), Integer.parseInt(rent2), Integer.parseInt(rent3),
        Integer.parseInt(rent4) };
  }

  @Override
  public String doOperation(Player player) {
    // TODO Auto-generated method stub
    return null;
  }

}
