package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;

public abstract class Property extends MonopolyCode {
  protected Player owner;
  protected int mortgageValue;
  protected boolean isMortgaged;
  protected int rent;
  protected int propertyPrice;

  @Override
  public void doOperation(Player player) {
    // This property is owned by a player (current player or other player)
    if (isOwned()) {
      // If the owner is not the player
      if (!isOwnedBy(player)) {
        // Pay rent
        int payAmount = calculateAmountToPay();
        // Pay the owner
        if (payAmount > 0)
          player.pay(payAmount, owner);
        else
          IOManager.printlnMsg("PROPERTY_IS_MORTGAGED");
      }
      // If the owner is the player
      else {
        if (IOManager.askYesNo("ASK_MANAGE_PROPERTY")) {
          while (propertyManagementMenu(player) != 0)
            ;
        }
      }
    }
    // This property is not owned (ask player if he wants to buy it)
    else {
      // Ask the player if he wants to buy the property
      // If the player wants to buy the property
      String prompt = String.format(IOManager.getMsg("PROPERTY_ASK_BUY"), this.description, this.propertyPrice);
      if (IOManager.askYesNo(prompt)) {
        // Buy the property
        player.buyProperty(this);
      }
      // The player doesn't want to buy the property
      else {
        IOManager.printlnMsg("PLAYER_DONT_BUY_PROPERTY");
      }
    }
  }

  public int propertyManagementMenu(Player player) {
    // Ask the player what he wants to do with the property
    IOManager.print("\n");
    IOManager.printlnMsg("PROPERTY_MANAGEMENT_MENU", this.description);
    IOManager.print("\n");
    IOManager.print("\t- [1] Mortgage property\n");
    IOManager.print("\t- [2] Pay off property mortgage\n");
    IOManager.print("\t- [3] Sell property\n");
    IOManager.print("\t- [0] Exit\n");
    IOManager.print("\n");

    // Get the player's choice
    int opt = IOManager.readInt("PROMPT_OPTION", 0, 3);

    // Do the chosen operation
    if (opt == 1) {
      mortgageProperty(player);
      return 1;
    } else if (opt == 2) {
      payOffMortgage(player);
      return 2;
    } else if (opt == 3) {
      sellProperty(player);
      return 3;
    } else if (opt == 4) {
      return 0;
    }

    return -1; // Should never happen
  }

  public abstract int calculateAmountToPay();

  public void mortgageProperty(Player player) {
    // If the property is already mortgaged
    if (this.isMortgaged) {
      IOManager.printlnMsg("PROPERTY_ALREADY_MORTGAGED");
      return;
    }

    // Mortgage the property
    player.increaseMoney(this.mortgageValue);
    this.isMortgaged = true;
  }

  public void payOffMortgage(Player player) {
    // If the property is not mortgaged
    if (!this.isMortgaged) {
      IOManager.printlnMsg("PROPERTY_NOT_MORTGAGED");
      return;
    }

    // If the player has enough money to pay off the mortgage
    if (player.decreaseMoney(this.mortgageValue) != -1) {
      // Pay off the mortgage
      this.isMortgaged = false;
    }
    // If the player doesn't have enough money to pay off the mortgage
    else {
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
    }
  }

  public void sellProperty(Player player) {
    // If the property is mortgaged
    if (this.isMortgaged) {
      IOManager.printlnMsg("PROPERTY_CANT_SELL_MORTGAGED");
      return;
    }

    // Sell the property
    player.increaseMoney(this.propertyPrice);
    this.owner = null;
  }

  public boolean isOwned() {
    return owner != null;
  }

  public boolean isOwnedBy(Player player) {
    return owner.equals(player);
  }

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
    return propertyPrice;
  }

  public void setPropertyPrice(int propertyPrice) {
    this.propertyPrice = propertyPrice;
  }

}
