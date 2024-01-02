package src.MonopolyGame.MonopolyCodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Player;

public class RepairsCard extends MonopolyCode {
  // Attributes
  private String description;
  private int pricePerHouse;
  private int pricePerHotel;

  // Constructor
  public RepairsCard(String description) {
    this.description = description;
    parsePricePerHouse(); // Store the price per house and hotel
  }

  @Override
  public String doOperation(Player player) {
    // TODO Auto-generated method stub
    return null;
  }

  public void parsePricePerHouse() {
    String pricePerHousePattern = ".+(\\d+)€.+(\\d+)€";
    Matcher matcher = Pattern.compile(pricePerHousePattern).matcher(this.description);

    if (matcher.find()) {
      this.pricePerHouse = Integer.parseInt(matcher.group(1));
      this.pricePerHotel = Integer.parseInt(matcher.group(2));
    } else
      throw new IllegalArgumentException("Invalid description for repairs card: " + this.description);
  }

}
