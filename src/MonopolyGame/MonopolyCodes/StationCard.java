package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class StationCard extends Property {
  // Attributes
  private int rent[];

  // Constructors (for serialization)
  public StationCard() {
  }

  // Constructor
  public StationCard(String description, String rent1, String rent2, String rent3, String rent4) {
    this.description = description;
    this.rent = new int[] { Integer.parseInt(rent1), Integer.parseInt(rent2), Integer.parseInt(rent3),
        Integer.parseInt(rent4) };
  }

  @Override
  public void doOperation(Player player) {
    // TODO Auto-generated method stub
  }

}
