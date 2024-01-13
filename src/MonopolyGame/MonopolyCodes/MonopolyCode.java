package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Const;

/**
 * The MonopolyCode class is the base class for all Monopoly codes in the game. This class is defined as abstract to define the common methods and attributes for all Monopoly codes and also implement some of those methods.
 */
public abstract class MonopolyCode implements Serializable {
  // Attributes
  protected String description; // Card description

  /**
   * Do the corresponding operation for this Monopoly code
   * 
   * @param player
   */
  public abstract void doOperation(Player player);

  /**
   * Parse the integer from the description of this Monopoly code
   * 
   * @return The integer parsed from the description of this Monopoly code
   */
  public int parseIntFromDescription() {
    // Parse using regex
    Pattern pattern = Pattern.compile("(-?\\d+)" + Const.CURRENCY_SYMBOL);
    Matcher matcher = pattern.matcher(this.description);
    if (matcher.find()) // If the number is found
      return Integer.parseInt(matcher.group(1));
    else // If the number is not found
      return 0;
  }

  @Override
  public String toString() {
    return this.description;
  }

  // ------------------------ Getters and Setters ------------------------
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
