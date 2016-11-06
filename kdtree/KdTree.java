import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Iterator;

public class KdTree {
  private Node root = null;
  private int size = 0;

  private static class Node {
    private Point2D p;    // the point
    private RectHV rect;  // the axis-aligned rectangle corresponding to this node
    private Node lb;      // the left/bottom subtree
    private Node rt;      // the right/top subtree

    // rect: parent's rect
    // isTopOrRight: is this the top/right part of the parent's rect?
    public Node (Point2D p, RectHV rect) {
      this.p = p;
      this.rect = rect;
    }

    public boolean has(Point2D p) {
      if (this.p.equals(p)) return true;
      if (rt != null && rt.rect.contains(p)) return rt.has(p);
      return (lb != null) ? lb.has(p) : false;
    }

    private RectHV rightTop(boolean isVertical) {
      return isVertical ? new RectHV(p.x(), rect.ymin(),
                                     rect.xmax(), rect.ymax()) :
                          new RectHV(rect.xmin(), p.y(),
                                     rect.xmax(), rect.ymax());
    }

    private RectHV leftBottom(boolean isVertical) {
      return isVertical ? new RectHV(rect.xmin(), rect.ymin(),
                                     p.x(), rect.ymax()) :
                          new RectHV(rect.xmin(), rect.ymin(),
                                     rect.xmax(), p.y());
    }

    // Requires not(has(p)) && this.rect.contains(p);
    // vertical is true if this node is vertical.
    public void insert(Point2D p, boolean isVertical) {
      RectHV rect = (rt == null) ? rightTop(isVertical) : rt.rect;
      if (rect.contains(p)) {
        if (rt == null) rt = new Node(p, rect);
        else rt.insert(p, !isVertical);
      } else {
        if (lb == null) lb = new Node(p, leftBottom(isVertical));
        else lb.insert(p, !isVertical);
      }
    }

    public void draw(boolean isVertical) {
      StdDraw.setPenColor(StdDraw.BLACK);
      p.draw();
      if (isVertical) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
      }
      StdDraw.setPenColor(StdDraw.BLACK);
      if (lb != null) lb.draw(!isVertical);
      if (rt != null) rt.draw(!isVertical);
    }

    // Mutates A: adds all points that are in rect.
    public void range(RectHV rect, ArrayList<Point2D> A) {
      if (!this.rect.intersects(rect)) return;
      if (rect.contains(p)) A.add(p);
      if (lb != null) lb.range(rect, A);
      if (rt != null) rt.range(rect, A);
    }

    private Point2D nearest(Point2D p, Point2D guess) {
      if (this.p != guess &&
          p.distanceSquaredTo(guess) < this.rect.distanceSquaredTo(p)) {
        return guess;
      }
      if (this.p.distanceSquaredTo(p) < guess.distanceSquaredTo(p)) {
        guess = this.p;
      }
      Node first = (rt != null && rt.rect.contains(p)) ? rt : lb;
      Node second = (first == rt) ? lb : rt;
      if (first != null) guess = first.nearest(p, guess);
      if (second != null) guess = second.nearest(p, guess);
      return guess;
    }

    public Point2D nearest(Point2D p) {
      return nearest(p, this.p);
    }
  }

  // construct an empty set of points
  public KdTree() {
  }

  // is the set empty?
  public boolean isEmpty() {
    return size == 0;
  }
  // number of points in the set
  public int size() {
    return size;
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (isEmpty()) return false;
    return root.has(p);
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (contains(p)) return;
    if (root == null)
      root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
    else root.insert(p, true);
    size += 1;
  }

  // draw all points to standard draw
  public void draw() {
    root.draw(true);
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    ArrayList<Point2D> A = new ArrayList<Point2D>();
    if (!isEmpty()) root.range(rect, A);
    return A;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    return (isEmpty()) ? null : root.nearest(p);
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
  }
}
