package cells;

public class MutableCell<T> implements Cell<T> {
  private T value;

  public MutableCell() {
    value = null;
  }

  public MutableCell(T value) {
    if (value == null) {
      throw new IllegalArgumentException("The value cannot be null");
    }
    this.value = value;
  }

  @Override
  public void set(T value) {
    if (value == null) {
      throw new IllegalArgumentException("The value cannot be null");
    }
    this.value = value;
  }

  @Override
  public T get() {
    return value;
  }

  @Override
  public boolean isSet() {
    return value != null;
  }
}
