import java.math.BigInteger;
import java.util.ArrayList;

public class BiasedRandWalkSmart extends Algorithm {
    public BiasedRandWalkSmart(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph);
        // TODO Auto-generated constructor stub
    }
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void traverseGraph() {
        BiasSmart bias = new BiasSmart(rand);
        Node curNode = subGraph.start;
        int traversals = 0;
        
        while (!curNode.equals(subGraph.target)) {
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
}
