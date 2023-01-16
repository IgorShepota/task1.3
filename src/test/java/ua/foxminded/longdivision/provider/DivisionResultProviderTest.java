package ua.foxminded.longdivision.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.domain.DivisionStep;

class DivisionResultProviderTest {

  DivisionResultProvider resultProvider = new DivisionResultProvider();
  
  @Test
  void provideDivisionResultShouldWorkCorrectlyWhenDivisionIsGreaterThenDivisor() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(801000820)
                                          .withDivisor(801)
                                          .withSteps(Arrays.asList(new DivisionStep(801, 801, 0, 2)
                                                                 , new DivisionStep(820, 801, 19, 8)
                                                                 , new DivisionStep(19, 0, 0, 8)))
                                          .build();
    
    assertEquals(result, resultProvider.provideDivisionResult(801000820, 801));
  }
  
  @Test
  void provideDivisionResultShouldWorkCorrectlyWhenDivisionHasALargeNumberOfZerosInARow() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(100000)
                                          .withDivisor(5)
                                          .withSteps(Arrays.asList(new DivisionStep(10, 10, 0, 1)
                                                                 , new DivisionStep(0, 0, 0, 5)))
                                          .build();
    
    assertEquals(result, resultProvider.provideDivisionResult(100000, 5));
  }
  
  @Test
  void provideDivisionResultShouldWorkCorrectlyWhenDivisionLesserThanDivisor() {
    DivisionResult result = DivisionResult.newBuilder()
                                          .withDivision(777)
                                          .withDivisor(888)
                                          .withSteps(Arrays.asList(new DivisionStep(777, 0, 0, 2)))
                                          .build();
    
    assertEquals(result, resultProvider.provideDivisionResult(777, 888));
  }

  @Test
  void provideDivisionResultShouldWorkCorrectlyWhenDivisionEqualsDivisor() {
   DivisionResult result = DivisionResult.newBuilder()
                                         .withDivision(777)
                                         .withDivisor(777)
                                         .withSteps(Arrays.asList(new DivisionStep(777, 777, 0, 2),
                                                                  new DivisionStep(0, 0, 0, 2)))
                                         .build();
    
   assertEquals(result, resultProvider.provideDivisionResult(777, 777));
  }
}
