import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

  private class Node<Item> {
    public Node<Item> next;
    public Node<Item> prev;
    public Item item;

    public Node(Item i) {
      next = null;
      prev = null;
      item = i;
    }
  }

  private class LinkedList<Item> implements Iterator<Item> {
    private int length;
    private Node<Item> front;

    public LinkedList(Node<Item> p, int len) {
      front = p;
      length = len;
    }

    public Item next() {
      if (length == 0) {
        throw new NoSuchElementException();
      }
      Item i = front.item;
      front = front.next;
      length -= 1;
      return i;
    }

    public boolean hasNext() {
      return (length > 0);
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private Node<Item> front;
  private Node<Item> back;
  private int length;

  // construct an empty deque
  public Deque() {
    front = null;
    back = null;
    length = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return (length == 0);
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    Node<Item> p = new Node<Item>(item);
    if (length == 0) {
      front = p;
      back = p;
    } else {
      front.prev = p;
      p.next = front;
      front = p;
    }
    length += 1;
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    Node<Item> p = new Node<Item>(item);
    if (length == 0) {
      front = p;
      back = p;
    } else {
      back.next = p;
      p.prev = back;
      back = p;
    }
    length += 1;
  }


  // return the number of items on the deque
  public int size() {
    return length;
  }


  // remove and return the item from the front
  public Item removeFirst() {
    if (length == 0) {
      throw new NoSuchElementException();
    }
    Item i = front.item;
    if (length == 1) {
      back = null;
      front = null;
    } else {
      front = front.next;
      front.prev = null;
    }
    length -= 1;
    return i;
  }

  // remove and return the item from the end
  public Item removeLast() {
    if (length == 0) {
      throw new NoSuchElementException();
    }
    Item i = back.item;
    if (length == 1) {
      back = null;
      front = null;
    } else {
      back = back.prev;
      back.next = null;
    }
    length -= 1;
    return i;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new LinkedList<Item>(front, length);
  }

  // unit testing
  public static void main(String[] args) {
    Deque<String> d = new Deque<String>();
    assert d.isEmpty() : "Not empty on initialization.";
    d.addFirst("a");
    assert d.size() == 1 : "Size not incremented properly";
    assert d.removeFirst() == "a" : "First element not 'a'.";
    assert d.isEmpty();
    d.addFirst("a");
    assert d.removeLast() == "a";
    d.addFirst("a");
    d.addFirst("b");
    assert d.removeFirst() == "b";
    d.addLast("c");
    assert d.removeFirst() == "a";
    assert d.removeLast() == "c";
    d.addFirst("c");
    d.addFirst("b");
    d.addFirst("a");
    Iterator<String> it = d.iterator();
    assert it.next() == "a";
    assert it.next() == "b";
    assert it.next() == "c";
    assert it.hasNext() == false;
    StdOut.println("Deque: passed.");
  }

}
