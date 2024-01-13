package src.MonopolyGame.MonopolyCodes;

/**
 * The StationCard represents the stations in the game
 * 
 * <p>
 * The rent of the stations is calculated based on the number of stations the owner has (at least 1 since we are paying rent)
 * </p>
 */
public class StationCard extends Property {
  // Attributes
  private int fare[];

  // Constructors (for serialization)
  public StationCard() {
  }

  // Constructor
  public StationCard(String description, String fare1, String fare2, String fare3, String fare4, String mortgageValue) {
    this.description = description;
    this.mortgageValue = Integer.parseInt(mortgageValue);
    this.propertyPrice = Integer.parseInt(mortgageValue) * 2;
    this.fare = new int[] { Integer.parseInt(fare1), Integer.parseInt(fare2), Integer.parseInt(fare3),
        Integer.parseInt(fare4) };
  }

  /**
   * This method will calculate the amount of money the player has to pay to the owner of the property
   * 
   * <p>
   * The rent of the stations is calculated based on the number of stations the owner has (at least 1 since we are paying rent)
   * </p>
   * 
   */
  @Override
  public int calculateAmountToPay() {

    // If the property is mortgaged, no fare is paid
    if (isMortgaged)
      return 0;

    // Pay the fare based on the number of stations the owner has (at least 1 since we are paying fare)
    return fare[owner.getStationCount() - 1];
  }

  // ---------------------------------------- Getters and Setters ----------------------------------------
  public int[] getFare() {
    return fare;
  }

  public void setFare(int[] fare) {
    this.fare = fare;
  }

}
