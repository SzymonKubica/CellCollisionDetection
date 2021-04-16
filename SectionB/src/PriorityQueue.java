import java.util.Iterator;

/**
 * You must implement the <code>remove</code> and <code>PQRebuild</code> methods.
 */

public class PriorityQueue<T extends Comparable<T>> implements
        PriorityQueueInterface<T> {

  private T[] items;             //a minHeap of elements T
  private final static int max_size = 512;
  private int size;              // number of elements in the minHeap.


  public PriorityQueue() {
    items = (T[]) new Comparable[max_size];
    size = 0;
  }

  /**
   * Returns true if the priority queue is empty. False otherwise.
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the size of the priority queue.
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns the element with highest priority, or returns null if the priority.
   * queue is empty. The priority queue is left unchanged
   */
  public T peek() {
    T root = null;
    if (!isEmpty()) {
      root = items[0];
    }
    return root;
  }

  /**
   * Adds a new entry to the priority queue according to the priority value.
   *
   * @param newEntry the new element to add to the priority queue
   * @throws PQException exception if the priority queue is full
   */
  public void add(T newEntry) throws PQException {
    if (size < max_size) {
      items[size] = newEntry;
      int place = size;
      int parent = (place - 1) / 2;
      while ((parent >= 0) && (items[place].compareTo(items[parent]) < 0)) {
        swap(place, parent);
        parent = (place - 1) / 2;
      }
      size++;
    } else {
      throw new PQException("The priorityQueue is full");
    }
  }

  /**
   * <p> Implement this method for Question 1 </p>
   * <p>
   * Removes the element with highest priority.
   */
  public void remove() {
    swap(0, size - 1);
    items[size - 1] = null;
    size--;
    PQRebuild(0);
  }

  /**
   * <p> Implement this method for Question 1 </p>
   */
  private void PQRebuild(int root) {
    int left = 2 * (root + 1) - 1;
    int right = 2 * (root + 1);
    if (left < size) {
      // there is a left sub heap.
      int smallerSubHeap;
      if (left == size - 1) {
        smallerSubHeap = left;
      } else if (items[left].compareTo(items[right]) < 0) {
        smallerSubHeap = left;
      } else {
        // Favours right subHeap if the keys are equal.
        smallerSubHeap = right;
      }

      if (items[root].compareTo(items[smallerSubHeap]) > 0) {
        swap(root, smallerSubHeap);
        PQRebuild(smallerSubHeap);
      }
    }
  }

  private void swap(int index1, int index2) {
    T temp = items[index1];
    items[index1] = items[index2];
    items[index2] = temp;
  }


  public Iterator<Object> iterator() {
    return new PQIterator<>();
  }

  private class PQIterator<T> implements Iterator<Object> {

    private int position = 0;

    public boolean hasNext() {
      return position < size;
    }

    public Object next() {
      Object temp = items[position];
      position++;
      return temp;
    }

    public void remove() {
      throw new IllegalStateException();
    }

  }

  /**
   * Returns a priority queue that is a clone of the current priority queue.
   */
  public PriorityQueue<T> clone() {
    PriorityQueue<T> clone = new PriorityQueue<>();
    clone.size = this.size;
    clone.items = (T[]) new Comparable[max_size];
    System.arraycopy(this.items, 0, clone.items, 0, size);
    return clone;
  }

}
