import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut; 
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {

    private int rowLen;
    private int total;
    private int runCount;
    private double[] results;
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        rowLen = N;
        int i = T;
        total = 0;
        runCount = 0;
        results = new double[T];
        while (0 < i--) {
            int c = monteCarloSimulation(N);
            results[i] = (double) c/N/N;
            total += c;
            runCount++;
        }
    }
// run the Monte Carlo simulation on an N-by-N grid
    private int monteCarloSimulation(int N) {
        int c = 0;
        Percolation p = new Percolation(N);
        while (!p.percolates()) {
            int i = 1+StdRandom.uniform(N);
            int j = 1+StdRandom.uniform(N);
            if (!p.isOpen(i, j)) {
                c++;
                p.open(i, j);
            }
        }
        return c;
    }
    public double mean() {
        return (double) total/runCount/rowLen/rowLen;
    }
    public double stddev() {
        if (1 == runCount) {
            return Double.NaN;
        }
        return StdStats.stddev(results);
    }  
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats ps = new PercolationStats(N, T);
        double m = ps.mean();
        double s = ps.stddev();
        double l = (m - (1.96*s)/Math.sqrt(T));
        double h = (m + (1.96*s)/Math.sqrt(T));

        StdOut.println("mean                    = "+ m);
        StdOut.println("stddev                  = "+ s);
        StdOut.println("95% confidence interval = "+ l +", "+ h);
    }
}