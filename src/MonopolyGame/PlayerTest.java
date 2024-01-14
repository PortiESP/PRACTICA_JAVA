import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testSummary() {
        Player player = new Player("John Doe", 1000);
        player.addProperty(new StationCard("Station 1"));
        player.addProperty(new StreetCard("Street 1"));
        player.addProperty(new ServiceCard("Service 1"));

        String[] expectedSummary = {
                "Name: John Doe",
                "Money: $1000",
                "Properties: (3)",
                "    - Stations (1)",
                "        - Station 1",
                "    - Streets (1)",
                "        - Street 1",
                "    - Services (1)",
                "        - Service 1"
        };

        Assertions.assertArrayEquals(expectedSummary, player.summary());
    }
}