package ua.foxminded.longdivision.domain;

import java.util.List;
import java.util.Objects;

public class DivisionResult {
  private final int division;
  private final int divisor;
  private final int quotient;
  private final List<DivisionStep> steps;

  public DivisionResult(Builder builder) {
    this.division = builder.division;
    this.divisor = builder.divisor;
    this.quotient = division / divisor;
    this.steps = builder.steps;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public int getDivision() {
    return division;
  }

  public int getDivisor() {
    return divisor;
  }

  public int getQuotient() {
    return quotient;
  }

  public List<DivisionStep> getSteps() {
    return steps;
  }

  @Override
  public int hashCode() {
    return Objects.hash(division, divisor, quotient, steps);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DivisionResult other = (DivisionResult) obj;
    return division == other.division &&
           divisor == other.divisor &&
           quotient == other.quotient&&
           Objects.equals(steps, other.steps);
  }

  @Override
  public String toString() {
    return "DivisionResult [division=" + division + ", divisor=" + divisor + ", quotient=" + quotient + ", steps="
        + steps + "]";
  }

  public static class Builder {
    private int division;
    private int divisor;
    private List<DivisionStep> steps;

    private Builder() {}

    public Builder withDivision(int division) {
      this.division = division;
      return this;
    }

    public Builder withDivisor(int divisor) {
      this.divisor = divisor;
      return this;
    }

    public Builder withSteps(List<DivisionStep> steps) {
      this.steps = steps;
      return this;
    }

    public DivisionResult build() {
      return new DivisionResult(this);
    }
  }
}
