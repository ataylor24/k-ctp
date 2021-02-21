import java.math.BigInteger;
import java.util.ArrayList;

/*
 * there should be a graph that is the alg
 */

public class SimpleRandomWalk extends Algorithm {
    
        public SimpleRandomWalk(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph); 
    }
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void traverseGraph() {
        
        Node curNode = subGraph.start;
        int traversals = 0;

        while (!curNode.equals(subGraph.target)) {
            int nextNode = rand.nextInt(curNode.edgeList.size());
            Edge nextEdge = curNode.edgeList.get(nextNode);
            distance = distance.add(BigInteger.valueOf(nextEdge.weight));
            curNode = nextEdge.toNode;
            
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