import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.util.Collection;

@SuppressWarnings("overrides")
public class Board {
  private final int n;
  private int manhattan = -1;
  private int zloc = -1;  // Where is the empty tile?
  private char[][] tiles;

  // construct a board from an n-by-n array of tiles
  // (where tiles[i][j] = block in row i, column j)
  public Board(int[][] tiles) {
    this.n = tiles.length;
    if (n < 2) throw new IllegalArgumentException();
    this.tiles = new char[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        this.tiles[i][j] = (char) tiles[i][j];
      }
    }
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int outOfPlace = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) continue;
        if (tiles[i][j] != i * n + j + 1 ) outOfPlace++;
      }
    }
    return outOfPlace;
  }

  private int manhattanDistance(int i, int j) {
    int row = (tiles[i][j] - 1) / n;
    int col = (tiles[i][j] - 1) % n;
    return Math.abs(row - i) + Math.abs(col - j);
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    if (manhattan != -1) return manhattan;

    int row, col;
    int sum = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) continue;
        sum += manhattanDistance(i, j);
      }
    }
    manhattan = sum;
    return sum;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return manhattan() == 0;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    setZloc();
    int first = (zloc != 0) ? 0 : 1;
    int last = (zloc != n*n - 1) ? n*n - 1 : n*n - 2;
    return swap(first, last);
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == null) return false;
    return this.toString().equals(y.toString());
  }

  // Returns a board with the given locations swapped.
  private Board swap(int loc, int loc2) {
    int[][] twin = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        twin[i][j] = (int) tiles[i][j];
      }
    }
    int znew = zloc;
    if (zloc == loc) {
      znew = loc2;
    }
    if (zloc == loc2) {
      znew = loc;
    }

    twin[loc/n][loc%n] = tiles[loc2/n][loc2%n];
    twin[loc2/n][loc2%n] = tiles[loc/n][loc%n];

    Board b = new Board(twin);
    b.zloc = znew;
    return b;
  }

  // Set the location of the zero tile.
  private void setZloc() {
    if (zloc != -1) return;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) {
          zloc = i * n + j;
          return;
        }
      }
    }
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    setZloc();
    int zr = zloc / n;
    int zc = zloc % n;
    Stack<Board> s = new Stack<Board>();
    Board b;
    int dist;
    if (zr + 1 < n) {
      b = swap(zloc, (zr + 1) * n + zc);
      b.manhattan = manhattan() + (b.manhattanDistance(zr, zc)
                                   - manhattanDistance(zr + 1, zc));
      s.push(b);
    }
    if (zr - 1 >= 0) {
      b = swap(zloc, (zr - 1) * n + zc);
      b.manhattan = manhattan() + (b.manhattanDistance(zr, zc)
                                   - manhattanDistance(zr - 1, zc));
      s.push(b);
    }
    if (zc + 1 < n) {
      b = swap(zloc, zr * n + zc + 1);
      b.manhattan = manhattan() + (b.manhattanDistance(zr, zc)
                                   - manhattanDistance(zr, zc + 1));
      s.push(b);
    }
    if (zc - 1 >= 0) {
      b = swap(zloc, zr * n + zc - 1);
      b.manhattan = manhattan() + (b.manhattanDistance(zr, zc)
                                   - manhattanDistance(zr, zc - 1));
      s.push(b);
    }
    return s;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", (int) tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // unit tests (not graded)
  public static void main(String[] args) {
    StdOut.println("Board.java:");
    int[][] tmp = {{8, 1, 3},
                   {4, 0, 2},
                   {7, 6, 5}};
    Board b = new Board(tmp);
    assert (b.hamming() == 5) : b.hamming();
    assert (b.manhattan() == 10) : b.manhattan();
    assert (b.equals(b));
    int[][] tmp2 = {{5, 1, 3},
                    {4, 0, 2},
                    {7, 6, 8}};
    Board bPrime = new Board(tmp2);
    assert (! bPrime.equals(b));
    assert (b.twin().equals(bPrime)) : b.twin().toString();
    assert (((Collection<?>) b.neighbors()).size() == 4);
    StdOut.println("Passed!");
  }

}
