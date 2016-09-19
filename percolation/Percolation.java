import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
  private int N;
  private int topUFNode;
  private int bottomUFNode;
  private boolean[] sites;
  private WeightedQuickUnionUF UF;

  private void validateSite(int i, int j) {
    if (i < 1 || j < 1 || i > N || j > N) {
      StdOut.println(i);
      StdOut.println(j);
      throw new IndexOutOfBoundsException();
    }
  }

  public Percolation(int n) {
    // create n-by-n grid, with all sites blocked
    N = n;
    if (N <= 0) {
      throw new IllegalArgumentException();
    }
    sites = new boolean[N*N];
    for (int i = 0; i < N*N; i++) {
      sites[i] = false;
    }

    // One node for each site, plus one each for the top and bottom.
    int UFSize = (N * N) + 2;
    topUFNode = 0;
    bottomUFNode = UFSize - 1;
    UF = new WeightedQuickUnionUF(UFSize);
  }

  private int getSiteIndex(int i, int j) {
    // Returns an index into the sites array.
    return (i - 1) * N + (j - 1);
  }

  private int getUFNode(int i, int j) {
    // Returns the UF node the site is associated with.
    //
    // (-1 if the site isn't valid.)
    if (i < 1 || j < 1 || i > N || j > N) {
      return -1;
    }
    return (i - 1) * N + (j - 1) + 1;
  }

  private void connectSiteIfValid(int p, int i, int j) {
    // Unions UFNode p with the node associated with site (i, j).
    //
    // Only connects if the site is valid.
    int q = getUFNode(i, j);
    if (q != -1 && isOpen(i, j)) {
      UF.union(p, q);
    }
  }

  public void open(int i, int j) {
    // open site (row i, column j) if it is not open already
    validateSite(i, j);
    int p = getSiteIndex(i, j);

    // Note the site is open.
    sites[p] = true;

    // Union with neighbors if they are open.
    p = getUFNode(i, j);

    connectSiteIfValid(p, i-1, j);
    connectSiteIfValid(p, i+1, j);
    connectSiteIfValid(p, i, j-1);
    connectSiteIfValid(p, i, j+1);

    // If in the top row, union with top.
    if (i == 1) {
        UF.union(p, topUFNode);
    }

    // (similarly for the bottom row).
    if (i == N) {
        UF.union(p, bottomUFNode);
    }
  }

  public boolean isOpen(int i, int j) {
    // is site (row i, column j) open?
    validateSite(i, j);
    int p = getSiteIndex(i, j);
    return sites[p];
  }

  public boolean isFull(int i, int j) {
    // is site (row i, column j) full?
    validateSite(i, j);
    int p = getUFNode(i, j);
    return UF.connected(topUFNode, p);
  }

  public boolean percolates() {
    // does the system percolate?
    return UF.connected(topUFNode, bottomUFNode);
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
