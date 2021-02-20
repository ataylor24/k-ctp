import java.math.BigInteger;

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
            Edge edge = bias.findNextEdgeForBiasRandWalk(curNode.edgeList, null, superGraph);

            distance = distance.add(BigInteger.valueOf(edge.weight));

            curNode = edge.toNode;

            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }

    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/BiasedRandWalkSmart.class Java compiler
 * version: 9 (53.0) JD-Core Version: 1.1.3
 */
