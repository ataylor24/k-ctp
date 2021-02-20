import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleRandWalkMemFlush extends Algorithm {
    LinkedList<Node> memory;
    int memorySize;
    /*
     * Traverses the graph according the specifications of the algorithm
     */
    public SimpleRandWalkMemFlush(Long seed, Graph superGraph, Graph subGraph, int memorySize) {
        super(seed, superGraph, subGraph); 
        // TODO Auto-generated constructor stub
        this.memory = new LinkedList<Node>();
        this.memorySize = memorySize;
    }
    public void traverseGraph() {
        Node curNode = this.subGraph.start;
        int traversals = 0;
        this.memory.add(curNode);

        while (!curNode.equals(this.subGraph.target)) {

            Edge tempEdge = findNextNodeWithMemConstraint(curNode, curNode.edgeList.size());

            if (tempEdge == null) {
                this.memory.clear();
                tempEdge = findNextNodeWithoutMemConstraint(curNode, curNode.edgeList.size());
            }
            this.distance = this.distance.add(BigInteger.valueOf(tempEdge.weight));

            this.memory.addLast(curNode);
            curNode = tempEdge.toNode;

            traversals++;
            if (traversals >= this.max_traversal) {
                this.distance = BigInteger.valueOf(-2);
                return;
            }
        }
    }

    private Edge findNextNodeWithoutMemConstraint(Node curNode, int size) {
        int nextEdge = rand.nextInt(size);
        return curNode.edgeList.get(nextEdge);
    }

    private Edge findNextNodeWithMemConstraint(Node curNode, int size) {
        Edge chosenEdge = null;
        ArrayList<Edge> viableEdges = new ArrayList<Edge>();
        for (int i = 0; i < curNode.edgeList.size(); ++i) {
            //build an arrayList of viable edges by for loop, and then randomly choose from that size
            if (!memory.contains(curNode.edgeList.get(i).toNode)) {
                viableEdges.add(curNode.edgeList.get(i));
            }
        }
       if (viableEdges.size() > 0) {
           int nextEdge = rand.nextInt(viableEdges.size());
           chosenEdge = viableEdges.get(nextEdge);
           return chosenEdge;
       }
       return null;
    }
}


/*
 * Location: /Users/alextaylor/Desktop/sim_class_v15/!/SimpleRandWalkMemFlush.class Java compiler
 * version: 9 (53.0) JD-Core Version: 1.1.3
 */
