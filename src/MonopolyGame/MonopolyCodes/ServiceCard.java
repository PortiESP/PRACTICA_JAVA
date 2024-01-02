package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class ServiceCard extends MonopolyCode {
  private String description;
  private int[] priceFactor;
  private Player owner;

  // Constructor
  public ServiceCard(String description, String priceFactor1, String priceFactor2) {
    this.description = description;
    this.priceFactor = new int[] { Integer.parseInt(priceFactor1), Integer.parseInt(priceFactor2) };
  }

  @Override
  public String doOperation(Player player) {
    // TODO Auto-generated method stub
    return null;
  }

}
