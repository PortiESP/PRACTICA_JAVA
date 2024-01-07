package src.MonopolyGame.MonopolyCodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.MenuBuilder;

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
    // Print the card description
    MenuBuilder.alert("REPAIRS_CARD_TITLE", this.description);

    int houses = player.getTotalHouses();
    int hotels = player.getTotalHotels();
    int total = houses * this.pricePerHouse + hotels * this.pricePerHotel;
    // Cargos (por las risas)
    int bribe = (int) (Math.random() * 100);
    float tax = total * 0.21F;
    float totalWithTax = total + tax;

    String[] lines = {
        IOManager.getMsg("REPAIRS_BILL_HEAD"),
        "",
        String.format(IOManager.getMsg("REPAIRS_HOUSES"), houses, this.pricePerHouse),
        String.format(IOManager.getMsg("REPAIRS_HOTELS"), hotels, this.pricePerHotel),
        "",
        String.format(IOManager.getMsg("REPAIRS_BRIBE"), bribe),
        "",
        String.format(IOManager.getMsg("REPAIRS_TAX"), tax),
        String.format(IOManager.getMsg("REPAIRS_TOTAL"), totalWithTax + bribe)
    };

    // Print the operation summary
    MenuBuilder.doc("REPAIRS_BILL", lines);
    IOManager.pause();

    player.pay((int) totalWithTax + bribe);
  }

  public void parsePricePerHouse() {
    String pricePerHousePattern = ".+ (\\d+)€.+ (\\d+)€";
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
