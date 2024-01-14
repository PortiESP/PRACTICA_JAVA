package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import src.MonopolyGame.Player;
import src.MonopolyGame.IO.IOManager;
import src.MonopolyGame.MonopolyCodes.Property;
import src.MonopolyGame.MonopolyCodes.ServiceCard;
import src.MonopolyGame.MonopolyCodes.StationCard;
import src.MonopolyGame.MonopolyCodes.StreetCard;

public class Player_test {

  @BeforeAll
  public static void init() {
    // Set the language to English
    IOManager.setLanguage("English");
  }

  /*
   * Test the method `getStationCount()` 
   */
  @Test
  public void getStationCount() {
    Player player = new Player();
    ArrayList<Property> properties = player.getProperties();

    // Test case 1: The player has 0 stations
    assertEquals(0, player.getStationCount(), "Incorrect station count");

    // Test case 2: The player has 1 station
    properties.add(new StationCard());
    assertEquals(1, player.getStationCount(), "Incorrect station count");

    // Test case 3: The player has 4 stations
    properties.add(new StationCard());
    properties.add(new StationCard());
    properties.add(new StationCard());
    assertEquals(4, player.getStationCount(), "Incorrect station count");
  }

  /*
   * Test the method `getServicesCount()` 
   */
  @Test
  public void getServicesCount() {
    Player player = new Player();
    ArrayList<Property> properties = player.getProperties();

    // Test case 1: The player has 0 services
    assertEquals(0, player.getServicesCount(), "Incorrect services count");

    // Test case 2: The player has 1 service
    properties.add(new ServiceCard());
    assertEquals(1, player.getServicesCount(), "Incorrect services count");

    // Test case 3: The player has 4 services
    properties.add(new ServiceCard());
    properties.add(new ServiceCard());
    properties.add(new ServiceCard());
    assertEquals(4, player.getServicesCount(), "Incorrect services count");
  }

  /*
   * Test the method `getStreetsCount()` 
   */
  @Test
  public void getStreetsCount() {
    Player player = new Player();
    ArrayList<Property> properties = player.getProperties();

    // Test case 1: The player has 0 streets
    assertEquals(0, player.getStreetsCount(), "Incorrect streets count");

    // Test case 2: The player has 1 street
    properties.add(new StreetCard());
    assertEquals(1, player.getStreetsCount(), "Incorrect streets count");

    // Test case 3: The player has 4 streets
    properties.add(new StreetCard());
    properties.add(new StreetCard());
    properties.add(new StreetCard());
    assertEquals(4, player.getStreetsCount(), "Incorrect streets count");
  }

  /*
   * Test the method `decreaseMoney()`
   */
  @Test
  public void decreaseMoney() {
    Player player = new Player();
    player.setMoney(1000);

    // Test case 1: The player has enough money
    assertEquals(0, player.decreaseMoney(1000));

    // Test case 2: The player doesn't have enough money
    assertEquals(-1, player.decreaseMoney(2000));
  }

  /*
   * Test the method `liquidateAssets()`
   */
  @Test
  public void liquidateAssets() {
    // Initialize the buffer
    TestUtils.stdinBuffer(" \n \n");

    Player player = new Player();
    player.setMoney(1000);

    // Test case 1: The player has enough money
    assertTrue(player.liquidateAssets(1000));
  }

  /*
   * Test the method `pay()` with only one parameter (pay to the bank) (player has enough money)
   */
  @Test
  public void pay1() {

    Player player = new Player();
    player.setMoney(1000);

    // Test case 1: The player has enough money
    player.pay(500);
    assertEquals(500, player.getMoney(), "Incorrect money after paying");
  }

}
