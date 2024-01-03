package src.MonopolyGame.MonopolyCodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Player;

public class RepairsCard extends MonopolyCode {
  // Attributes
  private String description;
  private int pricePerHouse;
  private int pricePerHotel;

  // Constructor (for serialization)
  public RepairsCard() {
  }

  // Constructor
  public RepairsCard(String description) {
    this.description = description;
    parsePricePerHouse(); // Store the price per house and hotel
  }

  @Override
  public void doOperation(Player player) {
    int houses = player.getTotalHouses();
    int hotels = player.getTotalHotels();
    int total = houses * this.pricePerHouse + hotels * this.pricePerHotel;

    player.pay(total);
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPricePerHouse() {
    return pricePerHouse;
  }

  public void setPricePerHouse(int pricePerHouse) {
    this.pricePerHouse = pricePerHouse;
  }

  public int getPricePerHotel() {
    return pricePerHotel;
  }

  public void setPricePerHotel(int pricePerHotel) {
    this.pricePerHotel = pricePerHotel;
  }

}
