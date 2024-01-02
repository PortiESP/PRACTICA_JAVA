package src.MonopolyGame;

import java.util.ArrayList;

import src.MonopolyGame.MonopolyCodes.Property;

public class Player {
  // Attributes
  private String name; // Player name
  private int money; // Player money
  private boolean isBroken; // Is the player broken?
  private ArrayList<Property> properties = new ArrayList<>(); // Player properties

  // Constructor
  public Player(String name) {
    this.name = name;
    this.money = Const.STARTING_MONEY;
    this.isBroken = false;
  }

  // Methods
  public String toString() {
    return String.format("Player %s [$%d]", this.name, this.money);
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

}
