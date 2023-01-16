package ua.foxminded.longdivision.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.domain.DivisionStep;

class DivisionViewProviderTest {
  
  DivisionViewProvider viewProvider = new DivisionViewProvider();

  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionGreaterThanDivisor() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(801000820)
                                          .withDivisor(801)
                                          .withSteps(Arrays.asList(new DivisionStep(801, 801, 0, 2)
                                                                 , new DivisionStep(820, 801, 19, 8)
                                                                 , new DivisionStep(19, 0, 0, 8)))
                                          .build();

    assertEquals("_801000820|801\r\n"
        + " 801      |-------\r\n"
        + " ---      |1000001\r\n"
        + "      _820\r\n"
        + "       801\r\n"
        + "       ---\r\n"
        + "        19\r\n"
        + "", viewProvider.provideDivisionView(result));
  }
  
  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionEqualsDivisor() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(801)
                                          .withDivisor(801)
                                          .withSteps(Arrays.asList(new DivisionStep(801, 801, 0, 2)
                                                                 , new DivisionStep(0, 0, 0, 2)))
                                          .build();
    
    assertEquals("_801|801\r\n"
        + " 801|-\r\n"
        + " ---|1\r\n"
        + "   0\r\n"
        + "", viewProvider.provideDivisionView(result));
  }
  
  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionHasALargeNumberOfZerosInARow() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(100000)
                                          .withDivisor(5)
                                          .withSteps(Arrays.asList(new DivisionStep(10, 10, 0, 1)
                                                                 , new DivisionStep(0, 0, 0, 5)))
                                          .build();
    
    assertEquals("_100000|5\r\n"
        + " 10    |-----\r\n"
        + " --    |20000\r\n"
        + "      0\r\n"
        + "", viewProvider.provideDivisionView(result));
  }
  
  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionLesserThanDivisor() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(777)
                                          .withDivisor(888)
                                          .withSteps(Arrays.asList(new DivisionStep(777, 0, 0, 2)))
                                          .build();
                                          
    assertEquals("_777|888\r\n"
        + "   0|-\r\n"
        + " ---|0\r\n"
        + " 777\r\n"
        + "", viewProvider.provideDivisionView(result));
  }
  
  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionIsZero() {
    DivisionResult result = DivisionResult.newBuilder()
        .withDivision(0)
        .withDivisor(4)
        .withSteps(Arrays.asList(new DivisionStep(0, 0, 0, 0)))
        .build();
    
    assertEquals("_0|4\r\n"
        + " 0|-\r\n"
        + " -|0\r\n"
        + " 0\r\n"
        + "", viewProvider.provideDivisionView(result));
  }
  
  @Test
  void provideDivisionViewShouldWorkCorrectlyWhenDivisionIsMaxValue() {
    int division = Integer.MAX_VALUE;
    DivisionResult result = DivisionResult.newBuilder()
        .withDivision(division)
        .withDivisor(100245)
        .withSteps(Arrays.asList(new DivisionStep(214748, 200490, 142583, 5),
                                 new DivisionStep(142583, 100245, 42338, 6),
                                 new DivisionStep(423386, 400980, 22406, 7),
                                 new DivisionStep(224064, 200490, 23574, 8),
                                 new DivisionStep(235747, 200490, 35257, 9),
                                 new DivisionStep(35257, 200490, 0, 9)))
        .build();
    
    assertEquals("_2147483647|100245\r\n"
        + " 200490    |-----\r\n"
        + " ------    |21422\r\n"
        + " _142583\r\n"
        + "  100245\r\n"
        + "  ------\r\n"
        + "  _423386\r\n"
        + "   400980\r\n"
        + "   ------\r\n"
        + "   _224064\r\n"
        + "    200490\r\n"
        + "    ------\r\n"
        + "    _235747\r\n"
        + "     200490\r\n"
        + "     ------\r\n"
        + "      35257\r\n"
        + "", viewProvider.provideDivisionView(result));
  }

  @Test
  void provideDivisionViewShouldThrowExceptionWhenDivisionResultIsIncorrect() {
    StringIndexOutOfBoundsException exception = assertThrows(StringIndexOutOfBoundsException.class, () -> {
      DivisionResult result = DivisionResult.newBuilder()
          .withDivision(12000)
          .withDivisor(2)
          .withSteps(Arrays.asList(new DivisionStep(12, 12, 0, 1)))
          .build();
      
      viewProvider.provideDivisionView(result);
    });

    String expectedMessage = "start > end";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
  
  @Test
  void provideDivisionViewShouldshouldGiveAnEmptyOutputWhenStepsIsEmpty() {
    DivisionResult result = DivisionResult.newBuilder()
        .withDivision(0)
        .withDivisor(4)
        .withSteps(Collections.emptyList())
        .build();
    
    assertEquals("", viewProvider.provideDivisionView(result));
  }
}
