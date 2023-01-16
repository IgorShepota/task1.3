package ua.foxminded.longdivision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import ua.foxminded.longdivision.provider.DivisionResultProvider;
import ua.foxminded.longdivision.provider.DivisionViewProvider;
import ua.foxminded.longdivision.provider.ResultProvider;
import ua.foxminded.longdivision.provider.ViewProvider;
import ua.foxminded.longdivision.validator.DivisionValidator;
import ua.foxminded.longdivision.validator.Validator;

class CalculatorITTest {
  
  Validator validator = new DivisionValidator();
  ResultProvider resultProvider = new DivisionResultProvider();
  ViewProvider viewProvider = new DivisionViewProvider();
  Calculator calculator = new Calculator(validator, resultProvider, viewProvider);

  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionEqualsZeroOrPositiveAndDivisorIsPositive() {
    assertEquals("_801000820|801\r\n"
        + " 801      |-------\r\n"
        + " ---      |1000001\r\n"
        + "      _820\r\n"
        + "       801\r\n"
        + "       ---\r\n"
        + "        19\r\n"
        + "", calculator.makeDivision(801000820, 801));
  }

  @Test
  void makeDivisionShouldThrowExceptionWhenDivisorEqualsZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      calculator.makeDivision(20, 0);
    });

    String expectedMessage = "It is forbidden to divide by zero.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void makeDivisionShouldThrowExceptionWhenDivisionLesserThanZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      calculator.makeDivision(-1, 80);
    });

    String expectedMessage =
        "The division can be zero or a positive number, the divisor can only be a positive number.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void makeDivisionShouldThrowExceptionWhenDivisorLesserThanZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      calculator.makeDivision(80, -1);
    });

    String expectedMessage =
        "The division can be zero or a positive number, the divisor can only be a positive number.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
  
  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionHasALargeNumberOfZerosInARow() {
    assertEquals("_100000|5\r\n"
        + " 10    |-----\r\n"
        + " --    |20000\r\n"
        + "      0\r\n"
        + "", calculator.makeDivision(100000, 5));
  }
  
  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionLesserThanDivisor() {
    assertEquals("_777|888\r\n"
        + "   0|-\r\n"
        + " ---|0\r\n"
        + " 777\r\n"
        + "", calculator.makeDivision(777, 888));
  }
}
