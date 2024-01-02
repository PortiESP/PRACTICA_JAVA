package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Const;

public abstract class MonopolyCode {
  // Attributes
  protected String description; // Card description

  // Methods
  public abstract String doOperation(Player player);

  public String toString() {
    return this.description;
  }

  public int parseIntFromDescription() {
    // Parse using regex
    Pattern pattern = Pattern.compile("(-?\\d+)" + Const.CURRENCY_SYMBOL);
    Matcher matcher = pattern.matcher(this.description);
    if (matcher.find()) // If the number is found
      return Integer.parseInt(matcher.group(1));
    else // If the number is not found
      return 0;
  }

}
