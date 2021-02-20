import java.math.BigInteger;
import java.util.ArrayList;



public class SimpleRandWalkSmart extends Algorithm {
    public SimpleRandWalkSmart(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph);
    }

    public void traverseGraph() {
        Node curNode = this.subGraph.start;
        byte traversals = 0;

        while (!curNode.equals(this.subGraph.target)) {
            Edge edge = contains_target(curNode.edgeList);

            if (edge == null) {
                int i = this.rand.nextInt(curNode.edgeList.size());
                edge = curNode.edgeList.get(i);
            }

            this.distance = this.distance.add(BigInteger.valueOf(edge.weight));
            curNode = edge.toNode;

            traversals++;
            if (traversals >= this.max_traversal) {
                this.distance = BigInteger.valueOf(-2L);
                return;
            }
        }
    }

    public Edge contains_target(ArrayList<Edge> paramArrayList) {
        for (byte b = 0; b < paramArrayList.size(); b++) {

            if (((Edge) paramArrayList.get(b)).toNode.equals(this.superGraph.target))
                return paramArrayList.get(b);
        }
        return null;
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/SimpleRandWalkSmart.class Java compiler
 * version: 9 (53.0) JD-Core Version: 1.1.3
 */
