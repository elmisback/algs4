import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Double;
import edu.princeton.cs.algs4.StdOut;

// Iterates through all 4-tuples and checks if the 4 points lie on a line.
public class BruteCollinearPoints {
  private final LinkedList<LineSegment> segs;

  // Throws an IllegalArgumentException if the array contains a duplicate point.
  private void checkNoDups(Point[] points) {
    points = Arrays.copyOf(points, points.length);
    Point p = new Point(0, 0);
    Arrays.sort(points, p.slopeOrder());
    for (int i = 0; i < points.length - 1; i++) {
      if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
    }
  }

  // finds all line segments containing 4 or more points
  public BruteCollinearPoints(Point[] points) {
    if (points == null) throw new NullPointerException();

    // Check for duplicates.
    checkNoDups(points);

    segs = new LinkedList<LineSegment>();

    for (Point p : points) {
      for (Point q : points) {
        if (!(p.compareTo(q) < 0)) continue;
        double slope = p.slopeTo(q);
        for (Point r : points) {
          if (!(q.compareTo(r) < 0)) continue;
          if (p.slopeTo(r) != slope) continue;
          for (Point s : points) {
            if (!(r.compareTo(s) < 0)) continue;
            if (p.slopeTo(s) != slope) continue;
            segs.add(new LineSegment(p, s));
          }
        }
      }
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return segs.size();
  }

  // the line segments
  public LineSegment[] segments() {
    LineSegment[] ret = new LineSegment[segs.size()];
    for (int i = 0; i < segs.size(); i++) {
      ret[i] = segs.get(i);
    }
    return ret;
  }

  public static void main(String[] args) {
    StdOut.println("BruteCollinearPoints:");
    BruteCollinearPoints bcp;
    Point[] points = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
    };
    bcp = new BruteCollinearPoints(points);
    assert bcp.numberOfSegments() == 0 : bcp.segments()[0];
    Point[] R = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
      new Point(2, 2)
    };
    bcp = new BruteCollinearPoints(R);
    assert bcp.numberOfSegments() == 1 : bcp.numberOfSegments();
    assert "(-1, -1) -> (2, 2)".equals(bcp.segments()[0].toString()) : String.format("%s", bcp.segments()[0].toString()) ;

    Point[] Q = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
      new Point(2, 2),
      new Point(1, -1),
      new Point(2, -2),
      new Point(3, -3),
    };
    bcp = new BruteCollinearPoints(Q);
    StdOut.println("Should have (-1, -1) -> (2, 2) and (3, -3) -> (0, 0).");
    StdOut.println(bcp.segments()[0].toString());
    StdOut.println(bcp.segments()[1].toString());
    assert bcp.numberOfSegments() == 2 : bcp.numberOfSegments();
    StdOut.println("Passed.");
  }
}
