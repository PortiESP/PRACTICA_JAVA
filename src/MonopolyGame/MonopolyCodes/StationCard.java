package src.MonopolyGame.MonopolyCodes;

public class StationCard extends Property {
  // Attributes
  private int rent[];

  // Constructors (for serialization)
  public StationCard() {
  }

  // Constructor
  public StationCard(String description, String rent1, String rent2, String rent3, String rent4) {
    this.description = description;
    this.rent = new int[] { Integer.parseInt(rent1), Integer.parseInt(rent2), Integer.parseInt(rent3),
        Integer.parseInt(rent4) };
  }

  @Override
  public int calculateAmountToPay() {

    // If the property is mortgaged, no rent is paid
    if (isMortgaged)
      return 0;

    // Pay the rent based on the number of stations the owner has (at least 1 since we are paying rent)
    return rent[owner.getStationCount() - 1];
  }
}
