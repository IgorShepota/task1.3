package ua.foxminded.longdivision.provider;

import ua.foxminded.longdivision.domain.DivisionResult;

public interface ResultProvider {

  public DivisionResult provideDivisionResult(int division, int divisor);
}
