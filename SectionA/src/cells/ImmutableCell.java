package cells;

public class ImmutableCell<T> implements Cell<T>{
  private final T value;

  public ImmutableCell(T value) {
    if (value == null) {
      throw new IllegalArgumentException("The value cannot be null");
    }
    this.value = value;
  }
  @Override
  public void set(T value) {
    throw new UnsupportedOperationException("The cell is immutable. Its value cannot be changed.");
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public boolean isSet() {
    return true;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof  ImmutableCell) {
      return this.value.equals(((ImmutableCell<T>) other).value);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
