package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.MenuBuilder;

public class ServiceCard extends Property {
  private int[] priceFactor;

  // Constructors (for serialization)
  public ServiceCard() {
  }

  // Constructor
  public ServiceCard(String description, String priceFactor1, String priceFactor2, String mortgageValue) {
    this.description = description;
    this.mortgageValue = Integer.parseInt(mortgageValue);
    this.propertyPrice = Integer.parseInt(mortgageValue) * 2;
    this.priceFactor = new int[] { Integer.parseInt(priceFactor1), Integer.parseInt(priceFactor2) };
  }

  @Override
  public int calculateAmountToPay() {

    // If the property is mortgaged, no rent is paid
    if (isMortgaged)
      return 0;

    // Ask the player to roll the dice
    int dice = MenuBuilder.readInt("PROMPT_ROLL_THE_DICE");

    // Pay the rent based on the number of stations the owner has (at least 1 since we are paying rent)
    return dice * priceFactor[owner.getServicesCount() - 1];
  }

  @Override
  public String summary() {
    if (isMortgaged) {
      return String.format("[%s]: %s", IOManager.getMsg("MORTGAGED"));
    } else {
      return String.format("[%s]: %s=(%s * %d) ~ %s=%s", this.description,
          IOManager.getMsg("INCOME"), IOManager.getMsg("DICE"), priceFactor[owner.getServicesCount() - 1],
          IOManager.getMsg("MORTGAGED"), isMortgaged() ? IOManager.getMsg("YES") : IOManager.getMsg("NO"));
    }
  }

  public int[] getPriceFactor() {
    return priceFactor;
  }

  public void setPriceFactor(int[] priceFactor) {
    this.priceFactor = priceFactor;
  }

}
