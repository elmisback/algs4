//import java.util.Arrays;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.ArrayList;
//import java.lang.Double;
//import edu.princeton.cs.algs4.StdOut;
//
//public class FastCollinearPoints {
//  private LinkedList<LineSegment> segs = new LinkedList<LineSegment>();
//
//  // Throws an IllegalArgumentException if the array contains a duplicate point.
//  private void checkNoDups(Point[] points) {
//    points = Arrays.copyOf(points, points.length);
//    Point p = new Point(0, 0);
//    Arrays.sort(points, p.slopeOrder());
//    for (int i = 0; i < points.length - 1; i++) {
//      if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException();
//    }
//  }
//
//  // finds all line segments containing 4 or more points
//  public FastCollinearPoints(Point[] points) {
//    if (points == null) throw new NullPointerException();
//
//    // Copy and sort the input array.
//    Point[] pCopy = points.clone();
//    Arrays.sort(pCopy);
//
//    // Check for duplicates.
//    checkNoDups(pCopy);
//
//    for (int i = 0; i < pCopy.length - 3; i++) {
//      Arrays.sort(pCopy);
//      Point p = pCopy[i];
//
//      // Sort by slope.
//      // Check if any 3 adj. points have equal slopes wrt p.
//      // If so, they are collinear with p.
//      Arrays.sort(pCopy, pCopy[i].slopeOrder());
//      for (int first = 1, last = 2; last < pCopy.length; last++) {
//        while (last < pCopy.length
//               && p.slopeTo(pCopy[first]) == p.slopeTo(pCopy[last])) last++;
//
//        if (last - first >= 3 && p.compareTo(pCopy[first]) < 0) {
//          segs.add(new LineSegment(p, pCopy[last - 1]));
//        }
//        first = last;
//      }
//    }
//  }
//
//  // the number of line segments
//  public int numberOfSegments() {
//    return segs.size();
//  }
//
//  // the line segments
//  public LineSegment[] segments() {
//    LineSegment[] ret = new LineSegment[segs.size()];
//    for (int i = 0; i < segs.size(); i++) {
//      ret[i] = segs.get(i);
//    }
//    return ret;
//  }
//
//  public static void main(String[] args) {
//    StdOut.println("FastCollinearPoints:");
//    FastCollinearPoints fcp;
//    Point[] points = {
//      new Point(0, 0),
//      new Point(1, 1),
//      new Point(-1, -1),
//    };
//    fcp = new FastCollinearPoints(points);
//    assert fcp.numberOfSegments() == 0;
//    Point[] R = {
//      new Point(0, 0),
//      new Point(1, 1),
//      new Point(-1, -1),
//      new Point(2, 2)
//    };
//    fcp = new FastCollinearPoints(R);
//    assert fcp.numberOfSegments() == 1 : fcp.segments()[1];
//    assert "(-1, -1) -> (2, 2)".equals(fcp.segments()[0].toString()) : String.format("%s", fcp.segments()[0].toString()) ;
//
//    Point[] Q = {
//      new Point(0, 0),
//      new Point(1, 1),
//      new Point(-1, -1),
//      new Point(2, 2),
//      new Point(-1, 1),
//      new Point(1, -1),
//      new Point(2, -2),
//      new Point(3, -3),
//      new Point(4, -4),
//      new Point(5, -5)
//    };
//    fcp = new FastCollinearPoints(Q);
//    StdOut.println("Should have (-1, -1) -> (2, 2) and (5, -5) -> (-1, 1).");
//    StdOut.println(fcp.segments()[0].toString());
//    StdOut.println(fcp.segments()[1].toString());
//    assert fcp.numberOfSegments() == 2 : fcp.numberOfSegments();
//
//    Point[] S = {
//      new Point(0, 0),
//      new Point(0, 1),
//      new Point(0, 2),
//      new Point(0, 3)
//    };
//    fcp = new FastCollinearPoints(S);
//    assert fcp.numberOfSegments() == 1;
//
//    StdOut.println("Passed.");
//  }
//}

import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ArrayList<LineSegment> jSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new NullPointerException();

        Point[] jCopy = points.clone();
        Arrays.sort(jCopy);

        if (hasDuplicate(jCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }
        // and now show must go on )))

        for (int i = 0; i < jCopy.length - 3; i++) {
            Arrays.sort(jCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(jCopy, jCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < jCopy.length; last++) {
                // find last collinear to p point
                while (last < jCopy.length
                        && Double.compare(jCopy[p].slopeTo(jCopy[first]), jCopy[p].slopeTo(jCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && jCopy[p].compareTo(jCopy[first]) < 0) {
                    jSegments.add(new LineSegment(jCopy[p], jCopy[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
        // finds all line segments containing 4 or more points
    }

    // the number of line segments
    public int numberOfSegments() {
        return jSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }

    // test the whole array fo duplicate points
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

  public static void main(String[] args) {
    StdOut.println("FastCollinearPoints:");
    FastCollinearPoints fcp;
    Point[] points = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
    };
    fcp = new FastCollinearPoints(points);
    assert fcp.numberOfSegments() == 0;
    Point[] R = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
      new Point(2, 2)
    };
    fcp = new FastCollinearPoints(R);
    assert fcp.numberOfSegments() == 1 : fcp.segments()[1];
    assert "(-1, -1) -> (2, 2)".equals(fcp.segments()[0].toString()) : String.format("%s", fcp.segments()[0].toString()) ;

    Point[] Q = {
      new Point(0, 0),
      new Point(1, 1),
      new Point(-1, -1),
      new Point(2, 2),
      new Point(-1, 1),
      new Point(1, -1),
      new Point(2, -2),
      new Point(3, -3),
      new Point(4, -4),
      new Point(5, -5)
    };
    fcp = new FastCollinearPoints(Q);
    StdOut.println("Should have (-1, -1) -> (2, 2) and (5, -5) -> (-1, 1).");
    StdOut.println(fcp.segments()[0].toString());
    StdOut.println(fcp.segments()[1].toString());
    assert fcp.numberOfSegments() == 2 : fcp.numberOfSegments();

    Point[] S = {
      new Point(0, 0),
      new Point(0, 1),
      new Point(0, 2),
      new Point(0, 3)
    };
    fcp = new FastCollinearPoints(S);
    assert fcp.numberOfSegments() == 1;

    StdOut.println("Passed.");
  }

}
