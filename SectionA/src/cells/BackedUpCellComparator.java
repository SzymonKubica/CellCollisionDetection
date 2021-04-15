package cells;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BackedUpCellComparator<U> implements Comparator<BackedUpCell<U>> {
  private Comparator<U> valueComparator;

  public BackedUpCellComparator(Comparator<U> comparator) {
    valueComparator = comparator;
  }

  @Override
  public int compare(BackedUpCell<U> cell1, BackedUpCell<U> cell2) {
    if (compareBeingSet(cell1, cell2).isPresent()) {
      return compareBeingSet(cell1, cell2).get();
    }
    // Both cells are set, we need to compare values.
    if (cell1.get().equals(cell2.get())) {
      // If values of the cells are equal, we need to compare backed up values.
      if (cell1.hasBackup() && cell2.hasBackup()) {
        return compareBackedUpValues(cell1, cell2);
      } else if (cell1.hasBackup()) {
        return 1;
      } else if (cell2.hasBackup()) {
        return -1;
      } else {
        // Cells have no backup and their values are equal implies overall equality.
        return 0;
      }
    } else {
      return valueComparator.compare(cell1.get(), cell2.get());
    }
  }

  private int compareBackedUpValues(BackedUpCell<U> cell1, BackedUpCell<U> cell2) {
    // The current values need to be saved and we compare reverted cells.
    U cell1CurrentValue = cell1.get();
    U cell2CurrentValue = cell2.get();
    cell1.revertToPrevious();
    cell2.revertToPrevious();
    int result = compare(cell1, cell2);
    // After the comparison the state of cells needs to be restored.
    cell1.set(cell1CurrentValue);
    cell2.set(cell2CurrentValue);
    return result;
  }

  private Optional<Integer> compareBeingSet(BackedUpCell<U> cell1, BackedUpCell<U> cell2) {
    if (!cell1.isSet() && !cell2.isSet()) {
      return Optional.of(0);
    } else if (cell1.isSet() && !cell2.isSet()) {
      return Optional.of(1);
    } else if (cell2.isSet() && !cell1.isSet()) {
      return Optional.of(-1);
    } else {
      return Optional.empty();
    }
  }
}


