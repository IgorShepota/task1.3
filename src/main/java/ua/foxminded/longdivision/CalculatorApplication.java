package ua.foxminded.longdivision;

import ua.foxminded.longdivision.provider.DivisionResultProvider;
import ua.foxminded.longdivision.provider.DivisionViewProvider;
import ua.foxminded.longdivision.provider.ResultProvider;
import ua.foxminded.longdivision.provider.ViewProvider;
import ua.foxminded.longdivision.validator.DivisionValidator;
import ua.foxminded.longdivision.validator.Validator;

public class CalculatorApplication {
  public static void main(String[] args) {
    Validator validator = new DivisionValidator();
    ResultProvider divisionStepProvider = new DivisionResultProvider();
    ViewProvider divisionViewProvider = new DivisionViewProvider();

    Calculator calculator = new Calculator(validator, divisionStepProvider, divisionViewProvider);

    final String output = calculator.makeDivision(12000, 2);

    System.out.println(output);
  }
}
