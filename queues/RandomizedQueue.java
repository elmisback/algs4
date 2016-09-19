import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

class RQIterator<Item> implements Iterator<Item> {

  public RQIterator() {
  }

  public Item next() {
    //if (length == 0) {
    //  throw new NoSuchElementException();
    //}
    return null;
  }

  public boolean hasNext() {
    return false;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}

public class RandomizedQueue<Item> implements Iterable<Item> {
  Item[] q;
  int front; // points to the first item in the queue.

  // construct an empty randomized queue
  @SuppressWarnings("unchecked")
  public RandomizedQueue() {
    q = (Item[]) new Object[1];
    q[0] = null;
    front = 0;
  }

  // is the queue empty?
  public boolean isEmpty() {
    return front == 0;
  }

  // return the number of items on the queue
  public int size() {
    return front;
  }

  // resize the underlying array, copying elements
  @SuppressWarnings("unchecked")
  private void resize(int newlen) {
    Item[] q2 = (Item[]) new Object[newlen];
    int size = (newlen < q.length) ? newlen : q.length;
    for (int i=0; i < size; i++) {
      q2[i] = q[i];
    }
    q = q2;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if ( front == q.length - 1) {
      resize(2 * q.length);
    }
    front += 1;
    q[front] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item item = q[front];
    front -= 1;
    if (front < q.length / 2) {
      resize(q.length / 2);
    }
    return item;

  }

  // return (but do not remove) a random item
  public Item sample() {
    return null;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return null;
  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueue<String> Q = new RandomizedQueue<String>();
    assert Q.isEmpty();
    assert Q.size() == 0;
    Q.enqueue("a");
    assert Q.size() == 1;
    assert Q.dequeue() == "a";
    assert Q.isEmpty();
    StdOut.println("passed");
  }
}
