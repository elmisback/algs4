import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

class RQIterator<Item> implements Iterator<Item> {
  private Item[] q;
  private int front;

  @SuppressWarnings("unchecked")
  public RQIterator(Item[] givenQ, int givenFront) {
    q = (Item []) new Object[givenQ.length];
    front = givenFront;
    // Copy elements over.
    for (int i = 0; i <= front; i++) {
      q[i] = givenQ[i];
    }

    // (We skip the null node.)
    for (int i = 1; i < front; i++) {
      int j = StdRandom.uniform(front - i + 1);
      Item swp = q[i];
      q[i] = q[i + j];
      q[i + j] = swp;
    }
  }

  public Item next() {
    if (front == 0) {
      throw new NoSuchElementException();
    }
    Item swp = q[front];
    front -= 1;
    return swp;
  }

  public boolean hasNext() {
    return front > 0;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] q;
  private int front; // points to the first item in the queue.

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

  // Randomly swap front with some element of q.
  private void randswap() {
    Item frontItem = q[front];
    int s = StdRandom.uniform(front);
    s = s + 1;  // (Remember our null offset.)
    q[front] = q[s];
    q[s] = frontItem;
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

    // Randomly swap front with some element of q.
    randswap();
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item item = q[front];
    q[front] = null;
    front -= 1;
    if (front < (q.length - 1) / 2) {
      resize(q.length / 2);
    }
    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return q[StdRandom.uniform(front) + 1];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RQIterator<Item>(q, front);
  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueue<String> Q = new RandomizedQueue<String>();
    assert Q.isEmpty();
    assert Q.size() == 0;
    Q.enqueue("a");

    assert Q.size() == 1;
    Iterator<String> it = Q.iterator();
    assert it.next() == "a";
    assert Q.dequeue() == "a";
    assert Q.isEmpty();
    Q.enqueue("a");
    Q.enqueue("b");
    StdOut.println("RQ:");
    it = Q.iterator();
    StdOut.printf("It. order: %s, %s\n", it.next(), it.next());
    StdOut.printf("Sample: %s\n", Q.sample());
    StdOut.printf("Order: %s, %s\n", Q.dequeue(), Q.dequeue());
    assert Q.isEmpty();
    Q.enqueue("a");
    Q.enqueue("b");
    Q.enqueue("c");
    Q.enqueue("d");
    Q.enqueue("e");
    Q.enqueue("f");
    Q.enqueue("g");
    Q.enqueue("h");
    StdOut.printf("Order: %s, %s, %s, %s, %s, %s, %s, %s \n",
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue(),
        Q.dequeue()
        );
    StdOut.println("Passed!");
  }
}
