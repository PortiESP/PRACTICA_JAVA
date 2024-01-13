package src.MonopolyGame.MonopolyCodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.IO.MenuBuilder;

/**
 * The RepairsCard will make the player pay for the "repairs" of his properties (houses and hotels)
 * 
 * <p>
 * The description of the card must be contain two prices, one for the houses and one for the hotels. The first price that appears in the description will be the price per house, and the second one will be the price per hotel.
 * </p>
 */
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
    parseDescription(); // Store the price per house and hotel
  }

  /**
   * This method will make the player pay for the repairs of his properties (houses and hotels)
   * 
   * <p>
   * The player will pay the price per house multiplied by the number of houses he has, and the price per hotel multiplied by the number of hotels he has.
   * </p>
   * 
   * <h4>Random values for fun</h4>
   * <p>
   * The player will also pay a bribe (random value between 0 and 100) and a tax (21% of the total price)
   * </p>
   * 
   * <hr/>
   */
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
        String.format(IOManager.getMsg("REPAIRS_HOUSES"), houses, (houses * this.pricePerHouse)),
        String.format(IOManager.getMsg("REPAIRS_HOTELS"), hotels, (hotels * this.pricePerHotel)),
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

  /**
   * This method will parse the description of the card to get the price per house and hotel
   * 
   * <p>
   * The description of the card must be contain two prices, one for the houses and one for the hotels. The first price that appears in the description will be the price per house, and the second one will be the price per hotel.
   * </p>
   * 
   * <hr/>
   */
  public void parseDescription() {
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
