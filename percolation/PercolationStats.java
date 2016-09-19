import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] thresholds;
   private int N;
   private int T;

   public PercolationStats(int n, int trials) {
     // perform trials independent experiments on an n-by-n grid
     N = n;
     T = trials;
     if (n <= 0 || trials <= 0) {
       throw new IllegalArgumentException();
     }

     int opened;
     thresholds = new double[trials];

     for (int t = 0; t < trials; t++) {
       StdOut.println(t);
       opened = 0;
       Percolation P = new Percolation(n);
       while (! P.percolates()) {
         int i = StdRandom.uniform(n) + 1;
         int j = StdRandom.uniform(n) + 1;
         if (! P.isOpen(i, j)) {
           P.open(i, j);
           opened++;
         }
       }
       thresholds[t] = ((double) opened) / (n*n);
     }
   }

   public double mean() {
     // sample mean of percolation threshold
     return StdStats.mean(thresholds);
   }

   public double stddev() {
     // sample standard deviation of percolation threshold
     return StdStats.stddev(thresholds);
   }

   public double confidenceLo() {
     // low  endpoint of 95% confidence interval
     return mean() - 1.96 * stddev()/Math.sqrt(T);
   }

   public double confidenceHi() {
     // high endpoint of 95% confidence interval
     return mean() + 1.96 * stddev()/Math.sqrt(T);
   }

   public static void main(String[] args) {
     // test client (described below)
     PercolationStats S = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
     double a = S.confidenceLo();
     double b = S.confidenceHi();
     StdOut.printf("mean                    = %f\n", S.mean());
     StdOut.printf("stddev                  = %f\n", S.stddev());
     StdOut.printf("95%% confidence interval = %f, %f\n", a, b);
   }
}
