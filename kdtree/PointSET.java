import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
  private SET<Point2D> set = new SET<Point2D>();

  // construct an empty set of points
  public PointSET() {
  }
  // is the set empty?
  public boolean isEmpty() {
    return set.isEmpty();
  }
  // number of points in the set
  public int size() {
    return set.size();
  }
  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    set.add(p);
  }
  // does the set contain point p?
  public boolean contains(Point2D p) {
    return set.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D p : set) p.draw();
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    ArrayList<Point2D> range = new ArrayList<Point2D>();
    for (Point2D p : set) {
      if (rect.contains(p)) range.add(p);
    }
    return range;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    Point2D nearest = null;
    for (Point2D q : set) {
      if (nearest == null) nearest = q;
      if (p.distanceTo(q) < p.distanceTo(nearest)) nearest = q;
    }
    return nearest;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
  }
}
