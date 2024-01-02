package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;

public class StreetCard extends MonopolyCode {
  // Attributes
  private int rent;
  private int[] wHousesRent;
  private int wHotelRent;
  private int housePrice;
  private int hotelPrice;
  private int mortgageValue;
  private int propertyPrice;
  private int houseCount;
  private int hotelCount;
  private Player owner;

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
  public String doOperation(Player player) {
    // TODO Auto-generated method stub
    return null;
  }

}
