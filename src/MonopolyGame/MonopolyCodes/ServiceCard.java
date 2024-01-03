package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.IO.IOManager;

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
  public int calculateAmountToPay() {

    // If the property is mortgaged, no rent is paid
    if (isMortgaged)
      return 0;

    // Ask the player to roll the dice
    int dice = IOManager.readInt("PROMPT_ROLL_THE_DICE");

    // Pay the rent based on the number of stations the owner has (at least 1 since we are paying rent)
    return dice * priceFactor[owner.getServicesCount() - 1];
  }

}
