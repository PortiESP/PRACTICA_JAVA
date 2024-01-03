package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class ServiceCard extends Property {
  private int[] priceFactor;

  // Constructors (for serialization)
  public ServiceCard() {
  }

  // Constructor
  public ServiceCard(String description, String priceFactor1, String priceFactor2) {
    this.description = description;
    this.priceFactor = new int[] { Integer.parseInt(priceFactor1), Integer.parseInt(priceFactor2) };
  }

  @Override
  public void doOperation(Player player) {
    // TODO Auto-generated method stub
  }

}
