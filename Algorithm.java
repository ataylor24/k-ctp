import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Algorithm implements Runnable {
    public Random rand;
    public BigInteger distance;
    Graph superGraph;
    Graph subGraph;
    public int max_traversal;

    public Algorithm(Long seed, Graph superGraph, Graph subGraph) {
        this.rand = new Random();
        //this.rand.setSeed(seed);
        this.superGraph = superGraph;
        this.subGraph = subGraph;
        this.distance = new BigInteger("0");
        this.max_traversal = Integer.MAX_VALUE;
    }

    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void traverseGraph() {
        
    }
    
    /*
     * Reads in the config file for each algorithm
     */
    public void readConfig() {

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        distance = BigInteger.valueOf(0);
        traverseGraph();
    }
   
}