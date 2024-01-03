package src.MonopolyGame;

import java.io.Serializable;
import java.util.ArrayList;

import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.MonopolyCodes.Property;
import src.MonopolyGame.MonopolyCodes.ServiceCard;
import src.MonopolyGame.MonopolyCodes.StationCard;
import src.MonopolyGame.MonopolyCodes.StreetCard;

/**
 * This class represents a player in the game. It contains the player's name, money, properties and if he is broke. 
 * 
 * <p>
 * The player's name is used to uniquely identify the player, so it is used in the equals method.
 * </p>
 * 
 * <p>
 * The player also stores the properties he owns, so it is easier to check if he has a monopoly or sell them if he is about to be broke.
 * </p>
 */
public class Player implements Serializable {
  // Attributes
  private String name; // Player name
  private int money; // Player money
  private boolean broke; // Is the player broke?
  private ArrayList<Property> properties = new ArrayList<>(); // Player properties

  // Constructors (for serialization)
  public Player() {
  }

  // Constructor
  public Player(String name) {
    this.name = name;
    this.money = Const.STARTING_MONEY;
    this.broke = false;
  }

  // Methods
  public String toString() {
    return String.format("Player %s [$%d] - %s", this.name, this.money, this.properties.toString());
  }

  /**
   * Returns a summary of the player's name, money and properties.
   * 
   * @return A string of the summary.
   */
  public String summary() {
    return String.format("%s [$%d]", this.name, this.money);
  }

  /**
   * Get the number of stations the player owns.
   * 
   * @return The number of stations the player owns.
   */
  public int getStationCount() {
    int count = 0;
    for (Property property : properties) {
      if (property instanceof StationCard)
        count++;
    }
    return count;
  }

  /**
   * Get the number of services the player owns.
   * 
   * @return The number of services the player owns.
   */
  public int getServicesCount() {
    int count = 0;
    for (Property property : properties) {
      if (property instanceof ServiceCard)
        count++;
    }
    return count;
  }

  @Override
  /**
   * Checks if the player is the same as another player.
   * 
   * @param player The player to compare.
   * @return True if the players are the same, false otherwise.
   */
  public boolean equals(Object player) {
    // If the player is the same object, return true
    return (player == this);
  }

  /**
   * Pay an amount of money to another player.
   * 
   * @param amount The amount of money to pay.
   * @param player The player to pay.
   */
  public void pay(int amount, Player player) {
    // If the player has enough money
    if (this.money >= amount) {
      // Pay the player (if the player can afford the payment)
      if (this.decreaseMoney(amount) != -1) {
        player.increaseMoney(amount);
      }
      // If the player can't afford the payment, liquidate assets
      else {
        this.liquidateAssets(amount);
      }
    }
  }

  /**
   * Pay an amount of money to the bank.
   * 
   * @param amount The amount of money to pay.
   */
  public void pay(int amount) {
    // If the player has enough money
    if (this.money >= amount) {
      // Pay the bank (if the player can afford the payment)
      if (this.decreaseMoney(amount) != -1) {
        return;
      }
      // If the player can't afford the payment, liquidate assets
      else {
        this.liquidateAssets(amount);
      }
    }
  }

  /**
   * Get the total number of houses the player owns (in all properties).
   * 
   * @return The total number of houses the player owns.
   */
  public int getTotalHouses() {
    int count = 0;
    for (Property property : properties) {
      if (property instanceof StreetCard)
        count += ((StreetCard) property).getHouseCount();
    }
    return count;
  }

  /**
   * Get the total number of hotels the player owns (in all properties).
   * 
   * @return The total number of hotels the player owns.
   */
  public int getTotalHotels() {
    int count = 0;
    for (Property property : properties) {
      if (property instanceof StreetCard && ((StreetCard) property).isHotel())
        count += 1;
    }
    return count;
  }

  /**
   * Buy a property.
   * 
   * @param property The property to buy.
   */
  public void buyProperty(Property property) {
    // If the player can afford the payment
    if (this.decreaseMoney(property.getPropertyPrice()) != -1) {
      // Buy the property
      this.properties.add(property);
      property.setOwner(this);

      return;
    }
    // If the player can't afford the payment, ask if he wants to liquidate assets
    else {
      // If the player wants to liquidate assets
      if (IOManager.askYesNo("PLAYER_LIQUIDATE_ASSETS")) {
        // Liquidate assets until the player can afford the payment
        this.liquidateAssets(property.getPropertyPrice());
        // Buy the property
        this.properties.add(property);
        property.setOwner(this);
      }
    }

    IOManager.printlnMsg("PLAYER_DONT_BUY_PROPERTY");
  }

  /**
   * Liquidate assets until the player can afford the specified amount.
   * 
   * @param amount The amount of money the player needs to afford.
   * @return True if the player is broke, false otherwise.
   */
  public boolean liquidateAssets(int amount) {
    // While the player can't afford the payment
    while (this.money < amount) {
      IOManager.printlnMsg("PLAYER_LIQUIDATE_ASSETS");
      // If the player has no properties, he is broke
      if (this.properties.size() == 0) {
        return false;
      }

      // Otherwise, print the liquidation menu
      liquidateMenu();
    }

    return true;
  }

  public void loser() {
    this.broke = true;
    IOManager.printlnMsg("PLAYER_LOSER");
  }

  /**
   * Print the liquidation menu.
   */
  public void liquidateMenu() {
    // Print the liquidation menu
    IOManager.printlnMsg("LIQUIDATE_MENU");
    IOManager.print("\n");
    IOManager.print("\t- [1] Sell a property");
    IOManager.print("\t- [2] Mortgage a property");
    IOManager.print("\n");

    // Get the user's input
    int opt = IOManager.readInt("PROMPT_OPTION", 1, 2);

    // Sell a property
    if (opt == 1) {
      printPropertiesList();
      int sellOpt = IOManager.readInt("PROMPT_OPTION", 1, this.properties.size()) - 1;

      this.sell2Bank(sellOpt);
    }
    // Mortgage a property
    else {
      printPropertiesList();
      int mortgageOpt = IOManager.readInt("PROMPT_OPTION", 1, this.properties.size()) - 1;

      this.mortgage(mortgageOpt);
    }
  }

  public void printPropertiesList() {
    IOManager.printlnMsg("PLAYER_PROPERTIES_LIST");
    for (int i = 0; i < this.properties.size(); i++) {
      IOManager.print(String.format("\t-[%d] %s\n", i + 1, this.properties.get(i).toString()));
    }
    IOManager.print("\n");
  }

  /**
   * Sell a property.
   * 
   * @param int The index of the property to sell in the player's properties list.
   */
  public void sell2Bank(int index) {
    // Get the property to sell
    Property property = this.properties.get(index);

    // Sell the property
    this.properties.remove(index);
    this.increaseMoney(property.getPropertyPrice());
    property.setOwner(null);
  }

  /**
   * Mortgage a property.
   * 
   * @param int The index of the property to mortgage in the player's properties list.
   */
  public void mortgage(int index) {
    // Get the property to mortgage
    Property property = this.properties.get(index);

    // Mortgage the property
    property.setMortgaged(true);
    this.increaseMoney(property.getMortgageValue());
  }

  /**
   * Increase the player's money by an amount.
   * 
   * @param amount The amount of money to increase.
   * @return The new amount of money the player has.
   */
  public int increaseMoney(int amount) {
    this.money += amount;
    return this.money;
  }

  /**
   * Decrease the player's money by an amount.
   * 
   * @param amount The amount of money to decrease.
   * @return (If the player could afford payment) -> The new amount of money the player has.
   *        (If the player couldn't afford payment) -> -1 (operation failed, money not decreased)
   */
  public int decreaseMoney(int amount) {
    // Try to decrease the money, if the player has enough money, return the new amount of money
    if (this.money - amount > 0) {
      this.money -= amount;
      return this.money;
    }
    // If the player doesn't have enough money, return -1 (operation failed)
    else {
      return -1;
    }
  }

  // ---------------------------------------- Getters and Setters ----------------------------------------
  public String getName() {
    return name;
  }

  public int getMoney() {
    return money;
  }

  public boolean isBroke() {
    return broke;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public void setBroke(boolean isBroke) {
    this.broke = isBroke;
  }

  public ArrayList<Property> getProperties() {
    return properties;
  }

  public void setProperties(ArrayList<Property> properties) {
    this.properties = properties;
  }

}
