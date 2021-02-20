import java.math.BigInteger;

public class SimpleRandomWalk extends Algorithm {
    public SimpleRandomWalk(Long seed, Graph superGraph, Graph subGraph) {
        super(seed, superGraph, subGraph); 
    }

    public void traverseGraph() {
        Node curNode = this.subGraph.start;
        int traversals = 0;

        while (!curNode.equals(this.subGraph.target)) {
            int nextNode = this.rand.nextInt(curNode.edgeList.size());
            Edge nextEdge = curNode.edgeList.get(nextNode);
            this.distance = this.distance.add(BigInteger.valueOf(nextEdge.weight));
            curNode = nextEdge.toNode;

            traversals++;
            if (traversals >= this.max_traversal) {
                this.distance = BigInteger.valueOf(-2L);
                return;
            }
        }
    }

    public void readConfig() {}
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/SimpleRandomWalk.class Java compiler version:
 * 9 (53.0) JD-Core Version: 1.1.3
 */
