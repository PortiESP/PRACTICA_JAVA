package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.MenuBuilder;

public abstract class Property extends MonopolyCode {
  protected Player owner;
  protected int mortgageValue;
  protected boolean isMortgaged;
  protected int rent;
  protected int propertyPrice;

  @Override
  /**
   * Do the corresponding operation for this property
   * 
   * <p>
   * <ul>
   * <li>If the property is owned by someone</li>
   *    <ul>
   *    <li>If the owner is not the player -> Pay the rent</li>
  *     <li>If the owner is the player -> Ask if he wants to manage the property</li>
   *    </ul>
   * <li>If the property is not owned by anyone -> Ask if the player wants to buy the property</li>
   * </ul>
   * 
   * @param player The player who landed on this property
   */
  public void doOperation(Player player) {
    // This property is owned by a player (current player or other player)
    if (isOwned()) {
      // If the owner is not the player
      if (!isOwnedBy(player)) {
        // Pay rent
        int payAmount = calculateAmountToPay();
        // Pay the owner
        if (payAmount > 0) {
          String message = String.format(IOManager.getMsg("SUMMARY_PLAYER_PAY_RENT"),
              player.getName(), payAmount, owner.getName(), this.description);
          MenuBuilder.alert("TRANSACTION", message);
          player.pay(payAmount, owner);
        } else
          MenuBuilder.alert("TRANSACTION", "PROPERTY_IS_MORTGAGED");
      }
      // If the owner is the player
      else {
        if (MenuBuilder.askYesNo("ASK_MANAGE_PROPERTY")) {
          while (propertyManagementMenu() != 0)
            ;
        }
      }
    }
    // This property is not owned (ask player if he wants to buy it)
    else {
      // Ask the player if he wants to buy the property
      // If the player wants to buy the property
      String prompt = String.format(IOManager.getMsg("PROPERTY_ASK_BUY"), this.description, this.propertyPrice);
      if (MenuBuilder.askYesNo(prompt)) {
        // Buy the property
        player.buyProperty(this);
      }
      // The player doesn't want to buy the property
      else {
        MenuBuilder.alert("INFO", "PLAYER_DONT_BUY_PROPERTY");
      }
    }
  }

  /**
   * Show the property management menu and do the corresponding operation.
   * 
   * <p>
   * The option is returned back to know if the player wants to exit the menu or not.
   * </p>
   * 
   * @return The option chosen by the player
   */
  public int propertyManagementMenu() {
    String mortgaged = IOManager.getMsg("MORTGAGED").toUpperCase();
    // Ask the player what he wants to do with the property
    String title = String.format(IOManager.getMsg("PROPERTY_MANAGEMENT_MENU"), this.description);
    String[] options = {
        String.format("%s", (isMortgaged ? "#" : "1"),
            (isMortgaged ? "-- " + mortgaged + " --" : "PROPERTY_MANAGEMENT_MORTGAGE")),
        "PROPERTY_MANAGEMENT_PAY_OFF_MORTGAGE",
        String.format("%s", (isMortgaged ? "#" : "3"),
            (isMortgaged ? "-- " + mortgaged + " --" : "PROPERTY_MANAGEMENT_SELL")),
        "EXIT"
    };

    // Print the menu
    int opt = MenuBuilder.menu(title, options);

    // If the property is mortgaged
    if (isMortgaged)
      if (opt == 1 || opt == 3) {
        MenuBuilder.alert("WARN", "PROPERTY_MANAGEMENT_IS_MORTGAGED");
        return -1;
      }

    // Do the chosen operation
    if (opt == 1) {
      mortgageProperty();
      return 1;
    } else if (opt == 2) {
      payOffMortgage();
      return 2;
    } else if (opt == 3) {
      sellProperty();
      return 3;
    } else if (opt == 0) {
      return 0;
    }

    return -1; // Should never happen
  }

  public String summary() {
    if (isMortgaged) {
      return String.format("[%s]: %s", IOManager.getMsg("MORTGAGED"));
    } else {
      return String.format("[%s]: %s=(%d) ~ %s=%s", this.description,
          IOManager.getMsg("INCOME"), calculateAmountToPay(),
          IOManager.getMsg("MORTGAGED"), isMortgaged() ? IOManager.getMsg("YES") : IOManager.getMsg("NO"));
    }
  }

  /**
   * Calculate the amount to pay for the rent/fare of this property.
   * 
   * @return The amount to pay for the rent/fare of this property
   */
  public abstract int calculateAmountToPay();

  /**
   * Mortgage this property, the player receives the mortgage value.
   */
  public void mortgageProperty() {
    // If the property is already mortgaged
    if (this.isMortgaged) {
      MenuBuilder.alert("WARN", "PROPERTY_ALREADY_MORTGAGED");
      return;
    }

    // Mortgage the property
    owner.increaseMoney(this.mortgageValue);
    this.isMortgaged = true;
  }

  /**
   * Pay off the mortgage of this property, the player pays the mortgage value.
   */
  public void payOffMortgage() {
    // If the property is not mortgaged
    if (!this.isMortgaged) {
      MenuBuilder.alert("WARN", "PROPERTY_NOT_MORTGAGED");
      return;
    }

    // If the player has enough money to pay off the mortgage
    if (owner.decreaseMoney(this.mortgageValue) != -1) {
      // Pay off the mortgage
      this.isMortgaged = false;
    }
    // If the player doesn't have enough money to pay off the mortgage
    else {
      MenuBuilder.alert("WARN", "PLAYER_CANT_AFFORD");
    }
  }

  /**
   * Sell this property, the player receives the property price.
   */
  public void sellProperty() {
    // If the property is mortgaged
    if (this.isMortgaged) {
      MenuBuilder.alert("WARN", "PROPERTY_CANT_SELL_MORTGAGED");
      return;
    }

    // Sell the property
    owner.increaseMoney(this.propertyPrice);
    this.owner = null;
  }

  /**
   * Check if this property is owned by someone.
   * 
   * @return True if this property is owned by someone, false otherwise
   */
  public boolean isOwned() {
    return owner != null;
  }

  /**
   * Check if this property is owned by the given player.
   * 
   * @param player The player to check if he owns this property
   * @return True if this property is owned by the given player, false otherwise
   */
  public boolean isOwnedBy(Player player) {
    return owner.equals(player);
  }

  /**
   * Check if this property is mortgaged.
   * 
   * @return True if this property is mortgaged, false otherwise
   */
  public boolean isMortgaged() {
    return isMortgaged;
  }

  // ---------------------------------------- Getters and Setters ----------------------------------------
  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public int getMortgageValue() {
    return mortgageValue;
  }

  public void setMortgageValue(int mortgageValue) {
    this.mortgageValue = mortgageValue;
  }

  public void setMortgaged(boolean isMortgaged) {
    this.isMortgaged = isMortgaged;
  }

  public int getRent() {
    return rent;
  }

  public void setRent(int rent) {
    this.rent = rent;
  }

  public int getPropertyPrice() {
    // If the property is mortgaged, return the mortgage value (half of the property price)
    if (isMortgaged)
      return mortgageValue;
    // If the property is not mortgaged, return the full property price
    else
      return propertyPrice;
  }

  public void setPropertyPrice(int propertyPrice) {
    this.propertyPrice = propertyPrice;
  }

}
