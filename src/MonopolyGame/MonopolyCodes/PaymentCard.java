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
    IOManager.printMsg("PAYMENT_CARD");
    IOManager.print(String.format(": [%s]", this.description));
    if (this.amount < 0)
      if (player.decreaseMoney(-this.amount) == -1) {
        if (!player.liquidateAssets(-this.amount)) {
          player.loser();
        }
      } else {
        player.increaseMoney(amount);
      }
  }
}
