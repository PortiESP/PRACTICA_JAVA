package src.MonopolyGame.MonopolyCodes;

public class StationCard extends Property {
  // Attributes
  private int fare[];

  // Constructors (for serialization)
  public StationCard() {
  }

  // Constructor
  public StationCard(String description, String fare1, String fare2, String fare3, String fare4) {
    this.description = description;
    this.fare = new int[] { Integer.parseInt(fare1), Integer.parseInt(fare2), Integer.parseInt(fare3),
        Integer.parseInt(fare4) };
  }

  @Override
  public int calculateAmountToPay() {

    // If the property is mortgaged, no fare is paid
    if (isMortgaged)
      return 0;

    // Pay the fare based on the number of stations the owner has (at least 1 since we are paying fare)
    return fare[owner.getStationCount() - 1];
  }

  public int[] getFare() {
    return fare;
  }

  public void setFare(int[] fare) {
    this.fare = fare;
  }

}
