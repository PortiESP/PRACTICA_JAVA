package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public abstract class Property extends MonopolyCode {
  protected Player owner;
  protected int mortgageValue;
  protected boolean isMortgaged;
  protected int rent;

  public boolean isOwned() {
    return owner != null;
  }

  public boolean isOwnedBy(Player player) {
    return owner.equals(player);
  }

  public boolean isMortgaged() {
    return isMortgaged;
  }
}
