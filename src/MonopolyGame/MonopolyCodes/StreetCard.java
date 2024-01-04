package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.IO.IOManager;

public class StreetCard extends Property {
  // Attributes
  private int[] wHousesRent;
  private int wHotelRent;
  private int housePrice;
  private int hotelPrice;
  private int houseCount;
  private boolean hotel;

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
    this.hotel = false;
  }

  @Override
  /**
   * Calculates the amount of money the player has to pay to the owner of the property when he lands on it
   * 
   * @return The amount of money the player has to pay
   */
  public int calculateAmountToPay() {

    // If the property is mortgaged, no rent is paid
    if (isMortgaged)
      return 0;

    // Pay the house rent
    if (houseCount > 0)
      return wHousesRent[houseCount - 1];
    // Pay the hotel rent
    else if (hotel)
      return wHotelRent;

    // Pay the minimum rent
    return rent;

  }

  @Override
  /**
   * Prints the property's information and a menu with the available operations.
   * 
   * The option chosen by the player is returned, but the operations is handled here, we just return the option to know if we need to print the menu again or not.
   * 
   * @param player The player that landed on the property
   * @return The option the player chose
   */
  public int propertyManagementMenu() {
    String y = IOManager.getMsg("YES");
    String n = IOManager.getMsg("NO");
    // Ask the player what he wants to do with the property
    IOManager.print("\n");
    IOManager.printlnMsg("PROPERTY_MANAGEMENT_MENU", this.description);
    IOManager.print("\n");
    IOManager.printlnMsg("PROPERTY_SUMMARY", houseCount, (hotel ? y : n), (isMortgaged ? y : n),
        calculateAmountToPay());
    IOManager.print("\n");
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "1"), (isMortgaged ? "-- MORTGAGED --" : "Buy houses")));
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "2"), (isMortgaged ? "-- MORTGAGED --" : "Sell houses")));
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "3"), (isMortgaged ? "-- MORTGAGED --" : "Buy hotel")));
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "4"), (isMortgaged ? "-- MORTGAGED --" : "Sell hotel")));
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "5"),
            (isMortgaged ? "-- MORTGAGED --" : "Mortgage property")));
    IOManager.print("\t- [6] Pay off property mortgage\n");
    IOManager.print(
        String.format("\t- [%s] %s\n", (isMortgaged ? "#" : "7"), (isMortgaged ? "-- MORTGAGED --" : "Sell property")));
    IOManager.print("\t- [0] Exit\n");
    IOManager.print("\n");

    // Get the player's choice
    int opt = IOManager.readInt("PROMPT_OPTION", 0, 7);

    if (isMortgaged && (opt != 6 && opt != 0)) {
      IOManager.printlnMsg("INVALID_OPTION");
      return -1;
    }

    // Do the chosen operation
    if (opt == 1) {
      buyHouses();
      return 1;
    } else if (opt == 2) {
      sellHouses();
      return 2;
    } else if (opt == 3) {
      buyHotel();
      return 3;
    } else if (opt == 4) {
      sellHotel();
      return 4;
    } else if (opt == 5) {
      mortgageProperty();
      return 5;
    } else if (opt == 6) {
      payOffMortgage();
      return 6;
    } else if (opt == 7) {
      sellProperty();
      return 0;
    } else if (opt == 0) {
      return 0;
    }

    return -1; // Should never happen
  }

  /**
   * Buys houses for the property
   * 
   * @param player
   */
  public void buyHouses() {
    int amount = IOManager.readInt("PROMPT_AMOUNT", 1, 4);
    if (owner.decreaseMoney(amount * this.housePrice) != -1) {
      this.houseCount += amount;
    } else {
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
    }
  }

  /**
   * Sells houses for the property
   * 
   * @param player The player that owns the property
   */
  public void sellHouses() {
    int amount = IOManager.readInt("PROMPT_AMOUNT", 1, 4);
    // If the player has enough houses to sell
    if (this.houseCount >= amount) {
      // Increase the player's money
      owner.increaseMoney(amount * this.housePrice);
      // Decrease the house count
      this.houseCount -= amount;
    }
    // If the player doesn't have enough houses to sell
    else {
      IOManager.printlnMsg("PROPERTY_CANT_SELL_HOUSES");
    }
  }

  /**
   * Buys a hotel for the property
   * 
   * @param player The player that owns the property
   */
  public void buyHotel() {
    // If the property already has a hotel
    if (this.hotel)
      IOManager.printlnMsg("PROPERTY_ALREADY_HAS_HOTEL");
    // If the property has less than 4 houses
    else if (this.houseCount < 4)
      IOManager.printlnMsg("PROPERTY_CANT_BUY_HOTEL");
    else {
      // If the player has enough money to buy a hotel
      if (owner.decreaseMoney(this.hotelPrice) != -1)
        this.hotel = true;
      // If the player doesn't have enough money to buy a hotel
      else
        IOManager.printlnMsg("PLAYER_CANT_AFFORD");
    }
  }

  public void sellHotel() {
    // If the property doesn't have a hotel
    if (!this.hotel)
      IOManager.printlnMsg("PROPERTY_CANT_SELL_HOTEL");
    // If the player has enough money to sell the hotel
    else if (owner.increaseMoney(this.hotelPrice) != -1)
      this.hotel = false;
    // If the player doesn't have enough money to sell the hotel
    else
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
  }

  @Override
  public void mortgageProperty() {
    // If the property is already mortgaged
    if (this.isMortgaged) {
      IOManager.printlnMsg("PROPERTY_ALREADY_MORTGAGED");
      return;
    }
    // If the property has houses or a hotel
    else if (this.houseCount > 0 || this.hotel) {
      IOManager.printlnMsg("PROPERTY_CANT_MORTGAGE");
      return;
    }

    // Mortgage the property
    owner.increaseMoney(this.mortgageValue);
    this.isMortgaged = true;
  }

  // --------------------------------------------- Getters & Setters ---------------------------------------------

  public int[] getWHousesRent() {
    return wHousesRent;
  }

  public void setWHousesRent(int[] wHousesRent) {
    this.wHousesRent = wHousesRent;
  }

  public int getWHotelRent() {
    return wHotelRent;
  }

  public void setWHotelRent(int wHotelRent) {
    this.wHotelRent = wHotelRent;
  }

  public int getHousePrice() {
    return housePrice;
  }

  public void setHousePrice(int housePrice) {
    this.housePrice = housePrice;
  }

  public int getHotelPrice() {
    return hotelPrice;
  }

  public void setHotelPrice(int hotelPrice) {
    this.hotelPrice = hotelPrice;
  }

  public int getHouseCount() {
    return houseCount;
  }

  public void setHouseCount(int houseCount) {
    this.houseCount = houseCount;
  }

  public boolean isHotel() {
    return hotel;
  }

  public void setHotel(boolean hotel) {
    this.hotel = hotel;
  }

}
