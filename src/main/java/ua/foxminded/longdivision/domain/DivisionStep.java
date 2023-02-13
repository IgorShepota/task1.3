package ua.foxminded.longdivision.domain;

import java.util.Objects;

public class DivisionStep {

  private final int minuend;
  private final int subtrahend;
  private final int remainder;
  private final int counter;

  public DivisionStep(int minuend, int subtrahend, int remainder, int counter) {
    this.minuend = minuend;
    this.subtrahend = subtrahend;
    this.remainder = remainder;
    this.counter = counter;
  }

  public int getMinuend() {
    return minuend;
  }

  public int getSubtrahend() {
    return subtrahend;
  }

  public int getRemainder() {
    return remainder;
  }

  public int getCounter() {
    return counter;
  }

  @Override
  public int hashCode() {
    return Objects.hash(counter, minuend, remainder, subtrahend);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    DivisionStep other = (DivisionStep) obj;
    return counter == other.counter
        && minuend == other.minuend
        && remainder == other.remainder
        && subtrahend == other.subtrahend;
  }

  @Override
  public String toString() {
    return "DivisionStep [minuend=" + minuend + ", subtrahend=" + subtrahend + ", remainder=" + remainder + ", counter="
        + counter + "]";
  }
}
