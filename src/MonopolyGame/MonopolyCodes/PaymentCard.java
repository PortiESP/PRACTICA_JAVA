package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class PaymentCard extends MonopolyCode {
  // Attributes
  private int amount; // Amount to pay

  // Constructors (for serialization)
  public PaymentCard() {
  }

  // Constructor
  public PaymentCard(String description) {
    this.description = description;
    this.amount = parseIntFromDescription();
  }

  @Override
  public String doOperation(Player player) {
    // TODO Auto-generated method stub
    return null;
  }
}
