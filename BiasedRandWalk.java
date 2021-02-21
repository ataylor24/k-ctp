import java.math.BigInteger;
import java.util.ArrayList;

public class BiasedRandWalk extends Algorithm {
    public BiasedRandWalk(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph);
        // TODO Auto-generated constructor stub
    }
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void traverseGraph() {
        Bias bias = new Bias(rand);
        Node curNode = subGraph.start;
        int traversals = 0;
        
        while (!curNode.equals(subGraph.target)) {
          //create a Double ArrayList of 1's
            Edge tempEdge = bias.findNextEdgeForBiasRandWalk(curNode.edgeList, null, superGraph);
            
            distance = distance.add(BigInteger.valueOf(tempEdge.weight));
            
            curNode = tempEdge.toNode;
            
            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }
        
    }

    /*
     * Reads in the config file for each algorithm
     */
    public void readConfig() {

    }
}