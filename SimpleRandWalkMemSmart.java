import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleRandWalkMemSmart extends Algorithm {
    LinkedList<Node> memory;
    int memorySize;

    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public SimpleRandWalkMemSmart(Long seed, Graph superGraph, Graph subGraph, int memorySize) {
        super(seed, superGraph, subGraph);
        // TODO Auto-generated constructor stub
        this.memory = new LinkedList<Node>();
        this.memorySize = memorySize;
    }

    public void traverseGraph() {

        Node curNode = subGraph.start;
        int traversals = 0;

        while (!curNode.equals(subGraph.target)) {

            Edge tempEdge = findNextNodeWithMemConstraintSmart(curNode, curNode.edgeList.size());

            if (tempEdge == null)
                tempEdge = findNextNodeWithoutMemConstraintSmart(curNode, curNode.edgeList.size());

            distance = distance.add(BigInteger.valueOf(tempEdge.weight));

            memory.addLast(curNode);

            curNode = tempEdge.toNode;

            traversals++;
            if (traversals >= max_traversal) {
                distance = BigInteger.valueOf(-2);
                return;
            }
        }
    }

    private Edge findNextNodeWithoutMemConstraintSmart(Node curNode, int size) {
        Edge edge = contains_target(curNode.edgeList);


        if (edge == null) {
            int i = this.rand.nextInt(curNode.edgeList.size());
            edge = curNode.edgeList.get(i);
        }

        return edge;
    }

    private Edge findNextNodeWithMemConstraintSmart(Node curNode, int size) {
        Edge chosenEdge = null;
        ArrayList<Edge> viableEdges = new ArrayList<Edge>();

        for (int i = 0; i < curNode.edgeList.size(); i++) {

            if ((curNode.edgeList.get(i)).toNode.equals(this.subGraph.target))
                return curNode.edgeList.get(i);
            if (!this.memory.contains(((Edge) curNode.edgeList.get(i)).toNode)) {
                viableEdges.add(curNode.edgeList.get(i));
            }
        }

        if (viableEdges.size() > 0) {
            int nextEdge = this.rand.nextInt(viableEdges.size());
            chosenEdge = viableEdges.get(nextEdge);
            return chosenEdge;
        }
        return null;
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
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/SimpleRandWalkMemSmart.class Java compiler
 * version: 9 (53.0) JD-Core Version: 1.1.3
 */
