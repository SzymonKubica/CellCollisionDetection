package cells;

import java.util.LinkedList;
import java.util.List;

public class BackedUpMutableCell<T> extends MutableCell<T> implements BackedUpCell<T> {
  private LinkedList<T> previousValues;
  private final Mode mode;
  private int limit;

  public BackedUpMutableCell() {
    super();
    previousValues = new LinkedList<>();
    mode = Mode.UNBOUNDED_BACKUP;
  }

  public BackedUpMutableCell(int limit) {
    super();
    this.limit = limit;
    previousValues = new LinkedList<>();
    mode = Mode.BOUNDED_BACKUP;
  }

  @Override
  public void set(T value) {
    if (isSet()) {
      backUpValue();
    }
    super.set(value);
  }

  private void backUpValue() {
    previousValues.addLast(get());
    if (mode == Mode.BOUNDED_BACKUP && previousValues.size() > limit) {
      // The oldest value will be stored at the index 0, so if the limit is exceeded we remove it.
      previousValues.removeFirst();
    }
  }

  @Override
  public boolean hasBackup() {
    return !previousValues.isEmpty();
  }

  @Override
  public void revertToPrevious() {
    if (!hasBackup()) {
      throw new UnsupportedOperationException("There is no backup");
    }
    super.set(previousValues.getLast());
    previousValues.removeLast();
  }

  private enum Mode {
    UNBOUNDED_BACKUP, BOUNDED_BACKUP
  }
}
