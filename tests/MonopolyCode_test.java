package tests;

import org.junit.jupiter.api.Test;

import src.MonopolyGame.MonopolyCodes.MonopolyCode;
import src.MonopolyGame.MonopolyCodes.PaymentCard;

import static org.junit.jupiter.api.Assertions.*;

public class MonopolyCode_test {

  @Test
  public void testParseIntFromDescription() {
    MonopolyCode code = new PaymentCard();

    // Test case 1: Number is found in the description (positive number)
    code.setDescription("This is a description with 10€");
    int result3 = code.parsePriceFromDescription();
    assertEquals(10, result3, "Incorrect parsing of positive number");

    // Test case 2: Number is found in the description (negative number)
    code.setDescription("This is a description with -10€");
    int result1 = code.parsePriceFromDescription();
    assertEquals(-10, result1, "Incorrect parsing of negative number");

    // Test case 3: Number is not found in the description
    code.setDescription("This is a description without a number");
    int result2 = code.parsePriceFromDescription();
    assertEquals(0, result2, "Incorrect parsing of number when not found");

    // Test case 4: Number is found in the description (without currency symbol)
    code.setDescription("This is a description with 10");
    int result4 = code.parsePriceFromDescription();
    assertEquals(0, result4, "Incorrect parsing of number without currency symbol");

    // Test case 5: Multiple numbers are found in the description (only the first one is parsed)
    code.setDescription("This is a description with 10€ and -20€");
    int result5 = code.parsePriceFromDescription();
    assertEquals(10, result5, "Incorrect parsing of number when multiple numbers are found");
  }
}