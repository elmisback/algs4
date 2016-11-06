import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.ArrayDeque;

class GameTreeNode implements Comparable<GameTreeNode> {
  public final Board b;
  private final GameTreeNode parent;
  private final int moves;

  public GameTreeNode(Board b) {
    this.b = b;
    this.parent = null;
    this.moves = 0;
  }

  public GameTreeNode(Board b, GameTreeNode parent, int moves) {
    this.b = b;
    this.parent = parent;
    this.moves = moves;
  }

  public ArrayList<GameTreeNode> getChildren() {
    ArrayList<GameTreeNode> children = new ArrayList<GameTreeNode>();
    for (Board neighbor : b.neighbors()) {
      if (parent != null && neighbor.equals(parent.b)) continue;
      children.add(new GameTreeNode(neighbor, this, moves + 1));
    }
    return children;
  }

  public int compareTo(GameTreeNode that) {
    int cmp = Integer.compare(this.b.manhattan() + this.moves,
                              that.b.manhattan() + that.moves);
    return (cmp == 0) ? Integer.compare(this.b.hamming(), that.b.hamming()) : cmp;
  }

  public ArrayList<Board> getSolution() {
    ArrayDeque<Board> s = new ArrayDeque<Board>();
    s.push(this.b);
    for (GameTreeNode c = this.parent; c != null; c = c.parent) s.push(c.b);
    return new ArrayList<Board>(s);
  }
}

public class Solver {
  private boolean isSolvable = false;
  private int moves = -1;
  private ArrayList<Board> solution = null;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    MinPQ<GameTreeNode> q = new MinPQ<GameTreeNode>();

    if (initial == null) throw new NullPointerException();

    q.insert(new GameTreeNode(initial));
    q.insert(new GameTreeNode(initial.twin()));

    GameTreeNode p;
    while (true) {
      p = q.delMin();
      if (p.b.isGoal()) {
        solution = p.getSolution();
        if (solution.get(0).equals(initial)) {
          isSolvable = true;
          moves = solution.size() - 1;
        } else {
          solution = null;
        }
        break;
      }
      for (GameTreeNode child : p.getChildren()) q.insert(child);
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return isSolvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solution;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }

}
