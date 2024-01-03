package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;

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
  public void doOperation(Player player) {
    IOManager.print("\n");
    IOManager.print(String.format("%s: [%s]\n", IOManager.getMsg("PAYMENT_CARD"), this.description));

    // The amount is negative (the player pays money)
    if (this.amount < 0) {
      if (player.decreaseMoney(-this.amount) == -1) {
        if (!player.liquidateAssets(-this.amount)) {
          player.loser();
        }
      }
    }
    // The amount is positive (the player receives money)
    else {
      player.increaseMoney(amount);
    }
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
