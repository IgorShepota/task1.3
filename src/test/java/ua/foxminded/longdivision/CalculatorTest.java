package ua.foxminded.longdivision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.domain.DivisionStep;
import ua.foxminded.longdivision.provider.DivisionResultProvider;
import ua.foxminded.longdivision.provider.DivisionViewProvider;
import ua.foxminded.longdivision.validator.DivisionValidator;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {
  
  @Mock
  private DivisionValidator validator;
  
  @Mock
  private DivisionResultProvider resultProvider;
  
  @Mock
  private DivisionViewProvider viewProvider;

  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionEqualsZeroOrPositiveAndDivisorIsPositive() {
    Mockito.doNothing().when(validator).validate(801000820, 801);
    
    when(resultProvider.provideDivisionResult(801000820, 801))
                       .thenReturn(DivisionResult.newBuilder()
                                                 .withDivision(801000820)
                                                 .withDivisor(801)
                                                 .withSteps(Arrays.asList(new DivisionStep(801, 801, 0, 2)
                                                                        , new DivisionStep(820, 801, 19, 8)
                                                                        , new DivisionStep(19, 0, 0, 8)))
                                                 .build());
    
    when(viewProvider.provideDivisionView(DivisionResult.newBuilder()
                                                        .withDivision(801000820)
                                                        .withDivisor(801)
                                                        .withSteps(Arrays.asList(new DivisionStep(801, 801, 0, 2)
                                                                               , new DivisionStep(820, 801, 19, 8)
                                                                               , new DivisionStep(19, 0, 0, 8)))
                                                        .build()))
                     .thenReturn("_801000820|801\r\n"
                         + " 801      |-------\r\n"
                         + " ---      |1000001\r\n"
                         + "      _820\r\n"
                         + "       801\r\n"
                         + "       ---\r\n"
                         + "        19\r\n"
                         + "");
    
    Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
    
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
      
      doThrow(new IllegalArgumentException("It is forbidden to divide by zero.")).when(validator).validate(20, 0);
      
      Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
      
      calculator.makeDivision(20, 0);
    });

    String expectedMessage = "It is forbidden to divide by zero.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
  
  @Test
  void makeDivisionShouldThrowExceptionWhenDivisionLesserThanZero() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      
      doThrow(new IllegalArgumentException(
          "The division can be zero or a positive number, the divisor can only be a positive number."))
          .when(validator).validate(-1, 80);
      
      Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
      
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
      
      doThrow(new IllegalArgumentException(
          "The division can be zero or a positive number, the divisor can only be a positive number."))
          .when(validator).validate(80, -1);
      
      Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
      
      calculator.makeDivision(80, -1);
    });

    String expectedMessage =
        "The division can be zero or a positive number, the divisor can only be a positive number.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
  
  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionHasALargeNumberOfZerosInARow() {
    Mockito.doNothing().when(validator).validate(100000, 5);
    
    when(resultProvider.provideDivisionResult(100000, 5))
                       .thenReturn(DivisionResult.newBuilder()
                                                 .withDivision(100000)
                                                 .withDivisor(5)
                                                 .withSteps(Arrays.asList(new DivisionStep(10, 10, 0, 1)
                                                                        , new DivisionStep(0, 0, 0, 5)))
                                                 .build());
    
    when(viewProvider.provideDivisionView(DivisionResult.newBuilder()
                                                        .withDivision(100000)
                                                        .withDivisor(5)
                                                        .withSteps(Arrays.asList(new DivisionStep(10, 10, 0, 1)
                                                                               , new DivisionStep(0, 0, 0, 5)))
                                                        .build()))
                     .thenReturn("_100000|5\r\n"
                         + " 10    |-----\r\n"
                         + " --    |20000\r\n"
                         + "      0\r\n"
                         + "");
    
    Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
    
    assertEquals("_100000|5\r\n"
        + " 10    |-----\r\n"
        + " --    |20000\r\n"
        + "      0\r\n"
        + "", calculator.makeDivision(100000, 5));
  }
  
  @Test
  void makeDivisionShouldWorkCorrectlyWhenDivisionLesserThanDivisor() {
    Mockito.doNothing().when(validator).validate(777, 888);
    
    when(resultProvider.provideDivisionResult(777, 888))
                       .thenReturn(DivisionResult.newBuilder()
                                                 .withDivision(777)
                                                 .withDivisor(888)
                                                 .withSteps(Arrays.asList(new DivisionStep(777, 0, 0, 2)))
                                                 .build());
                                            
    when(viewProvider.provideDivisionView(DivisionResult.newBuilder()
                                                        .withDivision(777)
                                                        .withDivisor(888)
                                                        .withSteps(Arrays.asList(new DivisionStep(777, 0, 0, 2)))
                                                        .build()))
                     .thenReturn("_777|888\r\n"
                         + "   0|-\r\n"
                         + " ---|0\r\n"
                         + " 777\r\n"
                         + "");
    
    Calculator calculator = new Calculator(validator, resultProvider, viewProvider);
    
    assertEquals("_777|888\r\n"
        + "   0|-\r\n"
        + " ---|0\r\n"
        + " 777\r\n"
        + "", calculator.makeDivision(777, 888));
  }
}
