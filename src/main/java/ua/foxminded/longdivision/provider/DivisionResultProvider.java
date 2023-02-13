package ua.foxminded.longdivision.provider;

import java.util.ArrayList;
import java.util.List;
import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.domain.DivisionStep;

public class DivisionResultProvider implements ResultProvider {

  @Override
  public DivisionResult provideDivisionResult(int division, int divisor) {
    List<DivisionStep> steps = new ArrayList<>();
    String[] divisionDigits = String.valueOf(division).split("");
    int minuend = 0;
    int subtrahend;
    int remainder;

    for (int counter = 0; counter < divisionDigits.length; counter++) {
      minuend = minuend * 10 + Integer.parseInt(divisionDigits[counter]);

      if (minuend >= divisor) {
        remainder = minuend % divisor;
        subtrahend = minuend - remainder;

        steps.add(new DivisionStep(minuend, subtrahend, remainder, counter));

        minuend = remainder;
      }

      if (minuend != 0 && counter == divisionDigits.length - 1) {
        subtrahend = 0;
        remainder = 0;
        steps.add(new DivisionStep(minuend, subtrahend, remainder, counter));
      }

      if (minuend == 0 && counter == divisionDigits.length - 1) {
        subtrahend = 0;
        remainder = 0;
        steps.add(new DivisionStep(minuend, subtrahend, remainder, counter));
      }
    }
    return DivisionResult.newBuilder()
        .withDivision(division)
        .withDivisor(divisor)
        .withSteps(steps)
        .build();
  }
}
