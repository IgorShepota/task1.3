package ua.foxminded.longdivision.validator;

public class DivisionValidator implements Validator {

  @Override
  public void validate(int division, int divisor) {

    if (divisor == 0) {
      throw new IllegalArgumentException("It is forbidden to divide by zero.");
    }

    if (division < 0 || divisor < 0) {
      throw new IllegalArgumentException(
          "The division can be zero or a positive number, the divisor can only be a positive number.");
    }
  }
}
