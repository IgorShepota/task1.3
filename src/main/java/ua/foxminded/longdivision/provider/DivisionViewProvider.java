package ua.foxminded.longdivision.provider;

import java.util.List;
import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.domain.DivisionStep;

public class DivisionViewProvider implements ViewProvider {
  
  @Override
  public String provideDivisionView(DivisionResult divisionResult) {
    StringBuilder result = new StringBuilder();
    List<DivisionStep> steps = divisionResult.getSteps();
    int division = divisionResult.getDivision();
    int divisor = divisionResult.getDivisor();
    int quotient = divisionResult.getQuotient();

    for (int i = 0; i < steps.size(); i++) {
      int minuend = steps.get(i).getMinuend();
      int subtrahend = steps.get(i).getSubtrahend();
      int counter = steps.get(i).getCounter();
      
      if (division < divisor) {
        result = convertStepsToView(minuend, subtrahend, counter, result);
        convertResultToView(division, divisor, quotient, result);
        result.append(String.format("%" + (counter + 2) + "d", minuend)).append("\n");
        break;
      }

      if (i == steps.size() - 1) {
        convertResultToView(division, divisor, quotient, result);
        result.append(String.format("%" + (counter + 2) + "d", minuend)).append("\n");
        break;
      }
      
      result = convertStepsToView(minuend, subtrahend, counter, result);
    }
    return result.toString().replaceAll("\n", System.lineSeparator());
  }
  
  private StringBuilder convertStepsToView(int minuend, int subtrahend, int counter, StringBuilder result) {
    String minuendLine = String.format("%" + (counter + 2) + "s", "_" + minuend);
    result.append(minuendLine).append("\n");

    String subtrahendLine = String.format("%" + (counter + 2) + "d", subtrahend);
    result.append(subtrahendLine).append("\n");

    String dashesLine = String.format("%" + (counter + 2) + "s", dashes(minuend));
    result.append(dashesLine).append("\n");
    
    return result;
  }

  private void convertResultToView(Integer division, Integer divisor, Integer quotient, StringBuilder result) {
    int[] indexes = findTheFirstThreeLineBreaks(result);
    int count = calculateLength(division) + 1 - indexes[0];

    result.insert(indexes[2], repeatSign(count, " ") + "|" + quotient.toString());
    result.insert(indexes[1], repeatSign(count, " ") + "|" + repeatSign(calculateLength(quotient), "-"));
    result.insert(indexes[0], "|" + divisor);
    result.replace(1, indexes[0], division.toString());
  }

  private int[] findTheFirstThreeLineBreaks(StringBuilder result) {
    final int NUMBER_OF_FIRST_THREE_LINE_BREAKS = 3; 
    int[] indexes = new int[3];

    for (int i = 0, j = 0; i < result.length(); i++) {
      if (result.charAt(i) == '\n') {
        indexes[j] = i;
        j++;
      }
      if (j == NUMBER_OF_FIRST_THREE_LINE_BREAKS) {
        break;
      }
    }
    return indexes;
  }

  private int calculateLength(Integer i) {
    return Integer.toString(i).length();
  }

  private String dashes(int minuend) {
    return repeatSign(calculateLength(minuend), "-");
  }

  private String repeatSign(int count, String symbol) {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < count; i++) {
      string.append(symbol);
    }
    return string.toString();
  }
}
