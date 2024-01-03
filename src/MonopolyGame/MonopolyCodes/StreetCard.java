package src.MonopolyGame.MonopolyCodes;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;

public class StreetCard extends Property {
  // Attributes
  private int[] wHousesRent;
  private int wHotelRent;
  private int housePrice;
  private int hotelPrice;
  private int houseCount;
  private boolean hasHotel;

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
    this.hasHotel = false;
  }

  @Override
  public int calculateAmountToPay() {

    // If the property is mortgaged, no rent is paid
    if (isMortgaged)
      return 0;

    // Pay the house rent
    if (houseCount > 0)
      return wHousesRent[houseCount - 1];
    // Pay the hotel rent
    else if (hasHotel)
      return wHotelRent;

    // Pay the minimum rent
    return rent;

  }

  public int propertyManagementMenu(Player player) {
    // Ask the player what he wants to do with the property
    IOManager.print("\n");
    IOManager.printMsg("PROPERTY_MANAGEMENT_MENU");
    IOManager.print(String.format(" [%s]", this.description));
    IOManager.print("\n");
    IOManager.print("\t- [1] Buy houses");
    IOManager.print("\t- [2] Sell houses");
    IOManager.print("\t- [3] Buy hotel");
    IOManager.print("\t- [4] Sell hotel");
    IOManager.print("\t- [5] Mortgage property");
    IOManager.print("\t- [6] Pay off property mortgage");
    IOManager.print("\t- [7] Sell property");
    IOManager.print("\t- [0] Exit");
    IOManager.print("\n");

    // Get the player's choice
    int opt = IOManager.readInt("PROMPT_OPTION", 0, 7);

    // Do the chosen operation
    if (opt == 1) {
      buyHouses(player);
      return 1;
    } else if (opt == 2) {
      sellHouses(player);
      return 2;
    } else if (opt == 3) {
      buyHotel(player);
      return 3;
    } else if (opt == 4) {
      sellHotel(player);
      return 4;
    } else if (opt == 5) {
      mortgageProperty(player);
      return 5;
    } else if (opt == 6) {
      payOffMortgage(player);
      return 6;
    } else if (opt == 7) {
      sellProperty(player);
      return 7;
    } else if (opt == 0) {
      return 0;
    }

    return -1; // Should never happen
  }

  public void buyHouses(Player player) {
    int amount = IOManager.readInt("PROMPT_AMOUNT", 1, 4);
    if (player.decreaseMoney(amount * this.housePrice) != -1) {
      this.houseCount += amount;
    } else {
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
    }
  }

  public void sellHouses(Player player) {
    int amount = IOManager.readInt("PROMPT_AMOUNT", 1, 4);
    // If the player has enough houses to sell
    if (this.houseCount >= amount) {
      // Increase the player's money
      player.increaseMoney(amount * this.housePrice);
      // Decrease the house count
      this.houseCount -= amount;
    }
    // If the player doesn't have enough houses to sell
    else {
      IOManager.printlnMsg("PROPERTY_CANT_SELL_HOUSES");
    }
  }

  public void buyHotel(Player player) {
    // If the property already has a hotel
    if (this.hasHotel)
      IOManager.printlnMsg("PROPERTY_ALREADY_HAS_HOTEL");
    // If the player has enough money to buy a hotel
    else if (player.decreaseMoney(this.hotelPrice) != -1)
      this.hasHotel = true;
    // If the player doesn't have enough money to buy a hotel
    else
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
  }

  public void sellHotel(Player player) {
    // If the property doesn't have a hotel
    if (!this.hasHotel)
      IOManager.printlnMsg("PROPERTY_CANT_SELL_HOTEL");
    // If the player has enough money to sell the hotel
    else if (player.increaseMoney(this.hotelPrice) != -1)
      this.hasHotel = false;
    // If the player doesn't have enough money to sell the hotel
    else
      IOManager.printlnMsg("PLAYER_CANT_AFFORD");
  }

  @Override
  public void mortgageProperty(Player player) {
    // If the property is already mortgaged
    if (this.isMortgaged) {
      IOManager.printlnMsg("PROPERTY_ALREADY_MORTGAGED");
      return;
    }
    // If the property has houses or a hotel
    else if (this.houseCount > 0 || this.hasHotel) {
      IOManager.printlnMsg("PROPERTY_CANT_MORTGAGE");
      return;
    }

    // Mortgage the property
    player.increaseMoney(this.mortgageValue);
    this.isMortgaged = true;
  }

}
