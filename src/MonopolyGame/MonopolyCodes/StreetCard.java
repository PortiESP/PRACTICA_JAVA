package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class StreetCard extends Property {
  // Attributes
  private int[] wHousesRent;
  private int wHotelRent;
  private int housePrice;
  private int hotelPrice;
  private int propertyPrice;
  private int houseCount;
  private int hotelCount;

  // Constructors (for serialization)
  public StreetCard() {
  }

  // Constructor
  public StreetCard(String description, String rent, String w1house, String w2house, String w3house, String w4house,
      String whotel,
      String housePrice,
      String hotelPrice, String mortgageValue) {
    this.description = description;
    this.rent = Integer.parseInt(rent);
    this.wHousesRent = new int[] { Integer.parseInt(w1house), Integer.parseInt(w2house), Integer.parseInt(w3house),
        Integer.parseInt(w4house) };
    this.wHotelRent = Integer.parseInt(whotel);
    this.housePrice = Integer.parseInt(housePrice);
    this.hotelPrice = Integer.parseInt(hotelPrice);
    this.mortgageValue = Integer.parseInt(mortgageValue);
    this.propertyPrice = Integer.parseInt(mortgageValue) * 2;
    this.houseCount = 0;
    this.hotelCount = 0;
  }

  @Override
  public void doOperation(Player player) {

  }

}
