import java.math.BigInteger;
import java.util.ArrayList;

/*
 * there should be a graph that is the alg
 */

public class SimpleRandWalkSmart extends Algorithm {
    
        public SimpleRandWalkSmart(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph); 
    }
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public void traverseGraph() {
        
        Node curNode = subGraph.start;
        int traversals = 0;

        while (!curNode.equals(subGraph.target)) {
            Edge nextEdge = contains_target(curNode.edgeList);
            
            if (nextEdge == null) {
                int nextNode = rand.nextInt(curNode.edgeList.size());
                nextEdge = curNode.edgeList.get(nextNode);
            }
            
            distance = distance.add(BigInteger.valueOf(nextEdge.weight));
            curNode = nextEdge.toNode;
            
            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }
    }
    
    public Edge contains_target(ArrayList<Edge> edgeList) {
        for (int i = 0; i < edgeList.size(); ++i) {
            
            if (edgeList.get(i).toNode.equals(superGraph.target)) return edgeList.get(i);
        }
        return null;
    }
}