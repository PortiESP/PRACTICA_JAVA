package src.MonopolyGame;

import java.io.Serializable;
import java.util.ArrayList;

import src.MonopolyGame.MonopolyCodes.Property;

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
