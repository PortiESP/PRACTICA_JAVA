package src.MonopolyGame;

import java.io.Serializable;
import java.util.ArrayList;

import src.MonopolyGame.MonopolyCodes.Property;

public class Player implements Serializable {
  // Attributes
  private String name; // Player name
  private int money; // Player money
  private boolean isBroken; // Is the player broken?
  private ArrayList<Property> properties = new ArrayList<>(); // Player properties

  // Constructors (for serialization)
  public Player() {
  }

  // Constructor
  public Player(String name) {
    this.name = name;
    this.money = Const.STARTING_MONEY;
    this.isBroken = false;
  }

  // Methods
  public String toString() {
    return String.format("Player %s [$%d] - %s", this.name, this.money, this.properties.toString());
  }

  public String summary() {
    return String.format("%s [$%d]", this.name, this.money);
  }

  @Override
  public boolean equals(Object player) {
    return this.name.equals(((Player) player).name);
  }

  public String getName() {
    return name;
  }

  public int getMoney() {
    return money;
  }

  public boolean isBroken() {
    return isBroken;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public void setBroken(boolean isBroken) {
    this.isBroken = isBroken;
  }

  public ArrayList<Property> getProperties() {
    return properties;
  }

  public void setProperties(ArrayList<Property> properties) {
    this.properties = properties;
  }

}
