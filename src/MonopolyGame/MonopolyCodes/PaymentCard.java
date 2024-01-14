package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.MenuBuilder;

/**
 * The PaymentCard will make the player pay or receive money
 */
public class PaymentCard extends MonopolyCode {
  // Attributes
  private int amount; // Amount to pay/receive (negative = player pays, positive = player receives)

  // Constructors (for serialization)
  public PaymentCard() {
  }

  // Constructor
  public PaymentCard(String description) {
    this.description = description;
    this.amount = parsePriceFromDescription();
  }

  /**
   * This method will make the player pay or receive money based on the value of the attribute {@code amount} of this card. 
   * 
   * <ul>
   * <li>If the amount is <strong>negative</strong>, the player pays the amount.</li>
   * <li>If the amount is <strong>positive</strong>, the player receives the amount.</li>
   * </ul>
   * 
   * <hr/>
   */
  @Override
  public void doOperation(Player player) {
    // The amount is negative (the player pays money)
    if (this.amount < 0) {
      // Print the operation summary
      MenuBuilder.alert("PAYMENT_CARD_TITLE", this.description);

      // Pay the amount
      if (player.decreaseMoney(-this.amount) == -1) {
        if (!player.liquidateAssets(-this.amount)) {
          player.loser();
        }
      }
    }
    // The amount is positive (the player receives money)
    else {
      // Print the operation summary
      MenuBuilder.alert("PAYMENT_CARD_TITLE", this.description);
      // Receive the amount
      player.increaseMoney(amount);
    }
  }

  // ---------------------------------------- Getters and Setters ----------------------------------------
  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
