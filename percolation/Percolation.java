import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
  private int N;
  private int topUFNode;
  private boolean[] sites;
  private WeightedQuickUnionUF UF;
  private boolean[] reachesBottom;

  private void validateSite(int i, int j) {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new IndexOutOfBoundsException();
    }
  }

  public Percolation(int n) {
    // create n-by-n grid, with all sites blocked
    N = n;
    if (N <= 0) {
      throw new IllegalArgumentException();
    }
    sites = new boolean[N*N + 1];
    for (int i = 0; i < N*N; i++) {
      sites[i] = false;
    }
    sites[N*N] = true;

    reachesBottom = new boolean[N*N + 1];
    for (int i = 0; i < N*(N-1); i++) {
      reachesBottom[i] = false;
    }

    for (int i = 0; i < N; i++) {
      reachesBottom[N*(N-1) + i] = true;
    }
    reachesBottom[N*N] = false;

    // One node for each site, plus one each for the top and bottom.
    int UFSize = (N * N) + 1;
    topUFNode = N * N;
    UF = new WeightedQuickUnionUF(UFSize);
  }

  private int index(int i, int j) {
    if (i < 1 || j < 1 || i > N || j > N) {
      return -1;
    }
    return (i - 1) * N + j - 1;
  }

  private void connectSitesIfValid(int p, int q) {
    // Unions UFNode p with the node associated with site (i, j).
    //
    // Only connects if q is valid.
    if (q != -1 && isOpen(q)) {
      p = UF.find(p);
      q = UF.find(q);
      if (reachesBottom[q] || reachesBottom[p]) {
        reachesBottom[q] = true;
        reachesBottom[p] = true;
      }
      UF.union(p, q);
    }
  }

  public void open(int i, int j) {
    // open site (row i, column j) if it is not open already
    validateSite(i, j);
    int p = index(i, j);

    // Note the site is open.
    sites[p] = true;

    // Union with neighbors if they are open.
    connectSitesIfValid(p, index(i-1, j));
    connectSitesIfValid(p, index(i+1, j));
    connectSitesIfValid(p, index(i, j-1));
    connectSitesIfValid(p, index(i, j+1));

    // If in the top row, union with top.
    if (i == 1) {
      connectSitesIfValid(p, topUFNode);
    }

    // (similarly for the bottom row).
    //if (i == N) {
    //    UF.union(p, bottomUFNode);
    //}
  }
  private boolean isOpen(int p) {
    return sites[p];
  }

  public boolean isOpen(int i, int j) {
    validateSite(i, j);
    return isOpen(index(i, j));
  }

  public boolean isFull(int i, int j) {
    // is site (row i, column j) full?
    validateSite(i, j);
    return UF.connected(index(i, j), topUFNode);
  }

  public boolean percolates() {
    // does the system percolate?
    return reachesBottom[UF.find(topUFNode)];
    //return UF.connected(topUFNode, bottomUFNode);
  }

  public static void main(String[] args) {
    StdOut.println("Test.");
    Percolation P = new Percolation(5);
    P.open(1,1);
    P.open(2,1);
    P.open(4,1);
    P.open(5,1);
    P.open(5,5);  // BUG: It's not perfect: there's overflow.
    P.percolates();
    StdOut.println(P.isFull(3, 1));
    StdOut.println(P.percolates());
    P.open(3,1);
    StdOut.println(P.isFull(3, 1));
    StdOut.println(P.percolates());
    StdOut.println(P.isFull(5, 5));
  }
}
