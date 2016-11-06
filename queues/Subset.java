import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

// prints a subset of input strings
public class Subset {

  // takes a command-line integer k;
  // reads in a sequence of N strings from standard input using StdIn.readString();
  // prints out exactly k of them, uniformly at random
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    if (k == 0) {
      return;
    }

    RandomizedQueue<String> RQ = new RandomizedQueue<String>();
    String s;

    // Naive solution.
    // Enqueue all strings from StdIn, then dequeue k times.
    //while (true) {
    //  try {
    //    s = StdIn.readString();
    //  } catch (NoSuchElementException e){
    //    break;
    //  }
    //  RQ.enqueue(s);
    //}

    // Space in O(k) solution.
    // First, enqueue k strings from StdIn.
    for (int i = 0; i < k; i++) {
      RQ.enqueue(StdIn.readString());
    }

    // Backwards Knuth Shuffle.
    // The first item could end up in one of the k spots + its own.
    int spots = k + 1;
    while (true) {
      try {
        s = StdIn.readString();
      } catch (NoSuchElementException e){
        break;
      }
      if (StdRandom.uniform(spots) < k) {
        RQ.dequeue();
        RQ.enqueue(s);
      }
      spots++;
    }

    Iterator<String> it = RQ.iterator();
    for (int i = 0; i < k; i++) {
      StdOut.println(it.next());
    }
  }
}
