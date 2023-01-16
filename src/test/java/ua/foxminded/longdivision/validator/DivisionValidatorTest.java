package ua.foxminded.longdivision.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DivisionValidatorTest {
  
  DivisionValidator validator = new DivisionValidator();
  
  @Test
  void validateShouldNotThrowExceptionWhenDivisionEqualsZeroOrPositiveAndDivisorIsPositive() {
    assertDoesNotThrow(() -> validator.validate(801000820, 801));
  }

  @Test
  void validateShouldThrowExceptionWhenDivisorEqualsZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      validator.validate(800, 0);
    });

    String expectedMessage = "It is forbidden to divide by zero.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void validateShouldThrowExceptionWhenDivisionLesserThanZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      validator.validate(-80, 1);
    });

    String expectedMessage =
        "The division can be zero or a positive number, the divisor can only be a positive number.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void validateShouldThrowExceptionWhenDivisorLesserThanZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      validator.validate(80, -1);
    });

    String expectedMessage =
        "The division can be zero or a positive number, the divisor can only be a positive number.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
