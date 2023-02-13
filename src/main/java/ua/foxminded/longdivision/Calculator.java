package ua.foxminded.longdivision;

import ua.foxminded.longdivision.domain.DivisionResult;
import ua.foxminded.longdivision.provider.ResultProvider;
import ua.foxminded.longdivision.provider.ViewProvider;
import ua.foxminded.longdivision.validator.Validator;

public class Calculator {
  private final Validator validator;
  private final ResultProvider resultProvider;
  private final ViewProvider viewProvider;

  public Calculator(Validator validator, ResultProvider resultProvider, ViewProvider viewProvider) {
    this.validator = validator;
    this.resultProvider = resultProvider;
    this.viewProvider = viewProvider;
  }

  public String makeDivision(int division, int divisor) {
    validator.validate(division, divisor);

    DivisionResult result = resultProvider.provideDivisionResult(division, divisor);

    return viewProvider.provideDivisionView(result);
  }
}
